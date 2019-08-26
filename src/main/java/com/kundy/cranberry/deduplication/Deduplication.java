package com.kundy.cranberry.deduplication;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.common.hash.BloomFilter;
import com.kundy.cranberry.config.ApplicationContextProvider;
import com.kundy.cranberry.constant.DeduplicationBloomFilter;
import com.kundy.cranberry.service.DeduplicationBloomFilterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.digester.DocumentProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 去重器【批量去重】
 *
 * @author kundy
 * @date 2019/8/23 6:08 PM
 */
@Slf4j
@Service
public class Deduplication {

    @Autowired
    private ApplicationContextProvider contextProvider;

    @Autowired
    private DeduplicationBloomFilterService service;

    /**
     * @param comparedList    唯一标示列表
     * @param bloomFilterName 对应的布隆过滤器的 bean 名称
     * @return 第一次出现的记录列表
     */
    public List<String> go(List<String> comparedList, String bloomFilterName) {
        return this.go(comparedList, null, bloomFilterName, null, 1);
    }

    /**
     * @param csvReader       待比较的 csv 文件
     * @param bloomFilterBean 对应的布隆过滤器的 bean 名称
     * @param identifier      csv 文件中唯一标识字段名
     * @return 第一次出现的记录列表
     */
    public List<String> go(CsvReader csvReader, String bloomFilterBean, String identifier) {
        return this.go(null, csvReader, bloomFilterBean, identifier, 2);
    }

    private List<String> go(List<String> comparedList, CsvReader csvReader, String bloomFilterName, String identifier, Integer type) {
        this.checkBloomFilter(bloomFilterName);
        List<String> result;
        if (type == 1) {
            result = this.findNotExistRecordFromList(bloomFilterName, comparedList);
        } else {
            result = this.findNotExistRecordFromCsv(bloomFilterName, csvReader, identifier);
        }

        if (result.size() <= 0) {
            return result;
        }

        this.batchSave(bloomFilterName, result);
        return result;
    }

    private void checkBloomFilter(String bloomFilterName) {
        if (!DeduplicationBloomFilter.INIT_STATUS.get(bloomFilterName)) {
            throw new RuntimeException("{} is not ready, please try again later.");
        }
    }

    /**
     * 从 List 中找出不存在元素
     */
    private List<String> findNotExistRecordFromList(String bloomFilterName, List<String> comparedList) {
        BloomFilter<String> bloomFilter = this.contextProvider.getApplicationContext().getBean(bloomFilterName, BloomFilter.class);
        // 保存不存在的元素
        List<String> result = new ArrayList<>();
        for (String identifier : comparedList) {
            if (!bloomFilter.mightContain(identifier)) {
                result.add(identifier);
            }
        }
        return result;
    }

    private List<String> findNotExistRecordFromCsv(String bloomFilterName, CsvReader csvReader, String identifier) {
        BloomFilter<String> bloomFilter = this.contextProvider.getApplicationContext().getBean(bloomFilterName, BloomFilter.class);
        List<String> result = new ArrayList<>();
        try {
            csvReader.readHeaders();
            while (csvReader.readRecord()) {
                String tmpIdentifier = csvReader.get(identifier);
                if (!bloomFilter.mightContain(tmpIdentifier)) {
                    result.add(tmpIdentifier);
                }
            }
        } catch (IOException e) {
            log.warn("read csv file error!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量保存不存在的元素到数据库
     */
    private void batchSave(String bloomFilterName, List<String> result) {
        boolean batchSave = this.service.batchSave(DeduplicationTableNameUtils.getTableName(bloomFilterName), result);
        if (!batchSave) {
            throw new RuntimeException("save deduplication record error");
        }
    }

}
