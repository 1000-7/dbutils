package info.unclewang;

import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * @Author unclewang
 * @Date 2018/5/8 23:13
 */
public class DataClean {
    public static void main(String[] args) {
        DbUtilsDruid dd = DbUtilsDruid.getInstance();
        DruidPooledConnection connection = dd.getCon();
        String querySql = "SELECT * From expert";
        String insertSql = "insert into article_cnki values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        //MapListHandler测试查询
        List<ExpertEntity> expertEntityList = (List<ExpertEntity>) dd.query(connection, querySql, new BeanListHandler(ExpertEntity.class));
        Paper paper = new Paper();
        int id = 0;

        for (ExpertEntity ee : expertEntityList) {
            if (ee.getAuthorCn() == null) {
                continue;
            }
            if (ee.getOrgan() == null) {
                ee.setOrgan("-1");
            }
            paper.setId(ee.getTid());
            paper.setProceeding(0);
            paper.setYear(Integer.valueOf(replace(ee.getYear())));
            paper.setDate(ee.getYear() + "-" + ee.getIssue());
            paper.setTitle(ee.getTitleCn().trim());
            paper.setKeywords(replace(ee.getKwCn()));
            paper.setSummary(replace(ee.getAbsCn()));
            paper.setAuid("-1");
            paper.setAutext(replace(ee.getAuthorCn()));
            paper.setInstid("-1");
            paper.setInstext(replace(ee.getOrgan()));
            paper.setSemid((long) 0);
            paper.setCategory(replace(ee.getJournalCn()));
            paper.setTopic("0");
            for (Object o : transfer(paper)) {
                System.out.println(o.toString());
            }
            System.out.println(id++);
            dd.crud(insertSql, transfer(paper));

        }

    }

    public static Object[] transfer(Paper paper) {
        Object[] a = new Object[]{paper.getId(), paper.getProceeding(),
                paper.getYear(), paper.getDate(), paper.getTitle(), paper.getKeywords(),
                paper.getSummary(), paper.getAuid(), paper.getAutext(), paper.getInstid(),
                paper.getInstext(), paper.getSemid(), paper.getCategory() + "", paper.getTopic()};
        return a;
    }

    public static String replace(String s) {
        return s.replaceAll("[\r\n\t ]", "").replaceAll(",", ";").trim();
    }
}
