package com.kundy.justbattle.mapper;

import com.kundy.justbattle.model.po.JbUserPo;

import java.util.List;

/**
 * @author kundy
 * @date 2019/8/16 5:14 PM
 */
public interface JbUserMapper {

    List<Integer> listBlackUserId();

    boolean save(JbUserPo userPo);

}
