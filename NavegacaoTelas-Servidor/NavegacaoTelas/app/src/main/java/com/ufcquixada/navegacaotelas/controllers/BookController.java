package com.ufcquixada.navegacaotelas.controllers;

import com.ufcquixada.navegacaotelas.data.DataBooks;
import com.ufcquixada.navegacaotelas.model.Book;

import java.util.List;

public class BookController {
    //private DataBooks listBooks;
    private List<Book> listBooks;

    public BookController(List<Book> books) {
        this.listBooks = books;
    }

    public int getSizeListBook() {
        return listBooks.size();
    }

    public void addBook(Book book) {
        listBooks.add(book);
    }

    public void removeBook(int id) {
        for (Book book: listBooks) {
            if(book.getId() == id) {
                listBooks.remove(book);
                return;
            }
        }
    }

    public void updateBook(Book newBook) {
        for (Book book: listBooks) {
            if(book.getId() == newBook.getId()) {
                book.setAuthor(newBook.getAuthor());
                book.setTitle(newBook.getTitle());
                book.setValue(newBook.getValue());
            }
        }
    }

    public int getIdByPosition(int position) {
        return listBooks.get(position).getId();
    }

    public Book getBook(int id) {
        for (Book book: listBooks) {
            if(book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    /*
    public BookController() {
        listBooks = DataBooks.getInstance();
    }

    public ArrayList<Book> getListBooks() {
        return listBooks.getBooks();
    }

    public int getSizeListBook() {
        return listBooks.getSizeListBook();
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
     */
}
