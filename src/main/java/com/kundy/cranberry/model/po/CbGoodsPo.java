package com.kundy.cranberry.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 货物表
 * <p>
 * MyBatis-Plus：使用 @TableName 指定查询表名，默认值为下划线类名
 *
 * @author kundy
 * @date 2019/8/18 10:52 PM
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("jb_goods")
public class CbGoodsPo extends Model<CbGoodsPo> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("name")
    private String name;
    private Integer stock;
    private Integer version;


}
