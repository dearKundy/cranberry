package com.kundy.cranberry.service;

import com.kundy.cranberry.model.po.CbUserPo;

import java.util.List;

/**
 * @author kundy
 * @date 2019/8/16 5:20 PM
 */
public interface CbUserService {

    List<Integer> listBlackUserId();

    boolean save(CbUserPo userPo);

}
