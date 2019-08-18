package com.kundy.justbattle;

import com.kundy.justbattle.dblock.OptimismLock;
import com.kundy.justbattle.dblock.PessimisticLock;
import com.kundy.justbattle.model.po.JbUserPo;
import com.kundy.justbattle.transaction.AnnotationTx;
import com.kundy.justbattle.transaction.ProgrammingTx;
import com.kundy.justbattle.transaction.TemplateTx;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JustBattleApplicationTests {

    @Autowired
    private ProgrammingTx programmingTx;

    @Autowired
    private TemplateTx templateTx;

    @Autowired
    private AnnotationTx annotationTx;

    @Autowired
    private PessimisticLock pessimisticLock;

    @Autowired
    private OptimismLock optimismLock;

    @Test
    public void testProgramingTx() {
        JbUserPo jbUserPo = new JbUserPo().setName("heyJude").setPassword("0000");
        boolean flag = this.programmingTx.go(jbUserPo);
        System.out.println(flag);
    }

    @Test
    public void testTemplateTx(){
        JbUserPo jbUserPo = new JbUserPo().setName("yahu").setPassword("0000");
        boolean flag = this.templateTx.go(jbUserPo);
        System.out.println(flag);
    }

    @Test
    public void testAnnotationTx(){
        JbUserPo jbUserPo = new JbUserPo().setName("yiming").setPassword("0000");
        boolean flag = this.annotationTx.go(jbUserPo);
        System.out.println(flag);
    }

    @Test
    public void testDbLock(){
        CountDownLatch latch = new CountDownLatch(5);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                this.optimismLock.sale(1);
                latch.countDown();
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
