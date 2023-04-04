package core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

/**
 * Service基类
 */
public abstract class BaseService<D extends BaseMapper<T>, T> {

    /**
     * 持久层对象
     */
    @Inject
    protected D dao;

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(Long id) {
        return dao.get(id);
    }

    /**
     * 获取单条数据
     *
     * @param entity
     */
    public T get(T entity) {
        return dao.get(entity);
    }

    /**
     * 查询列表数据
     */
    public List<T> findList(Map<String, Object> map) {
        return dao.findList(map);
    }

    public List<T> findListAll(Map<String, Object> map) {
        return dao.findListAll(map);
    }

    /**
     * 查询列表总数量
     */
    public int findCount(Map<String, Object> map) {
        return dao.findCount(map);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     */
    @Transactional
    public boolean save(T entity) {
        boolean flag = false;
        try {
            if (null == get(entity)) {
                dao.save(entity);
            } else {
                dao.update(entity);
            }
            flag = true;
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return flag;
    }

    /**
     * 更新数据
     *
     * @param entity
     */
    @Transactional
    public int update(T entity) {
        try {
            return dao.update(entity);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 删除数据
     *
     * @param entity
     */
    @Transactional
    public int delete(T entity) {
        try {
            return dao.delete(entity);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * 删除数据
     *
     * @param id
     */
    @Transactional
    public int delete(Long id) {
        try {
            return dao.delete(id);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
