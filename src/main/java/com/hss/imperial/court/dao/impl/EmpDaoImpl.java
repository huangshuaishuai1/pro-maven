package com.hss.imperial.court.dao.impl;

import com.hss.imperial.court.dao.BaseDao;
import com.hss.imperial.court.dao.api.EmpDao;
import com.hss.imperial.court.entity.Emp;

public class EmpDaoImpl extends BaseDao<Emp> implements EmpDao {
    @Override
    public Emp selectEmpByLoginAccount(String loginAccount, String encodedPassword) {

        //1. 写sql语句
        String sql = "select emp_id empId, emp_name empName," +
                " emp_position empPosition, " +
                "login_account loginAccount," +
                " login_password loginPassword " +
                "from t_emp where login_account=? and login_password=?";

        //2. 调用父类的方法查询单个对象
        Emp singleBean = super.getSingleBean(sql, Emp.class, loginAccount, encodedPassword);
        System.out.println(singleBean);
        return singleBean;


    }
}
