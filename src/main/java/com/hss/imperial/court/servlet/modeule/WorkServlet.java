package com.hss.imperial.court.servlet.modeule;

import com.hss.imperial.court.entity.Memorials;
import com.hss.imperial.court.service.api.MemorialsService;
import com.hss.imperial.court.service.impl.MemorialsServiceImpl;
import com.hss.imperial.court.servlet.base.ModelBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.MemoryUsage;
import java.util.List;

public class WorkServlet extends ModelBaseServlet {
    private MemorialsService memorialsService = new MemorialsServiceImpl();


    protected void showMemorialsDigestList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1. 调用Service 查询数据
        List<Memorials> list = memorialsService.getAllMemorialsDigest();

        //2. 将查询到的数据存入属性域
        request.setAttribute("memorialsList", list);

        //3. 渲染视图
        String template = "memorials-list";
        processTemplate(template, request, response);

    }


    protected void showMemorialsDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. 从请求参数读取 memorialsId
        String memorialsId = request.getParameter("memorialsId");

        // 2. 根据memorialsId 从 Service中查询Memorials 对象

        Memorials memorials = memorialsService.getMemorialsDetailById(memorialsId);
        System.out.println(memorials);
        //3.将memorials 对象存入请求域
        request.setAttribute("memorials", memorials);

//        4.解析渲染视图
        String templateName = "memorials_detail";
        processTemplate(templateName, request, response);

    }
}
