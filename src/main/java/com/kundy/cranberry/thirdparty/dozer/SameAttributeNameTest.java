package com.kundy.cranberry.thirdparty.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.Date;

/**
 * 假如两个 JavaBean 的属性名相同，可以直接映射
 * <p>
 * 最好不要每次映射对象时都创建一个 Mapper 实例来工作，这样会产生不必要的开销。
 * 最好使用 Spring 去管理，且是单例模型。
 *
 * @author kundy
 * @date 2019/9/27 6:25 PM
 */
public class SameAttributeNameTest {

    public static void main(String[] args) {
        Mapper mapper = new DozerBeanMapper();
        BeanA sameAttributeNameA = new BeanA().setName("name").setId(123).setDate(new Date());
        BeanB sameAttributeNameB = mapper.map(sameAttributeNameA, BeanB.class);
        System.out.println(sameAttributeNameB);
    }

}
