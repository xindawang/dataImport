package data1;

import java.sql.*;

/**
 * Created by ACER on 2017/4/26.
 */
public class dao1 {
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


    public static void insertSON(String a,String b,String c,String d,String e,
                                 String f,String g,String h,String i,String j,
                                 String k,String l,String m,String n,String o,
                                 String p,String q,String r,String s){
        connection = getConnection();
        String sqlStr = "insert into Business_Rule(TABLE_NAME,DATA_ELEMENT_NAME," +
                "TEI_OR_ACRONYM,Draft,Formal,Master,Update_1,Restate," +
                "PartOP_PM,CatalogueOP_PM,PartOP_PUM,CatalogueOP_PUM," +
                "Provisioning_Programme_Message,CODREQ,Tailorization_Possible,Applicabillity_Spare," +
                "Applicabillity_Non_Spare,Business_Rule,业务规则) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            // 执行插入数据操作
            pstm = connection.prepareStatement(sqlStr);

            pstm.setString(1, a);
            pstm.setString(2, b);
            pstm.setString(3, c);
            pstm.setString(4, d);
            pstm.setString(5, e);
            pstm.setString(6, f);
            pstm.setString(7, g);
            pstm.setString(8, h);
            pstm.setString(9, i);
            pstm.setString(10, j);
            pstm.setString(11, k);
            pstm.setString(12, l);
            pstm.setString(13, m);
            pstm.setString(14, n);
            pstm.setString(15, o);
            pstm.setString(16, p);
            pstm.setString(17, q);
            pstm.setString(18, r);
            pstm.setString(19, s);




            pstm.executeUpdate();
        } catch (SQLException z) {
            z.printStackTrace();
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
