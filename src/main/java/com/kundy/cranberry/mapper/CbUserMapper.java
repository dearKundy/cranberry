package com.kundy.cranberry.mapper;

import com.kundy.cranberry.model.po.CbUserPo;

import java.util.List;

/**
 * @author kundy
 * @date 2019/8/16 5:14 PM
 */
public interface CbUserMapper {

    List<Integer> listBlackUserId();

    boolean save(CbUserPo userPo);

}
