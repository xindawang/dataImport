package data2;

import java.sql.*;

/**
 * Created by ACER on 2017/4/26.
 */
public class dao2 {
    private static String USERNAMR = "DATADIC";
    private static String PASSWORD = "DATADIC";
    private static String DRVIER = "oracle.jdbc.driver.OracleDriver";
    private static String URL = "jdbc:oracle:thin:@localhost:1521:ORCL";

    // 创建一个数据库连接
    static Connection connection = null;
    // 创建预编译语句对象，一般都是用这个而不用Statement
    static PreparedStatement pstm = null;
    // 创建一个结果集对象
    static ResultSet rs = null;


    public static void insertSON(String son,String des,String description){
        connection = getConnection();
        String sqlStr = "insert into STANDARD_OBSERVATION_NUMBERS(SON,Description,描述) values(?,?,?)";
        int count = 0;

        try {
            // 执行插入数据操作
            pstm = connection.prepareStatement(sqlStr);

            pstm.setString(1, son);
            pstm.setString(2, des);
            pstm.setString(3, description);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource();
        }
    }

    public static Connection getConnection() {
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

    public static void ReleaseResource() {
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
