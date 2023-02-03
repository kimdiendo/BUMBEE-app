package com.example.bumbee.activities.TestVocab;

import java.util.ArrayList;
import java.util.Arrays;

public class Vocab {
    private String vocab;
    private String meaning;

    public Vocab(String vocab, String meaning) {
        this.vocab = vocab;
        this.meaning = meaning;
    }

    public String getVocab() {
        return vocab;
    }

    public void setVocab(String vocab) {
        this.vocab = vocab;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
    @Override
    public String toString() {
        return vocab + "\t" + meaning + "\n";
    }
}
