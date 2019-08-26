package com.kundy.cranberry.service.impl;

import com.kundy.cranberry.mapper.DeduplicationBloomFilterMapper;
import com.kundy.cranberry.service.DeduplicationBloomFilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kundy
 * @date 2019/8/23 2:50 PM
 */
@Slf4j
@Service
public class DeduplicationBloomFilterServiceImpl implements DeduplicationBloomFilterService {

    @Autowired
    private DeduplicationBloomFilterMapper mapper;

    @Override
    public boolean isTableExist(String tableName) {
        return this.mapper.isTableExist(tableName) != null;
    }

    @Override
    public boolean createTable(String tableName) {
        try {
            this.mapper.createTable(tableName);
        } catch (Exception e) {
            log.warn("create table {} error! cause: {}", tableName, e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<String> listIdentifier(String tableName, Integer offset, Integer limit) {
        return this.mapper.listIdentifier(tableName, offset, limit);
    }

    @Override
    public Integer selectCount(String tableName) {
        return this.mapper.selectCount(tableName);
    }

    @Override
    public boolean batchSave(String tableName, List<String> identifiers) {
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableName);
        map.put("identifiers", identifiers);
        return this.mapper.batchSave(map);
    }
}
