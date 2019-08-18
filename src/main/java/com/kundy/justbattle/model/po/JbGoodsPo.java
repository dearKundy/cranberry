package com.kundy.justbattle.model.po;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 货物表
 *
 * @author kundy
 * @date 2019/8/18 10:52 PM
 */
@Data
@Accessors(chain = true)
public class JbGoodsPo {

    private Integer id;
    private String name;
    private Integer stock;
    private Integer version;

}
