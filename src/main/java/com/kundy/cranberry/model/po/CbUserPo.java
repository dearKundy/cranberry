package com.kundy.cranberry.model.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author kundy
 * @date 2019/8/16 5:12 PM
 */
@Data
@Accessors(chain = true)
public class CbUserPo implements Serializable {

    private int id;
    private String name;
    private String password;
    private int isBlack;

}
