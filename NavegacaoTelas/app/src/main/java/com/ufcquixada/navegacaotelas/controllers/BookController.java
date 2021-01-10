package com.ufcquixada.navegacaotelas.controllers;

import com.ufcquixada.navegacaotelas.data.DataBooks;
import com.ufcquixada.navegacaotelas.model.Book;

import java.util.ArrayList;

public class BookController {
    private DataBooks listBooks;

    public BookController() {
        listBooks = DataBooks.getInstance();
    }

    public ArrayList<Book> getListBooks() {
        return listBooks.getBooks();
    }

    public void addBook(String title, String author, Float value) {
        listBooks.addBook(new Book(title, author, value));
    }

    public void removeBook(int id) {
        listBooks.removeBook(id);
    }

    public void updateBook(int id, String title, String author, Float value) {
        listBooks.updateBook(id, title, author, value);
    }

    public int getIdByPosition(int position) {
        return listBooks.getIdByPosition(position);
    }

    public Book getBook(int id) {
        return listBooks.getBook(id);
    }
}
