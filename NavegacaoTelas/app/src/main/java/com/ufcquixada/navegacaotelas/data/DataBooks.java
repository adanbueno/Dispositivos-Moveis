package com.ufcquixada.navegacaotelas.data;

import com.ufcquixada.navegacaotelas.model.Book;

import java.util.ArrayList;
import java.util.Iterator;

public class DataBooks {
    private static DataBooks INSTANCE;
    private static ArrayList<Book> books;

    private DataBooks() {
        books = new ArrayList<Book>();
    }

    public static DataBooks getInstance() {
        if(INSTANCE == null)  INSTANCE = new DataBooks();
        return INSTANCE;
    }

    public static ArrayList<Book> getBooks() {
        return books;
    }

    public int getIdByPosition(int position) {
        return books.get(position).getId();
    }

    public void addBook(Book book){
        books.add(book);
    }

    public void removeBook(int id) {
        for (Book book: books) {
            if(book.getId() == id) {
                books.remove(book);
                return;
            }
        }
    }

    public Book getBook(int id) {
        for (Book book: books) {
            if(book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public void updateBook(int id, String title, String author, Float value) {
        for (Book book: books) {
            if(book.getId() == id) {
                book.setTitle(title);
                book.setAuthor(author);
                book.setValue(value);
            }
        }
    }

}
