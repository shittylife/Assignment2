package com.example.assignment2;

public class Task {
    private long id = -1;
    private int year = -1;
    private int month = -1;
    private int day = -1;
    private String title = "", text = "";

    public Task(long id, int day, int month, int year) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Task(long id, int day, int month, int year, String title, String text) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.title = title;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
