package core;

import java.util.List;
import java.util.Map;

/**
 * DAO支持类实现
 */
public interface BaseMapper<T> {

    /**
     * 获取单条数据
     */
    public T get(Long id);

    /**
     * 获取单条数据
     */
    public T get(T entity);

    /**
     * 查询数据列表
     */
    public List<T> findList(Map<String, Object> map);

    /**
     * 查询数据列表
     */
    public List<T> findListAll(Map<String, Object> map);

    /**
     * 列表数量
     */
    public int findCount(Map<String, Object> map);

    /**
     * 插入数据
     */
    public int save(T entity);

    /**
     * 更新数据
     */
    public int update(T entity);

    /**
     * 删除数据
     */
    public int delete(Long id);

    /**
     * 删除数据（一般为逻辑删除，更新del_flag字段为1）
     */
    public int delete(T entity);
}