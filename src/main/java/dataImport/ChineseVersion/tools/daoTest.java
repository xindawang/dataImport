package dataImport.ChineseVersion.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class daoTest {

    // 定义连接所需的字符串
    // 192.168.0.X是本机地址(要改成自己的IP地址)，1521端口号，XE是精简版Oracle的默认数据库名
    private static String USERNAMR = "datadic";
    private static String PASSWORD = "datadic";
    private static String DRVIER = "oracle.jdbc.driver.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";

    // 创建一个数据库连接
    Connection connection = null;
    // 创建预编译语句对象，一般都是用这个而不用Statement
    PreparedStatement pstm = null;
    // 创建一个结果集对象
    ResultSet rs = null;


    public static void main(String[] args) {

        daoTest dt=new daoTest();
        dt.insertXml("QWEF");
    }


    public void insertXml(String elementName){
        connection = getConnection();

//        String sqlStr = "CREATE TABLE "+elementName+"(SUB_ELEMENT_NAME CHAR(80) PRIMARY KEY, required CHAR(10), repeatable CHAR(15))";
        String sqlStr = "SELECT * FROM DATA_DIC_ENG";
        int count = 0;

        try {
            // 执行插入数据操作
            pstm = connection.prepareStatement(sqlStr);
            pstm.executeUpdate();
            rs = pstm.executeQuery();
            while (rs.next()){
                System.out.println(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource();
        }
    }

    /**
     * 获取Connection对象
     *
     * @return
     */
    public Connection getConnection() {
        try {
            Class.forName(DRVIER);
            connection = DriverManager.getConnection(URL, USERNAMR, PASSWORD);
            System.out.println("成功连接数据库");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("class not find !", e);
        } catch (SQLException e) {
            throw new RuntimeException("get connection error!", e);
        }

        return connection;
    }

    /**
     * 释放资源
     */
    public void ReleaseResource() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}