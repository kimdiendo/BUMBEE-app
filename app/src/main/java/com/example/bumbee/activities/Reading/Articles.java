package com.example.bumbee.activities.Reading;

public class Articles {
    private String title;
    private String description;
    private String content;
    private String urlToImage;
    private String url;;

    public Articles(String title, String description, String url, String content, String urlToImage)
    {
        this.title = title;
        this.description = description;
        this.url = url;
        this.content = content;
        this.urlToImage = urlToImage;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrltoimage() {
        return urlToImage;
    }

    public void setUrltoimage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}
