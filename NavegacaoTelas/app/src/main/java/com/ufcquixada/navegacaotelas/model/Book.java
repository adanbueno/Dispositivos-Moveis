package com.ufcquixada.navegacaotelas.model;

public class Book {
    private static int id_dynamic = 0;
    private int id;
    private String title;
    private String author;
    private Float value;

    public Book(String title, String author, Float value) {
        this.id = ++id_dynamic;
        this.title = title;
        this.author = author;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Float getValue() {
        return value;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", Titulo: " + title +
                ", Autor: " + author +
                ", Valor: R$ " + value;
    }
}
