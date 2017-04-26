package dataImport.ChineseVersion;


import java.sql.*;

/**
 * Created by ACER on 2017/4/19.
 */
public class dataImportDao {
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


    public static void insertXml(String elementName, String compound,String type){
        connection = getConnection();
        String sqlStr = "insert into DATA_DIC_CHN_XML(DATA_ELEMENT_NAME,SIMPLE_OR_COMPOUND,DATA_TYPE) values(?,?,?)";
        int count = 0;

        try {
            // 执行插入数据操作
            pstm = connection.prepareStatement(sqlStr);

            pstm.setString(1, elementName);
            pstm.setString(2, compound);
            pstm.setString(3, type);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource();
        }
    }

    public static void insertXml(String elementName, String compound,String type,int minLen,int maxLen){
        connection = getConnection();
        String sqlStr = "insert into DATA_DIC_CHN_XML(DATA_ELEMENT_NAME,SIMPLE_OR_COMPOUND,DATA_TYPE,MIN_LEN,MAX_LEN) values(?,?,?,?,?)";
        int count = 0;

        try {
            // 执行插入数据操作
            pstm = connection.prepareStatement(sqlStr);

            pstm.setString(1, elementName);
            pstm.setString(2, compound);
            pstm.setString(3, type);
            pstm.setInt(4, minLen);
            pstm.setInt(5, maxLen);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource();
        }
    }

    public static void insertXml(String elementName, String compound,String type,int minLen,int maxLen,int minVal,int maxVal){
        connection = getConnection();
        String sqlStr = "insert into DATA_DIC_CHN_XML(DATA_ELEMENT_NAME,SIMPLE_OR_COMPOUND,DATA_TYPE,MIN_LEN,MAX_LEN,MIN_VAL,MAX_VAL) values(?,?,?,?,?,?,?)";
        int count = 0;

        try {
            // 执行插入数据操作
            pstm = connection.prepareStatement(sqlStr);

            pstm.setString(1, elementName);
            pstm.setString(2, compound);
            pstm.setString(3, type);
            pstm.setInt(4, minLen);
            pstm.setInt(5, maxLen);
            pstm.setInt(6, minVal);
            pstm.setInt(7, maxVal);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource();
        }
    }


    public static void insertDic(String elementName, String acr,String format,String xmlName,String subElement,String attribute,
                                 String usage,String description,String code,String remark,String example){
        connection = getConnection();
        String sqlStr = "insert into DATA_DIC_CHN(DATA_ELEMENT_NAME,TEI_OR_ACRONYM,FORMAT,XML_DATA_TYPE," +
                "SUB_DATA_ELEMENTS,ATTRIBUTES,USAGE,DESCRIPTION_OR_PURPOSE,CODES,REMARKS,EXAMPLES) " +
                "values(?,?,?,?,?,?,?,?,?,?,?)";

        try {
            // 执行插入数据操作
            pstm = connection.prepareStatement(sqlStr);

            pstm.setString(1, elementName);
            pstm.setString(2, acr);
            pstm.setString(3, format);
            pstm.setString(4, xmlName);
            pstm.setString(5, subElement);
            pstm.setString(6, attribute);
            pstm.setString(7, usage);
            pstm.setString(8, description);
            pstm.setString(9, code);
            pstm.setString(10, remark);
            pstm.setString(11, example);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource();
        }

    }

    public static void insertSubElement(String name,String subElementName,String required,String repeatable){

        connection = getConnection();
        String insert = "insert into DATA_DIC_CHN_SUB(SUPER_DATA_ELEMENT,DATA_ELEMENT_NAME,REQUIED,REPEATABLE) values(?,?,?,?)";

        required = required.replace("\\s","");
        try {
            // 执行插入数据操作
            pstm = connection.prepareStatement(insert);
            pstm.setString(1, name);
            pstm.setString(2, subElementName);
            pstm.setString(3, required);
            pstm.setString(4, repeatable);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ReleaseResource();
        }
    }

    public static void insertCode(String name,String codeName,String description,String specification){

        connection = getConnection();

        String insert = "insert into DATA_DIC_CHN_CODE(DATA_ELEMENT_NAME,CODE_NAME,DESCRIPTION,SPECIFICATION) values(?,?,?,?)";

        try {
            // 执行插入数据操作
            pstm = connection.prepareStatement(insert);
            pstm.setString(1, name);
            pstm.setString(2, codeName);
            pstm.setString(3, description);
            pstm.setString(4, specification);
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

    public static boolean HasTable(String name) {
        //判断某一个表是否存在
        boolean result = false;
        try {
            DatabaseMetaData meta = connection.getMetaData();//sqlConn 数据库连接
            ResultSet set = meta.getTables (null, null, name, null);
            while (set.next()) {
                result = true;
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace ();
        }
        return result;
    }

}
