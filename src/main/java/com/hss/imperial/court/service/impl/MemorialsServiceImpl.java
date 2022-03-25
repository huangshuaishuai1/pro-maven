package com.hss.imperial.court.service.impl;

import com.hss.imperial.court.dao.api.MemorialsDao;
import com.hss.imperial.court.dao.impl.MemorialsDaoImpl;
import com.hss.imperial.court.entity.Memorials;
import com.hss.imperial.court.service.api.MemorialsService;

import java.util.List;

public class MemorialsServiceImpl implements MemorialsService {

    private MemorialsDao memorialsDao = new MemorialsDaoImpl();


    @Override
    public List<Memorials> getAllMemorialsDigest() {


        return memorialsDao.selectAllMemorialsDigest();
    }

    @Override
    public Memorials getMemorialsDetailById(String memorialsId) {

        return memorialsDao.selectMemorialsById(memorialsId);
    }

    @Override
    public void updateMemorialsFeedBack(String memorialsId, String feedbackContent) {
        memorialsDao.updateMemorialsFeedBack(memorialsId, feedbackContent);
    }

    @Override
    public void updateMemorialsStatusToRead(String memorialsId) {
        memorialsDao.updateMemorialsStatusToRead(memorialsId);
    }
}
