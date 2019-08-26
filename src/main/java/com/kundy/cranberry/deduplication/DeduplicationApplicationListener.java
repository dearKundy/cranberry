package com.kundy.cranberry.deduplication;

import com.google.common.hash.BloomFilter;
import com.kundy.cranberry.config.ApplicationContextProvider;
import com.kundy.cranberry.constant.DeduplicationBloomFilter;
import com.kundy.cranberry.service.DeduplicationBloomFilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 监听 SpringBoot 程序启动完成事件
 *
 * @author kundy
 * @date 2019/8/25 5:16 PM
 */
@Slf4j
@Component
public class DeduplicationApplicationListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private ApplicationContextProvider contextProvider;

    @Autowired
    private DeduplicationBloomFilterService deduplicationBloomFilterService;

    private static final Integer THRESHOLD = 500000;

    private ExecutorService service = Executors.newFixedThreadPool(10);

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("Deduplication bloomFilter start initialization.");

        long start = System.currentTimeMillis();
        List<String> bloomList = DeduplicationProperties.getBloomList();
        if (bloomList == null) {
            return;
        }

        CountDownLatch latch = new CountDownLatch(bloomList.size());
        for (String s : bloomList) {
            service.execute(() -> {
                this.initBloomFilter(s, latch);
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
        log.info("Deduplication bloomFilter initialization finished. Cost time: {} ms", (System.currentTimeMillis() - start));
    }

    private void initBloomFilter(String bloomFilterName, CountDownLatch latch) {
        BloomFilter<String> bloomFilter = contextProvider.getApplicationContext().getBean(bloomFilterName, BloomFilter.class);
        String tableName = DeduplicationTableNameUtils.getTableName(bloomFilterName);
        if (this.createTable(tableName)) {
            latch.countDown();
            return;
        }
        this.fillBloomFilter(tableName, bloomFilter, bloomFilterName);
        latch.countDown();
    }

    /**
     * 假如记录数大于指定阈值，切割查询任务，最后将子查询结果合并
     */
    private List<String> multiTaskListIdentifier(String tableName) {
        Integer total = this.deduplicationBloomFilterService.selectCount(tableName);
        if (total < THRESHOLD) {
            log.info("less than the threshold,No need to cut subtasks.");
            return this.deduplicationBloomFilterService.listIdentifier(tableName, null, null);
        }
        return this.mergeTask(this.splitTask(total, tableName));
    }

    /**
     * 如果去重记录表不存在，先建表
     *
     * @return 是否成功建表
     */
    private Boolean createTable(String tableName) {
        if (!deduplicationBloomFilterService.isTableExist(tableName)) {
            log.info("Table {} dose not exist，create now.", tableName);
            this.deduplicationBloomFilterService.createTable(tableName);
            return true;
        }
        return false;
    }

    private void fillBloomFilter(String tableName, BloomFilter<String> bloomFilter, String beanId) {
        long start = System.currentTimeMillis();
        List<String> keys = this.multiTaskListIdentifier(tableName);
        log.info("{}BloomFilter initialization , Query DB cost time：{} ms.", beanId, (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        for (String key : keys) {
            bloomFilter.put(key);
        }
        //BUGFIX
        DeduplicationBloomFilter.INIT_STATUS.put(beanId, true);
        log.info("{}BloomFilter initialization , Add element cost time：{} ms.", beanId, (System.currentTimeMillis() - start));
    }

    private List<FutureTask<List<String>>> splitTask(Integer total, String tableName) {
        List<FutureTask<List<String>>> futureTasks = new ArrayList<>();
        int threadNum = (int) Math.ceil((double) total / THRESHOLD);
        for (int i = 0; i < threadNum; i++) {
            FutureTask<List<String>> futureTask = new FutureTask<>(new ListTread(tableName, i));
            this.service.submit(futureTask);
            futureTasks.add(futureTask);
        }
        return futureTasks;
    }

    private List<String> mergeTask(List<FutureTask<List<String>>> futureTasks) {
        List<String> result = new ArrayList<>();
        for (FutureTask<List<String>> futureTask : futureTasks) {
            try {
                result.addAll(futureTask.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private class ListTread implements Callable<List<String>> {

        private String tableName;
        private int round;

        private ListTread(String tableName, int round) {
            this.tableName = tableName;
            this.round = round;
        }

        @Override
        public List<String> call() {
            return deduplicationBloomFilterService.listIdentifier(tableName, round * THRESHOLD, THRESHOLD);
        }
    }

}
