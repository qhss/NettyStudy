package com.atguigu.dto;

import java.util.List;

public class FirstLetterList {
    private String firstWord;

    private List<FirstLetter> firstLetterList;

    public List<FirstLetter> getFirstLetterList() {
        return firstLetterList;
    }

    public void setFirstLetterList(List<FirstLetter> firstLetterList) {
        this.firstLetterList = firstLetterList;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public void setFirstWord(String firstWord) {
        this.firstWord = firstWord;
    }
}
