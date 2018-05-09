package info.unclewang;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;


public class MagThread implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(DbUtilsDruid.class);
    private long id;
    private String filename;

    public MagThread() {
    }

    public MagThread(long id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    @Override
    public void run() {
        List<String> stringList = null;
        try {
            stringList = FileUtils.readLines(new File("/Users/unclewang/" + this.filename), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        insertArticle(stringList, this.id);

    }

    public static void insertArticle(List<String> stringList, long id) {

        List<Paper> paperList = new ArrayList<>();
        DbUtilsDruid dd = DbUtilsDruid.getInstance();
        DruidPooledConnection connection = dd.getCon();
        String queryAuSql = "Select id from author where name = ?";
        String queryTopicSql = "Select id from topic where name = ?";

        for (String s : stringList) {
            id++;
            JSONObject mag = JSON.parseObject(s);
            JSONArray authorsJson = mag.getJSONArray("authors");
            JSONArray topicJson = mag.getJSONArray("fos");
            JSONArray keywordJson = mag.getJSONArray("keywords");
            StringBuilder authors = new StringBuilder();
            authors.append("");
            StringBuilder authorid = new StringBuilder();
            authorid.append("");
            StringBuilder keywords = new StringBuilder();
            keywords.append("");
            for (Object j : authorsJson) {
                JSONObject object = JSON.parseObject(String.valueOf(j));
                String author = (String) object.get("name");

                try {
                    Object[] auid = (Object[]) dd.query(queryAuSql, new ArrayHandler(), author);
                    authors.append(author + ";");
                    authorid.append(auid[0] + ";");
                } catch (ArrayIndexOutOfBoundsException e) {
                } catch (NullPointerException e) {
                }
            }
            String catagory = String.valueOf(topicJson.get(0));
            String topicid;
            try {
                Object[] topicIds = (Object[]) dd.query(queryTopicSql, new ArrayHandler(), catagory);
                topicid = String.valueOf(topicIds[0]);
            } catch (ArrayIndexOutOfBoundsException e) {
                topicid = "";
            }

            for (Object o : keywordJson) {
                String keyword = (String) o;
                keywords.append(keyword + ";;");
            }
            Paper paper = new Paper();
            System.out.println(id);
            paper.setId(id);
            paper.setProceeding(0);
            paper.setYear(Integer.valueOf(mag.getString("year")));
            if (mag.getString("issue") == null) {
                paper.setDate(mag.getString("year"));
            } else {
                paper.setDate(mag.getString("year") + "-" + mag.getString("issue"));
            }
            paper.setTitle(mag.getString("title").toLowerCase());
            try {
                paper.setKeywords(String.valueOf(keywords).substring(0, keywords.length() - 2)
                        .replaceAll(mag.getString("title").toLowerCase(), "")
                        .replaceAll(String.valueOf(authors).replaceAll(";", " "), ""));
            } catch (PatternSyntaxException e) {
                paper.setKeywords("");
            }
            if (mag.getString("abstract") != null) {
                paper.setSummary(mag.getString("abstract"));
            } else {
                paper.setSummary("");
            }
            paper.setAuid(String.valueOf(authorid));
            paper.setAutext(String.valueOf(authors));
            paper.setInstid("");
            paper.setInstext("");
            paper.setSemid((long) 0);
            paper.setCategory(catagory);
            paper.setTopic(topicid);
            String insertSql = "insert into article_cnki values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


//            if (id % 100 == 0) {
//                logger.info(Thread.currentThread().getName() + " " + String.valueOf(id));
//            }
            int paramLength = 50;
            if (paperList.size() == paramLength) {
                //Object[][] result=transfer(paperList);
                logger.info(Thread.currentThread().getName() + " " + String.valueOf(id));
                int i = dd.crud(insertSql, transfer(paperList));
                paperList.clear();
            } else {
                paperList.add(paper);
                continue;
            }

        }
    }

    public static Object[][] transfer(List<Paper> papers) {
        Object[][] result = new Object[papers.size()][14];
        for (int id = 0; id < papers.size(); id++) {
            Paper paper = papers.get(id);
            result[id][0] = paper.getId();
            result[id][1] = paper.getProceeding();
            result[id][2] = paper.getYear();
            result[id][3] = paper.getDate();
            result[id][4] = paper.getTitle();
            result[id][5] = paper.getKeywords();
            result[id][6] = paper.getSummary();
            result[id][7] = paper.getAuid();
            result[id][8] = paper.getAutext();
            result[id][9] = paper.getInstid();
            result[id][10] = paper.getInstext();
            result[id][11] = paper.getSemid();
            result[id][12] = paper.getCategory();
            result[id][13] = paper.getTopic();

        }
        return result;
    }

    public static Object[] transfer(Paper paper) {
        Object[] a = new Object[]{paper.getId(), paper.getProceeding(),
                paper.getYear(), paper.getDate(), paper.getTitle(), paper.getKeywords(),
                paper.getSummary(), paper.getAuid(), paper.getAutext(), paper.getInstid(),
                paper.getInstext(), paper.getSemid(), paper.getCategory(), paper.getTopic()};
        return a;
    }

    public static void main(String[] args) {
        MagThread mt1 = new MagThread(100064807, "expert1.txt");
        MagThread mt2 = new MagThread(100124832, "expert2.txt");
        MagThread mt3 = new MagThread(100184838, "expert3.txt");
        MagThread mt4 = new MagThread(100244809, "expert4.txt");
        MagThread mt5 = new MagThread(100304832, "expert5.txt");

        new Thread(mt1).start();
        new Thread(mt2).start();
        new Thread(mt3).start();
        new Thread(mt4).start();
        new Thread(mt5).start();


    }
}

