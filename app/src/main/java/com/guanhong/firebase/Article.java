package com.guanhong.firebase;

public class Article {

    public String authorId;
    public String title;
    public String content;
    public String tag;
    public String author;
    public String creatTime;

    public Article(){}

    public Article(String authorId,
                   String title,
                   String content,
                   String tag,
                   String author,
                   String creatTime) {

        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.author = author;
        this.creatTime = creatTime;
    }
}
