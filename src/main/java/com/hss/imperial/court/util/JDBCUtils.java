package com.hss.imperial.court.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 功能一：从数据源获取数据库连接
 * 功能二：从数据库获取到绑定到本地线程（借助ThreadLocal）
 * 功能三：释放线程和本地线程解除绑定
 */

public class JDBCUtils {
    // 数据源成员变量设置为静态资源，保证大对象的单例性。
    private static DataSource dataSource;

    // 由于 ThreadLocal 对象需要作为绑定数据时 k-v 对中的 key，所以要保证唯一性
    // 加 static 声明为静态资源即可保证唯一性
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    static {

        try {
            // 读取数据库信息
            // 为了保证程序的可移植性，需要一个基准来读取这个文件
            // 确定的基准： 类路径的根目录。resources 目录下的内容经过打包操作后会确定放在 WEB-INF/classes 目录下
            // WEB-INF/classes 目录存放编译好的 *.class 字节码文件，所以这个目录就称为类路径
            // 类路径无论在本地运行还是在服务器端运行都是一个确定的基准

            // 1. 获取当前类的加载器
            ClassLoader classLoader = JDBCUtils.class.getClassLoader();

            // 2. 通过类加载器对象从类路径的根目录下读取文件
            InputStream stream = classLoader.getResourceAsStream("jdbc.properties");

            // 3. 使用Properties 类封装属性文件中的数据
            Properties properties = new Properties();
            properties.load(stream);

            dataSource = DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e) {
            e.printStackTrace();
            // 为了避免真正抛出异常后， catch 块捕获到异常从而掩盖问题
            // 这里加所捕获的异常封装为运行时异常继续跑出
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {

        //1. 尝试从当前线程检查是否存在已经绑定的 Connection 对象
        Connection connection = threadLocal.get();
        try {


            // 2. 检查Connection对象是否为空
            if (connection == null) {
                // 3. 如果是空， 则从数据源获取数据库连接
                connection = dataSource.getConnection();

                // 4. 获取到数据库连接后 绑定到当前线程
                threadLocal.set(connection);
            }
        }catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();

                threadLocal.remove();
            } catch (SQLException e) {
                e.printStackTrace();
                throw  new RuntimeException(e);
            }
        }
    }
}
