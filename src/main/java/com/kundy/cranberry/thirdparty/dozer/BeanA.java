package com.kundy.cranberry.thirdparty.dozer;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author kundy
 * @date 2019/9/27 6:21 PM
 */
@Data
@Accessors(chain = true)
public class BeanA {

    private long id;
    private String name;
    private Date date;

    private String fileA;

}
