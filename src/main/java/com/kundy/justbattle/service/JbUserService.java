package com.kundy.justbattle.service;

import com.kundy.justbattle.model.po.JbUserPo;

import java.util.List;

/**
 * @author kundy
 * @date 2019/8/16 5:20 PM
 */
public interface JbUserService {

    List<Integer> listBlackUserId();

    boolean save(JbUserPo userPo);

}
