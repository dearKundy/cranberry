package com.kundy.justbattle;

import com.kundy.justbattle.model.po.JbUserPo;
import com.kundy.justbattle.transaction.AnnotationTx;
import com.kundy.justbattle.transaction.ProgrammingTx;
import com.kundy.justbattle.transaction.TemplateTx;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JustBattleApplicationTests {

    @Autowired
    private ProgrammingTx programmingTx;

    @Autowired
    private TemplateTx templateTx;

    @Autowired
    private AnnotationTx annotationTx;

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

}
