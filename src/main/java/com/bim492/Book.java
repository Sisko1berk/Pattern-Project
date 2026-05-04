package com.bim492;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable { 
    private static final long serialVersionUID = 1L; 
    
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    private String publisher;
    private String description; // YENİ EKLENDİ
    private List<String> categories;
    private List<String> tags;
    private boolean available;
    private int borrowCount;

    // CONSTRUCTOR GÜNCELLENDİ (description eklendi)
    public Book(String title, String author, int publicationYear, String isbn, String publisher, String description) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.publisher = publisher;
        this.description = description; // YENİ EKLENDİ
        this.categories = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.available = true;
        this.borrowCount = 0;
    }

    public void addCategory(String category) {
        if (this.categories.size() < 3) this.categories.add(category);
    }

    public void addTag(String tag) {
        if (this.tags.size() < 3) this.tags.add(tag);
    }

    public void borrowBook() {
        if (available) {
            available = false;
            borrowCount++;
            System.out.println("Book successfully borrowed.");
        } else {
            System.out.println("Error: This book is currently borrowed by someone else!");
        }
    }

    public void returnBook() {
        if (!available) { 
            available = true;
            System.out.println("Book returned successfully.");
        } else { 
            System.out.println("Error: This book is already in the library, you cannot return it!");
        }
    }

    // GET VE SET METOTLARI (Modify işlemi için hepsi eklendi)
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    @Override
    public String toString() {
        return String.format("Title: %s | Author: %s | Year: %d | Publisher: %s | ISBN: %s | Desc: %s | Categories: %s | Tags: %s | Available: %s | Borrow Count: %d", 
                title, author, publicationYear, publisher, isbn, description, categories.toString(), tags.toString(), (available ? "Yes" : "No"), borrowCount);
    }
}