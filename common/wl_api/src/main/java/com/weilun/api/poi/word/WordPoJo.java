package com.weilun.api.poi.word;

import org.apache.logging.log4j.util.Strings;

public class WordPoJo {

    private String title;
    private String content;
    private String section;
    private String url;
    private String issue;
    private String date;
    private String author;
    private String keyword;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (this.content == null) {
            this.content = content;
        } else {
            this.content += content;
        }
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "WordPoJo{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", section='" + section + '\'' +
                ", url='" + url + '\'' +
                ", issue='" + issue + '\'' +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
