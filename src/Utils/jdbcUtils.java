package Utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @ClassName jdbcUtils
 * @Description TODO
 * @Author xpower
 * @Date 2022/9/27 10:06
 * @Version 1.0
 */
public class jdbcUtils {
    public static Connection getConnection() throws Exception {
        InputStream resourceAsStream = jdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        //读取配置文件
        properties.load(resourceAsStream);
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        //注册驱动 获取连接
        Connection connection = DriverManager.getConnection(url, username, password);
        //返回连接
        return connection;
    }

    //关闭资源
    public static void CloseResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void CloseResource(Connection connection, PreparedStatement preparedStatement) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
