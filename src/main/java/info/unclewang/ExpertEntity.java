package info.unclewang;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "expert", schema = "cnki_cheng")
public class ExpertEntity {
    private long tid;
    private String titleCn;
    private String authorCn;
    private String organ;
    private String kwCn;
    private String kwEn;
    private String absCn;
    private String titleEn;
    private String authorEn;
    private String refs;
    private String foundation;
    private String literNum;
    private String journalCn;
    private String journalSpell;
    private String journalEn;
    private String year;
    private String issue;
    private String cn;
    private String issn;
    private String fileName;
    private String page;
    private String ccn;
    private String titleCnEn;
    private String kwCl;
    private String pubDate;
    private Integer downloadNum;
    private Integer citedNum;
    private String kwCu;
    private String absEn;
    private Integer journalCat;

    @Id
    @Column(name = "tid")
    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    @Basic
    @Column(name = "titleCn")
    public String getTitleCn() {
        return titleCn;
    }

    public void setTitleCn(String titleCn) {
        this.titleCn = titleCn;
    }

    @Basic
    @Column(name = "authorCn")
    public String getAuthorCn() {
        return authorCn;
    }

    public void setAuthorCn(String authorCn) {
        this.authorCn = authorCn;
    }

    @Basic
    @Column(name = "organ")
    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    @Basic
    @Column(name = "kwCn")
    public String getKwCn() {
        return kwCn;
    }

    public void setKwCn(String kwCn) {
        this.kwCn = kwCn;
    }

    @Basic
    @Column(name = "kwEn")
    public String getKwEn() {
        return kwEn;
    }

    public void setKwEn(String kwEn) {
        this.kwEn = kwEn;
    }

    @Basic
    @Column(name = "absCn")
    public String getAbsCn() {
        return absCn;
    }

    public void setAbsCn(String absCn) {
        this.absCn = absCn;
    }

    @Basic
    @Column(name = "titleEn")
    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    @Basic
    @Column(name = "authorEn")
    public String getAuthorEn() {
        return authorEn;
    }

    public void setAuthorEn(String authorEn) {
        this.authorEn = authorEn;
    }

    @Basic
    @Column(name = "refs")
    public String getRefs() {
        return refs;
    }

    public void setRefs(String refs) {
        this.refs = refs;
    }

    @Basic
    @Column(name = "foundation")
    public String getFoundation() {
        return foundation;
    }

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }

    @Basic
    @Column(name = "literNum")
    public String getLiterNum() {
        return literNum;
    }

    public void setLiterNum(String literNum) {
        this.literNum = literNum;
    }

    @Basic
    @Column(name = "journalCn")
    public String getJournalCn() {
        return journalCn;
    }

    public void setJournalCn(String journalCn) {
        this.journalCn = journalCn;
    }

    @Basic
    @Column(name = "journalSpell")
    public String getJournalSpell() {
        return journalSpell;
    }

    public void setJournalSpell(String journalSpell) {
        this.journalSpell = journalSpell;
    }

    @Basic
    @Column(name = "journalEn")
    public String getJournalEn() {
        return journalEn;
    }

    public void setJournalEn(String journalEn) {
        this.journalEn = journalEn;
    }

    @Basic
    @Column(name = "year")
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Basic
    @Column(name = "issue")
    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    @Basic
    @Column(name = "cn")
    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    @Basic
    @Column(name = "issn")
    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    @Basic
    @Column(name = "fileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "page")
    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Basic
    @Column(name = "ccn")
    public String getCcn() {
        return ccn;
    }

    public void setCcn(String ccn) {
        this.ccn = ccn;
    }

    @Basic
    @Column(name = "titleCnEn")
    public String getTitleCnEn() {
        return titleCnEn;
    }

    public void setTitleCnEn(String titleCnEn) {
        this.titleCnEn = titleCnEn;
    }

    @Basic
    @Column(name = "kwCl")
    public String getKwCl() {
        return kwCl;
    }

    public void setKwCl(String kwCl) {
        this.kwCl = kwCl;
    }

    @Basic
    @Column(name = "pubDate")
    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Basic
    @Column(name = "downloadNum")
    public Integer getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(Integer downloadNum) {
        this.downloadNum = downloadNum;
    }

    @Basic
    @Column(name = "citedNum")
    public Integer getCitedNum() {
        return citedNum;
    }

    public void setCitedNum(Integer citedNum) {
        this.citedNum = citedNum;
    }

    @Basic
    @Column(name = "kwCu")
    public String getKwCu() {
        return kwCu;
    }

    public void setKwCu(String kwCu) {
        this.kwCu = kwCu;
    }

    @Basic
    @Column(name = "absEn")
    public String getAbsEn() {
        return absEn;
    }

    public void setAbsEn(String absEn) {
        this.absEn = absEn;
    }

    @Basic
    @Column(name = "journal_cat")
    public Integer getJournalCat() {
        return journalCat;
    }

    public void setJournalCat(Integer journalCat) {
        this.journalCat = journalCat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExpertEntity that = (ExpertEntity) o;
        return tid == that.tid &&
                Objects.equals(titleCn, that.titleCn) &&
                Objects.equals(authorCn, that.authorCn) &&
                Objects.equals(organ, that.organ) &&
                Objects.equals(kwCn, that.kwCn) &&
                Objects.equals(kwEn, that.kwEn) &&
                Objects.equals(absCn, that.absCn) &&
                Objects.equals(titleEn, that.titleEn) &&
                Objects.equals(authorEn, that.authorEn) &&
                Objects.equals(refs, that.refs) &&
                Objects.equals(foundation, that.foundation) &&
                Objects.equals(literNum, that.literNum) &&
                Objects.equals(journalCn, that.journalCn) &&
                Objects.equals(journalSpell, that.journalSpell) &&
                Objects.equals(journalEn, that.journalEn) &&
                Objects.equals(year, that.year) &&
                Objects.equals(issue, that.issue) &&
                Objects.equals(cn, that.cn) &&
                Objects.equals(issn, that.issn) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(page, that.page) &&
                Objects.equals(ccn, that.ccn) &&
                Objects.equals(titleCnEn, that.titleCnEn) &&
                Objects.equals(kwCl, that.kwCl) &&
                Objects.equals(pubDate, that.pubDate) &&
                Objects.equals(downloadNum, that.downloadNum) &&
                Objects.equals(citedNum, that.citedNum) &&
                Objects.equals(kwCu, that.kwCu) &&
                Objects.equals(absEn, that.absEn) &&
                Objects.equals(journalCat, that.journalCat);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tid, titleCn, authorCn, organ, kwCn, kwEn, absCn, titleEn, authorEn, refs, foundation, literNum, journalCn, journalSpell, journalEn, year, issue, cn, issn, fileName, page, ccn, titleCnEn, kwCl, pubDate, downloadNum, citedNum, kwCu, absEn, journalCat);
    }
}
