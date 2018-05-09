package info.unclewang;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * unclewang
 * 2018/5/4 09:59
 * QueryRunner类、DBUtils类和Druid数据库连接池综合使用
 */
public class DbUtilsDruid {
    private static Logger logger = LoggerFactory.getLogger(DbUtilsDruid.class);
    private static DruidDataSource dds = null;
    private static DruidPooledConnection con = null;
    private static String filepath = "db.properties";

    private static class SingletonHolder {
        private static final DbUtilsDruid INSTANCE = new DbUtilsDruid();
    }


    public static final DbUtilsDruid getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private DbUtilsDruid() {
        init(filepath);
    }

    private void init(String filepath) {
        Properties properties = loadPropertyFile(filepath);
        try {
            dds = (DruidDataSource) DruidDataSourceFactory
                    .createDataSource(properties);
            con = dds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Properties loadPropertyFile(String fullFile) {
        Properties p = new Properties();
        if (fullFile == "" || fullFile.equals("")) {
            System.out.println("属性文件为空!");
        } else {
            InputStream inStream = DbUtilsDruid.class.getClassLoader().getResourceAsStream(fullFile);
            try {
                p.load(inStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    public DruidPooledConnection getCon() {
        return con;
    }

    public static String getFilepath() {
        return filepath;
    }

    public static void setFilepath(String filepath) {
        DbUtilsDruid.filepath = filepath;
    }

    /**
     * 如果db.properties文件放在默认的resources文件夹下，则用默认的crul方法
     */
    public int crud(String sql, Object... param) {
        int row = 0;
        DruidPooledConnection dpc = DbUtilsDruid.getInstance().getCon();
        QueryRunner qr = new QueryRunner();
        try {
            row = qr.update(dpc, sql, param);
//            logger.info(String.valueOf(row));
        } catch (SQLException e) {
            return row;
        } catch (ArrayIndexOutOfBoundsException e) {
            return row;
        }
        return row;
    }

    public int crud(String sql, Object[][] param) {
        int row = 0;
        DruidPooledConnection dpc = DbUtilsDruid.getInstance().getCon();
        QueryRunner qr = new QueryRunner();
        try {
            row = qr.batch(dpc, sql, param).length;
//            logger.info(String.valueOf(row));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return row;
    }

    /**
     * 把如果修改了db.properties文件路径，则应该 先DbUtilsDruid.setFilepath("db_new.properties");
     * 连接则把dpc传进去
     * DbUtilsDruid dd = DbUtilsDruid.getInstance();
     * Connection connection = dd.getCon();
     */
    public int crud(Connection dpc, String sql, Object... param) {
        int row = 0;
        QueryRunner qr = new QueryRunner();
        try {
            row = qr.update(dpc, sql, param);
            logger.info(String.valueOf(row));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row;
    }

    public Object query(String sql, ResultSetHandler rsh, Object... param) {
        DruidPooledConnection dpc = DbUtilsDruid.getInstance().getCon();
        return this.query(dpc, sql, rsh, param);
    }


    /**
     * 把如果修改了db.properties文件路径，则应该 先DbUtilsDruid.setFilepath("db_new.properties");
     * 连接则把dpc传进去
     * DbUtilsDruid dd = DbUtilsDruid.getInstance();
     * Connection connection = dd.getCon();
     */
    public Object query(Connection dpc, String sql, ResultSetHandler rsh, Object... param) {
        Object result = "";
        QueryRunner qr = new QueryRunner();
        try {

            if (rsh instanceof MapListHandler) {
                List<Map<String, Object>> listMapResult = (List<Map<String, Object>>) qr.query(dpc, sql, rsh, param);
                return listMapResult;
            }
            if (rsh instanceof ArrayHandler) {
                Object[] arrayResult = (Object[]) qr.query(dpc, sql, rsh, param);
                return arrayResult;
            }
            if (rsh instanceof ArrayListHandler) {
                List<Object[]> listResult = (List<Object[]>) qr.query(dpc, sql, rsh, param);
                return listResult;
            }
            if (rsh instanceof BeanListHandler) {
                List<Object> beanResult = (List<Object>) qr.query(dpc, sql, rsh, param);
                return beanResult;
            }
            if (rsh instanceof BeanHandler) {
                Object o = qr.query(dpc, sql, rsh, param);
                return o;
            }
            if (rsh instanceof ColumnListHandler) {
                //指定列名
                List<Object> o = (List<Object>) qr.query(dpc, sql, rsh, param);
                return o;
            }
            if (rsh instanceof ScalarHandler) {
                // SELECT COUNT(*) FROM sort
                long o = (Long) qr.query(dpc, sql, rsh, param);
                return o;
            }
            if (rsh instanceof MapHandler) {
                Map<String, Object> map = (Map<String, Object>) qr.query(dpc, sql, rsh, param);
                return map;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        logger.error("没有查到任何结果，有问题");
        return result;
    }

    public static void main(String[] args) throws SQLException {

        //DbUtilsDruid.setFilepath("db_server.properties");
        DbUtilsDruid dd = DbUtilsDruid.getInstance();
        Connection connection = dd.getCon();
        //写删除的SQL语句
        String crudSql = "DELETE FROM demo_copy1";
        //写插入的语句
        String insertsql = "insert into demo values(?,?,?,?)";
        //测试crud
        dd.crud(connection, insertsql, "", "", "", "");
        //查询的SQL语句
        String querySql = "SELECT * From demo";
        //MapListHandler测试查询
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) dd.query(connection, querySql, new MapListHandler());
        for (Map<String, Object> map : mapList) {
            System.out.println(map);
        }

        //ArrayHandler测试仅能查找一个
        Object[] arrayResult = (Object[]) dd.query(connection, querySql, new ArrayHandler());
        for (Object o : arrayResult) {
            System.out.println(o.toString());
        }

        //ArrayListHandler测试
        List<Object[]> arrayList = (List<Object[]>) dd.query(connection, querySql, new ArrayListHandler());
        for (Object[] oa : arrayList) {
            for (Object o : oa) {
                System.out.print(o.toString() + "\t");
            }
            System.out.println();
        }

        //BeanHandler测试,返回第一个
        Demo demo = (Demo) dd.query(connection, querySql, new BeanHandler(Demo.class));
        System.out.println(demo.toString());


        //BeanListHandler测试
        System.out.println("-----");
        List<Demo> demoList = (List<Demo>) dd.query(connection, querySql, new BeanListHandler(Demo.class));
        for (Demo oa : demoList) {
            System.out.println(oa);
        }

        //ColumnListHandler测试
        List<String> ids = (List<String>) dd.query(connection, querySql, new ColumnListHandler("id"));
        for (String oa : ids) {
            System.out.println(oa);
        }

        //ScalarHandler测试
        long counts = (Long) dd.query(connection, "SELECT COUNT(*) FROM demo", new ScalarHandler());
        System.out.println("demo的个数：" + counts);
        DbUtils.closeQuietly(dd.getCon());
    }
}