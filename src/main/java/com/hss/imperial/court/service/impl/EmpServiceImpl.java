package com.hss.imperial.court.service.impl;

import com.hss.imperial.court.dao.api.EmpDao;
import com.hss.imperial.court.dao.impl.EmpDaoImpl;
import com.hss.imperial.court.entity.Emp;
import com.hss.imperial.court.exception.LoginFailedException;
import com.hss.imperial.court.service.api.EmpService;
import com.hss.imperial.court.util.ImperialCourtConst;
import com.hss.imperial.court.util.MD5Util;

public class EmpServiceImpl implements EmpService {

    private EmpDao empDao = new EmpDaoImpl();

    @Override
    public Emp getEmpByLoginAccount(String loginAccount, String loginPassword) {
        // 1. 对密码执行加密
//        String encodedPassword = MD5Util.encode(loginAccount);

        //2. 根据账户和加密密码查询数据库
        Emp emp = empDao.selectEmpByLoginAccount(loginAccount,loginPassword);
        // 3 检查是否为null
        if (emp != null) {
            // 不是null 返回emp
            return emp;
        }else {
            //为null 抛出异常
            throw new LoginFailedException(ImperialCourtConst.LOGIN_FAILED_MESSAGE);
        }
    }
}
