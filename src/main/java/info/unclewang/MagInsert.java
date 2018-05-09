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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import static info.unclewang.DataClean.transfer;

public class MagInsert {
    private static Logger logger = LoggerFactory.getLogger(DbUtilsDruid.class);

    public static void main(String[] args) {
        List<String> stringList = null;
        try {
            stringList = FileUtils.readLines(new File("/Users/unclewang/json_expert.txt"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        insertAuid(stringList);
//        insertTopic(stringList);
        insertArticle(stringList);
    }

    public static void insertArticle(List<String> stringList) {
        long id = 100000000;
        Paper paper = new Paper();
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
            StringBuilder authorid = new StringBuilder();
            StringBuilder keywords = new StringBuilder();
            for (Object j : authorsJson) {
                JSONObject object = JSON.parseObject(String.valueOf(j));
                String author = (String) object.get("name");
                Object[] auid = (Object[]) dd.query(queryAuSql, new ArrayHandler(), author);
                authors.append(author + ";");
                authorid.append(auid[0] + ";");
            }
            String catagory = String.valueOf(topicJson.get(0));
            Object[] topicIds = (Object[]) dd.query(queryTopicSql, new ArrayHandler(), catagory);
            String topicid = String.valueOf(topicIds[0]);
            for (Object o : keywordJson) {
                String keyword = (String) o;
                keywords.append(keyword + ";;");
            }

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
            dd.crud(insertSql, transfer(paper));

            if (id % 1000 == 0) {
                logger.info(String.valueOf(id));
            }
        }
    }

    public static void insertTopic(List<String> stringList) {
        HashMap<String, Integer> stringLongHashMap = new HashMap<>();
        int id = 10000;
        for (String s : stringList) {
            JSONObject json = JSON.parseObject(s);
            JSONArray jsonArray = json.getJSONArray("fos");
            for (Object j : jsonArray) {
                stringLongHashMap.put(String.valueOf(j), id++);
            }
        }
        System.out.println("正在插入");
        DbUtilsDruid dd = DbUtilsDruid.getInstance();
        DruidPooledConnection connection = dd.getCon();
        String insertSql = "insert into topic values(?,?,?)";
        for (Map.Entry<String, Integer> s : stringLongHashMap.entrySet()) {
            dd.crud(connection, insertSql, s.getValue(), "-1", s.getKey());
        }
    }

    public static void insertAuid(List<String> stringList) {
        HashMap<String, Long> stringLongHashMap = new HashMap<>();
        long id = 100000000;
        for (String s : stringList) {
            JSONObject json = JSON.parseObject(s);
            JSONArray jsonArray = json.getJSONArray("authors");
            for (Object j : jsonArray) {
                JSONObject object = JSON.parseObject(String.valueOf(j));
                stringLongHashMap.put(object.getString("name"), id++);
            }
            //System.out.println(json.get("keywords"));
        }
        System.out.println("正在插入");
        DbUtilsDruid dd = DbUtilsDruid.getInstance();
        DruidPooledConnection connection = dd.getCon();
        String insertSql = "insert into author values(?,?,?)";
        for (Map.Entry<String, Long> s : stringLongHashMap.entrySet()) {
            dd.crud(connection, insertSql, s.getValue(), s.getKey(), 0);
        }
    }
}
