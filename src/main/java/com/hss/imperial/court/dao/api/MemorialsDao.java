package com.hss.imperial.court.dao.api;

import com.hss.imperial.court.entity.Memorials;

import java.util.List;

public interface MemorialsDao {
    List<Memorials> selectAllMemorialsDigest();

    Memorials selectMemorialsById(String memorialsId);
}
