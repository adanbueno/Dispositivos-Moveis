package com.ufcquixada.navegacaotelas.model;

public class Book {
    private int _id;
    private String title;
    private String author;
    private int value;

    public Book(int _id, String title, String author, int value) {
        this._id = _id;
        this.title = title;
        this.author = author;
        this.value = value;
    }

    public int getId() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getValue() {
        return value;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "id: " + _id +
                ", Titulo: " + title +
                ", Autor: " + author +
                ", Valor: R$ " + value;
    }
}
