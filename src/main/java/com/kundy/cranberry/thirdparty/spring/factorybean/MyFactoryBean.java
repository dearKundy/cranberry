package com.kundy.cranberry.thirdparty.spring.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author kundy
 * @date 2019/11/1 9:59 PM
 */
@Component
public class MyFactoryBean implements FactoryBean<Person> {

    @Override
    public Person getObject() throws Exception {
        return new Person() {
            @Override
            public void sayHi() {
                System.out.println("我是临时构建的！！！");
            }
        };
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

}
