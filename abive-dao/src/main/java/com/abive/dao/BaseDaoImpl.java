package com.abive.dao;

import com.abive.domain.Domain;
import com.abive.framework.query.Query;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ranjiangchuan on 15/3/29.
 */

public class BaseDaoImpl<T extends Domain> extends SqlSessionDaoSupport implements BaseDao<T> {
    public String getNameSpace() {
        return this.getClass().getName();
    }

    public Integer insert(T t) {
        this.getSqlSession().insert(getNameSpace() + ".insert", t);
        return t.getId();
    }

    public Integer update(T t) {
        return this.getSqlSession().update(this.getNameSpace() + ".update", t);
    }

    public Integer delete(Object id) {
        return this.getSqlSession().update(this.getNameSpace() + ".delete", id);
    }

    public Integer realDelete(Object id) {
        return this.getSqlSession().delete(this.getNameSpace() + ".realDelete", id);
    }

    @SuppressWarnings("unchecked")
    public T find(Object id) {
        return (T) this.getSqlSession().selectOne(this.getNameSpace() + ".find", id);
    }

    public List<T> list(Query query) {
        return this.getSqlSession().selectList(this.getNameSpace() + ".list", query);
    }

    public List<T> page(Query query) {
        return this.getSqlSession().selectList(this.getNameSpace() + ".page", query);
    }

    public List<T> listVo(Query query) {
        return this.getSqlSession().selectList(this.getNameSpace() + ".listVo", query);
    }

    public List<Object> pageVo(Query query) {
        return this.getSqlSession().selectList(this.getNameSpace() + ".pageVo", query);
    }

    public Integer count(Query query) {
        return (Integer) this.getSqlSession().selectOne(this.getNameSpace() + ".count", query);
    }

    /**
     * 批量执行操作
     */
    @Transactional
    public int batch(List<T> domains, Option option) {
        if (domains == null || domains.size() == 0) {
            return 0;
        }

        int count = 0;
        SqlSession sqlSession = this.getSqlSession();
        for (T domain : domains) {
            int result = 0;
            switch (option) {
                case INSERT:
                    result = sqlSession.insert(this.getNameSpace() + "." + option.getId(), domain);
                    break;
                case UPDATE:
                    result = sqlSession.update(this.getNameSpace() + "." + option.getId(), domain);
                    break;
                case DELETE:
                    /* 通过设置mark = 1 标志删除 */
                    result = sqlSession.update(this.getNameSpace() + "." + option.getId(), domain);
                    break;
                case REAL_DELETE:
                    /* 真删 */
                    result = sqlSession.delete(this.getNameSpace() + "." + option.getId(), domain);
                    break;
            }

            if (result > 0) {
                count++;
            }
        }
        return count;
    }

    /**
     * 分批次批量更新
     */
    @Transactional
    public int batchUpdate(List<T> domains, String sqlID, int pageSize) {
        if (CollectionUtils.isEmpty(domains)) {
            return 0;
        }

        int affect = 0;
        int size = domains.size();
        int startIndex = 0;
        int index = pageSize;

        int count = (size % index == 0) ? (size / index) : (size / index) + 1;

        SqlSession session = getSqlSession();
        if (size <= index) {
            session.update(getNameSpace() + "." + sqlID, domains);
        } else {
            boolean tag = false;
            for (int i = 0; i < count; i++) {
                if (tag) {
                    break;
                }
                if (index > size) {
                    index = size;
                    tag = true;
                }
                List<T> tempLists = domains.subList(startIndex, index);

                int result = session.update(getNameSpace() + "." + sqlID, tempLists);
                startIndex = index;
                index += pageSize;

                if (result > 0) {
                    affect++;
                }
            }
        }

        return affect;
    }

}
