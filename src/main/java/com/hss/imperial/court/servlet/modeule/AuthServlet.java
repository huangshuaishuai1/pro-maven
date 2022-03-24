package com.hss.imperial.court.servlet.modeule;

import com.hss.imperial.court.entity.Emp;
import com.hss.imperial.court.exception.LoginFailedException;
import com.hss.imperial.court.service.api.EmpService;
import com.hss.imperial.court.service.impl.EmpServiceImpl;
import com.hss.imperial.court.servlet.base.ModelBaseServlet;
import com.hss.imperial.court.util.ImperialCourtConst;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends ModelBaseServlet {
    private EmpService empService = new EmpServiceImpl();
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1 .获取请求参数（登录账号和密码）
            String loginAccount = request.getParameter("loginAccount");
            String loginPassword = request.getParameter("loginPassword");
            //2. 调用 EmpService getEmpByLoginAccount
            Emp emp = empService.getEmpByLoginAccount(loginAccount, loginPassword);
            //3. 通过request 获取 HttpSession 域
            HttpSession session = request.getSession();
            //4.将查询到的 Emp对象存入 Session 域
            session.setAttribute(ImperialCourtConst.LOGIN_EMP_ATTR_NAME, emp);
            //5.前往指定的页面视图
//            String templateName = "temp";
//            processTemplate(templateName, request, response);

            response.sendRedirect(request.getContextPath() + "/work?method=showMemorialsDigestList");
        }catch (Exception e){
            e.printStackTrace();
            // 6.此处捕获异常
            if (e instanceof LoginFailedException) {
                //7.如果是登录失败异常则跳转到登录页面
                // 将异常信息存入请求域
                request.setAttribute("message", e.getMessage());
                //处理视图 index
                processTemplate("index",request,response);
            }else {
                //8.如果不是登录异常则封装为运行时异常抛出去
                throw new RuntimeException();
            }
        }
    }
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();

        String templateName = "index";
        processTemplate(templateName, request, response);
    }
}
