package com.hss.imperial.court.test;


import com.hss.imperial.court.dao.BaseDao;
import com.hss.imperial.court.entity.Emp;
import com.hss.imperial.court.util.JDBCUtils;
import com.mysql.cj.util.DnsSrv;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

public class ImperialCourtTest {
    private BaseDao<Emp> baseDao = new BaseDao<>();

    @Test
    public void queryForOne() {
        String sql = "select emp_id empId, emp_name empName, emp_position empPosition, login_account loginAccount, login_password loginPassword from t_emp where emp_id=?";
        Emp singleBean = baseDao.getSingleBean(sql, Emp.class, 1);
        System.out.println(singleBean);
    }
    @Test
    public void queryForOne1() {
        String sql = "select emp_position empPosition from t_emp where emp_id=?";
        Emp singleBean = baseDao.getSingleBean(sql, Emp.class, 1);
        System.out.println(singleBean);
    }
    @Test
    public void testQueryForList() {
        String sql = "select emp_id empId, emp_name empName, emp_position empPosition, login_account loginAccount, login_password loginPassword from t_emp";
        List<Emp> beanList = baseDao.getBeanList(sql, Emp.class);
        System.out.println(beanList);
    }

    @Test
    public void testUpdate() {
        String sql = "update t_emp set emp_position=? where emp_id=?";

        String empPosition = "emperor";
        String empId = "3";
        int update = baseDao.update(sql,empPosition, empId);
        System.out.println(update);
    }

    @Test
    public void testGetConnection() {
        Connection connection = JDBCUtils.getConnection();
        System.out.println(connection);

        JDBCUtils.releaseConnection(connection);
    }

    @Test
    public void testSubString() {
        String subString = "aaa.png".substring("aaa.png".lastIndexOf("."));
        System.out.println("substring = " + subString);
    }
}
