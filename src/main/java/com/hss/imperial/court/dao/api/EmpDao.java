package com.hss.imperial.court.dao.api;

import com.hss.imperial.court.entity.Emp;

public interface EmpDao {
    Emp selectEmpByLoginAccount(String loginAccount, String encodedPassword);
}
