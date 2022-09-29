package BaseDao;

import Utils.jdbcUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Dao
 * @Description TODO
 * @Author xpower
 * @Date 2022/9/27 10:39
 * @Version 1.0
 */
public class Dao {
    //通用增删改查
    public boolean update(Connection connection,String sql, Object... args) {
//        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //获取连接
//            connection = jdbcUtils.getConnection();
            //预编译sql
            preparedStatement = connection.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            jdbcUtils.CloseResource(null, preparedStatement);
        }
        return false;
    }

    //通用查询操作
    public <T> List<T> select(Connection connection,Class<T> clazz, String sql, Object... args) {
//        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //获取连接
//            connection = jdbcUtils.getConnection();
            //预编译sql
            preparedStatement = connection.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            //执行sql 获取结果集
            resultSet = preparedStatement.executeQuery();
            //获取结果集元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //创建集合
            ArrayList<T> arrayList = new ArrayList<>();
            //获取结果集列数
            int columnCount = metaData.getColumnCount();
            //判断结果集是否有数据
            while (resultSet.next()) {
                //创建对象
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //获取列名
                    String columnName = metaData.getColumnName(i + 1);
                    //通过列名获取属性值
                    Object value = resultSet.getObject(columnName);
                    //通过列名获取属性
                    Field declaredField = t.getClass().getDeclaredField(columnName);
                    declaredField.setAccessible(true);
                    //给对象赋值
                    declaredField.set(t, value);
                }
                //添加到集合
                arrayList.add(t);
            }
            //返回集合
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            jdbcUtils.CloseResource(null, preparedStatement, resultSet);
        }
        return null;
    }
}
