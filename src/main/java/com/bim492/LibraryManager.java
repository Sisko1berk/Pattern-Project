package com.bim492;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryManager {
    private static LibraryManager instance;
    private List<Book> books;
    private Command lastCommand; 

    private LibraryManager() {
        books = new ArrayList<>();
    }

    public static LibraryManager getInstance() {
        if (instance == null) {
            instance = new LibraryManager();
        }
        return instance;
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added to the system: " + book.getTitle());
    }

    public List<Book> getBooks() {
        return books;
    }

    public void executeCommand(Command command) {
        command.execute();
        lastCommand = command; 
    }

    public void undoLastCommand() {
        if (lastCommand != null) {
            lastCommand.undo();
            lastCommand = null; 
        } else {
            System.out.println("No command to undo!");
        }
    }

    

    public void saveBooksToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("library_data.dat"))) {
            oos.writeObject(books);
            System.out.println("All library data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadBooksFromFile() {
        File file = new File("library_data.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                books = (List<Book>) ois.readObject();
                System.out.println("Previous library data loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading data: " + e.getMessage());
            }
        } else {
            System.out.println("No previous data found. Starting fresh.");
        }
    }
}