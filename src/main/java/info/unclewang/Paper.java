package info.unclewang;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ChengQikai
 * @description
 * @date 0:27 2018/5/7
 */
public class Paper implements Serializable {

    private Long id;


    private long proceeding;


    private String  authorList;

    private Integer year;

    private String date;

    private String title;

    private String keywords;

    private String summary;

    private String auid;

    private String autext;

    private String instid;

    private String instext;

    private Long semid;

    private String category;

    private String topic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuid() {
        return auid;
    }

    public void setAuid(String auid) {
        this.auid = auid;
    }

    public String getAutext() {
        return autext;
    }

    public void setAutext(String autext) {
        this.autext = autext;
    }

    public String getInstid() {
        return instid;
    }

    public void setInstid(String instid) {
        this.instid = instid;
    }

    public String getInstext() {
        return instext;
    }

    public void setInstext(String instext) {
        this.instext = instext;
    }

    public Long getSemid() {
        return semid;
    }

    public void setSemid(Long semid) {
        this.semid = semid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String toStringWithCiteFormat() {
        return this.autext + ". " + this.title + "." + this.proceeding + "," + this.year;
    }

    public long getProceeding() {
        return proceeding;
    }

    public void setProceeding(long proceeding) {
        this.proceeding = proceeding;
    }

    public String getAuthorList() {
        return authorList;
    }

    public void setAuthorList(String authorList) {
        this.authorList = authorList;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
