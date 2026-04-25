package com.bim492;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// implements Serializable kısmı çok önemli, dosyaya yazmayı sağlar
public class Book implements Serializable { 
    // Java'nın dosyaları tanımak için kullandığı seri numarası
    private static final long serialVersionUID = 1L; 
    
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    private List<String> categories;
    private List<String> tags;
    private boolean available;
    private int borrowCount;

    public Book(String title, String author, int publicationYear, String isbn) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
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
        if (!available) { // Kitap gerçekten ödünç alınmışsa (müsait değilse)
            available = true;
            System.out.println("Book returned successfully.");
        } else { // Kitap zaten kütüphanedeyse ve ödünç alınmamışsa
            System.out.println("Error: This book is already in the library, you cannot return it!");
        }
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public int getPublicationYear() { return publicationYear; }

    @Override
    public String toString() {
        return String.format("Title: %s | Author: %s | Year: %d | ISBN: %s | Available: %s | Borrow Count: %d", 
                title, author, publicationYear, isbn, (available ? "Yes" : "No"), borrowCount);
    }
}