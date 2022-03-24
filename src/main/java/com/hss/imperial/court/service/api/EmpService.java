package com.hss.imperial.court.service.api;

import com.hss.imperial.court.entity.Emp;

public interface EmpService {
    Emp getEmpByLoginAccount(String loginAccount, String loginPassword);
}
