package com.hss.imperial.court.service.api;

import com.hss.imperial.court.entity.Memorials;

import java.util.List;

public interface MemorialsService {
    List<Memorials> getAllMemorialsDigest();

    Memorials getMemorialsDetailById(String memorialsId);

    void updateMemorialsFeedBack(String memorialsId, String feedbackContent);

    void updateMemorialsStatusToRead(String memorialsId);
}
