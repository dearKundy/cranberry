package com.kundy.justbattle.model.po;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author kundy
 * @date 2019/8/16 5:12 PM
 */
@Data
@Accessors(chain = true)
public class JbUserPo {

    private int id;
    private String name;
    private String password;

}
