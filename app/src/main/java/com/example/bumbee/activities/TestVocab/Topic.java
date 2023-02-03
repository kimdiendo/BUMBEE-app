package com.example.bumbee.activities.TestVocab;
import android.widget.BaseAdapter;

public class Topic {
    private String topicImage;
    private String topicName;

    public Topic() {
    }

    public Topic(String a, String b) {
        this.topicImage = a;
        this.topicName = b;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicImage() {
        return topicImage;
    }

    public void setTopicImage(String topicImage) {
        this.topicImage = topicImage;
    }

}