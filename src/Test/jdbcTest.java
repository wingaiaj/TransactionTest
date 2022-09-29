package Test;

import BaseDao.Dao;
import Utils.jdbcUtils;
import org.junit.Test;
import pojo.customer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName jdbcTest
 * @Description TODO
 * @Author xpower
 * @Date 2022/9/27 11:16
 * @Version 1.0
 */
public class jdbcTest {
    Dao dao = new Dao();
    Connection connection;

    {
        try {
            connection = jdbcUtils.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void jdTest() throws FileNotFoundException {
        //向表中插入一条数据
        Connection connection = null;
        try {
            connection = jdbcUtils.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql = "insert into tbl_cust value(?,?,?,?)";
        FileInputStream fileInputStream = new FileInputStream(new File("src/jr.jpg"));
        boolean zx = dao.update(connection, sql, 2, "zx", 20, fileInputStream);
        System.out.println(zx);
    }

    @Test
    public void Test2() {
        //更改数据
        Connection connection = null;
        try {
            connection = jdbcUtils.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql = "update tbl_cust set name=? where id = ?";
        boolean zc = dao.update(connection, sql, "zc", 1);
        System.out.println(zc);
    }

    @Test
    public void Test3() {
        //查询操作
        String sql = "select id,name,age from tbl_cust";
        List<customer> select = dao.select(connection,customer.class, sql);
        System.out.println(select.size());
        System.out.println(select);
    }

    @Test
    public void transactionTest() {
        Connection connection = null;
        try {
            connection = jdbcUtils.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println(connection.getAutoCommit());
            //取消自动提交
            connection.setAutoCommit(false);

            String sql1 = "update tbl_cust set age=age-10 where id = ?";
            boolean update1 = dao.update(connection, sql1, 1);
            System.out.println(update1);

            //异常
            System.out.println(2 / 0);

            String sql2 = "update tbl_cust set age=age+10 where id = ?";

            boolean update = dao.update(connection, sql2, 2);
            System.out.println(update);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            //关闭连接
            jdbcUtils.CloseResource(connection, null);
        }
    }

    //验证事务的隔离级别
    @Test
    public void updateTransaction() {
        try {
            //当前隔离级别为读已提交
            //设置为读未提交
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            //取消自动提交
            connection.setAutoCommit(false);
            //修改数据
            String sql = "update tbl_cust set age = 5 where id = ?";
            boolean update = dao.update(connection, sql, 1);
            System.out.println(update);
            //查询数据
            String sql1 = "select id,name,age from tbl_cust where id = ?";
            List<customer> select = dao.select(connection,customer.class, sql1, 1);
            System.out.println(select);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
