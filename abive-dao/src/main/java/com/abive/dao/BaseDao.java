package com.abive.dao;

import com.abive.domain.Domain;
import com.abive.framework.query.Query;

import java.util.List;

/**
 * Created by ranjiangchuan on 15/3/29.
 */
public interface BaseDao <T extends Domain> {


    Integer insert(T t);

    Integer delete(Object id);

    Integer realDelete(Object id);

    Integer update(T t);

    T find(Object id);

    List<T> list(Query query);

    List<T> page(Query query);

    List<T> listVo(Query query);

    List<Object> pageVo(Query query);

    Integer count(Query query);

    /**
     * 分批次批量更新
     *
     * @param domains
     * @param sqlID
     * @param pageSize
     */
    public int batchUpdate(List<T> domains, String sqlID, int pageSize);

    /**
     * 批量执行操作
     *
     * @param domains
     * @param option  操作项
     * @return
     */
    int batch(List<T> domains, Option option);

}