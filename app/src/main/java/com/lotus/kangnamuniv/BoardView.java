package com.lotus.kangnamuniv;

public class BoardView {
    private String title;
    private String writer;

    public String getTitle(){
        return title;
    }
    public String getWriter(){
        return writer;
    }
    public BoardView(String title, String writer){
        this.title = title;
        this.writer = writer;
    }

}
