package com.hss.imperial.court.filter;

import com.hss.imperial.court.util.JDBCUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TransectionFilter implements Filter {
    // 声明集合保存静态资源扩展名
    private static Set<String> staticResourceExtNameSet;
    static {
        staticResourceExtNameSet = new HashSet<>();
        staticResourceExtNameSet.add(".png");
        staticResourceExtNameSet.add(".jpg");
        staticResourceExtNameSet.add(".css");
        staticResourceExtNameSet.add(".js");


    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 前置操作：排除静态组员
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String servletPath = request.getServletPath();
        if (servletPath.contains(".")) {

            String extName = servletPath.substring(servletPath.lastIndexOf("."));

            if (staticResourceExtNameSet.contains(extName)) {
                // 如果检测到是静态资源，直接放行。
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }
        }


        Connection connection = null;
        try {
            // 1.获取数据库链接
            connection = JDBCUtils.getConnection();

            // 重要操作：关闭自动提交功能
            connection.setAutoCommit(false);


            //2. 核心操作
            filterChain.doFilter(servletRequest, servletResponse);

            //3. 提交事务
            connection.commit();
        } catch (SQLException throwables) {
            try {
                //4. 回滚事务
                connection.rollback();
            }catch (Exception e) {
                e.printStackTrace();
            }
            String message = throwables.getMessage();

            // 将异常信息存入请求域
            request.setAttribute("systemMessage", message);
            // 将请求转发到指定页面
            request.getRequestDispatcher("/").forward(request, servletResponse);
        } finally {
            // 5. 释放数据库链接
            JDBCUtils.releaseConnection(connection);
        }
    }

    @Override
    public void destroy() {

    }
}
