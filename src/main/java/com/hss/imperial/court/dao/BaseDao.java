package com.hss.imperial.court.dao;

import com.hss.imperial.court.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * BaseDao 类是所有Dao实现类的基类
 * @param <T> Dao 方法种用到泛型就是实体类的类型
 */
public class BaseDao<T> {
    // DBUtils 工具包提供的数据库操作对象
    private QueryRunner runner = new QueryRunner();

    /**
     * 查询单个对象
     * @param sql 执行查询的sql语句
     * @param entityClass 实体类对应的class 对象
     * @param parameters 传给sql 语句的参数
     * @return 查询到的实体对象
     */
    public T getSingleBean(String sql, Class<T> entityClass, Object ... parameters) {
        try {
            //1. 获取数据库连接
            Connection connection = JDBCUtils.getConnection();

            return runner.query(connection, sql, new BeanHandler<>(entityClass), parameters);
        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }


    }

    /**
     * 通过的增删改方法
     * @param sql 执行操作的SQL语句
     * @param parameters SQL语句的参数
     * @return 受影响的行数
     */
    public int update(String sql, Object... parameters) {
        Connection connection = JDBCUtils.getConnection();
        try {
            return runner.update(connection,sql,parameters);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询获得多个对象
     * @param sql 执行操作的SQL语句
     * @param entityClass 实体类的Class对象
     * @param parameters Sql语句的参数
     * @return 查询结果
     */
    public List<T> getBeanList(String sql, Class<T> entityClass, Object...parameters) {
        try {
            Connection connection = JDBCUtils.getConnection();

            return runner.query(connection, sql, new BeanListHandler<>(entityClass), parameters);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
