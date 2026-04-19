package com.bim492;

public class ModifyBookCommand implements Command {
    private Book targetBook;
    private String oldTitle, oldAuthor;
    private String newTitle, newAuthor;

    public ModifyBookCommand(Book targetBook, String newTitle, String newAuthor) {
        this.targetBook = targetBook;
        this.newTitle = newTitle;
        this.newAuthor = newAuthor;
    }

    @Override
    public void execute() {
        this.oldTitle = targetBook.getTitle();
        this.oldAuthor = targetBook.getAuthor();
        
        targetBook.setTitle(newTitle);
        targetBook.setAuthor(newAuthor);
        System.out.println("Book updated successfully!");
    }

    @Override
    public void undo() {
        targetBook.setTitle(oldTitle);
        targetBook.setAuthor(oldAuthor);
        System.out.println("Modification UNDONE! Book restored to previous state.");
    }
}