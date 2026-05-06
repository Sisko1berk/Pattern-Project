package com.bim492;

import java.util.ArrayList;
import java.util.List;

public class ModifyBookCommand implements Command {
    private Book targetBook;
    
  
    private String oldTitle, oldAuthor, oldIsbn, oldPublisher, oldDescription;
    private int oldYear;
    private List<String> oldCategories;
    private List<String> oldTags;

   
    private String newTitle, newAuthor, newIsbn, newPublisher, newDescription;
    private int newYear;
    private List<String> newCategories;
    private List<String> newTags;

    public ModifyBookCommand(Book targetBook, String newTitle, String newAuthor, int newYear, 
                             String newIsbn, String newPublisher, String newDescription, 
                             List<String> newCategories, List<String> newTags) {
        this.targetBook = targetBook;
        this.newTitle = newTitle;
        this.newAuthor = newAuthor;
        this.newYear = newYear;
        this.newIsbn = newIsbn;
        this.newPublisher = newPublisher;
        this.newDescription = newDescription;
        this.newCategories = new ArrayList<>(newCategories);
        this.newTags = new ArrayList<>(newTags);
    }

    @Override
    public void execute() {
      
        this.oldTitle = targetBook.getTitle();
        this.oldAuthor = targetBook.getAuthor();
        this.oldYear = targetBook.getPublicationYear();
        this.oldIsbn = targetBook.getIsbn();
        this.oldPublisher = targetBook.getPublisher();
        this.oldDescription = targetBook.getDescription();
        this.oldCategories = new ArrayList<>(targetBook.getCategories());
        this.oldTags = new ArrayList<>(targetBook.getTags());
        
       
        targetBook.setTitle(newTitle);
        targetBook.setAuthor(newAuthor);
        targetBook.setPublicationYear(newYear);
        targetBook.setIsbn(newIsbn);
        targetBook.setPublisher(newPublisher);
        targetBook.setDescription(newDescription);
        targetBook.setCategories(new ArrayList<>(newCategories));
        targetBook.setTags(new ArrayList<>(newTags));
        
        System.out.println("Book updated successfully!");
    }

    @Override
    public void undo() {
        
        targetBook.setTitle(oldTitle);
        targetBook.setAuthor(oldAuthor);
        targetBook.setPublicationYear(oldYear);
        targetBook.setIsbn(oldIsbn);
        targetBook.setPublisher(oldPublisher);
        targetBook.setDescription(oldDescription);
        targetBook.setCategories(new ArrayList<>(oldCategories));
        targetBook.setTags(new ArrayList<>(oldTags));
        
        System.out.println("Modification UNDONE! Book restored to exact previous state.");
    }
}