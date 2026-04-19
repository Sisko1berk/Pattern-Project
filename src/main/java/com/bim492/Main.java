package com.bim492;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryManager library = LibraryManager.getInstance();
        
        // SISTEM ACILIR ACILMAZ ESKI VERILERI YUKLE
        library.loadBooksFromFile();
        
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- LIBRARY MANAGEMENT SYSTEM ---");
            System.out.println("1. Create Book");
            System.out.println("2. Search Book");
            System.out.println("3. Borrow / Return Book");
            System.out.println("4. Modify Book");
            System.out.println("5. Undo Last Modification");
            System.out.println("0. Exit & Save");
            System.out.print("Your choice: ");
            
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Title: "); String title = scanner.nextLine();
                    System.out.print("Author: "); String author = scanner.nextLine();
                    System.out.print("Publication Year: "); int year = Integer.parseInt(scanner.nextLine());
                    System.out.print("ISBN: "); String isbn = scanner.nextLine();
                    
                    Book newBook = new Book(title, author, year, isbn);
                    newBook.addCategory("Fiction");
                    newBook.addTag("Classic");
                    
                    library.addBook(newBook);
                    break;

                case "2":
                    System.out.println("\nSearch Results:");
                    List<Book> searchResults = new ArrayList<>(library.getBooks());
                    
                    System.out.print("Sort Order (1: Ascending, 2: Descending): ");
                    String sortChoice = scanner.nextLine();
                    
                    SortStrategy strategy = sortChoice.equals("1") ? new AscendingSort() : new DescendingSort();
                    strategy.sort(searchResults);
                    
                    for (int i = 0; i < searchResults.size(); i++) {
                        System.out.println((i + 1) + ". " + searchResults.get(i).toString());
                    }
                    break;

                case "3":
                    System.out.print("Enter ISBN of the book: ");
                    String targetIsbn = scanner.nextLine();
                    Book targetBook = library.getBooks().stream().filter(b -> b.getIsbn().equals(targetIsbn)).findFirst().orElse(null);
                    
                    if(targetBook != null) {
                        System.out.print("1: Borrow | 2: Return -> ");
                        String action = scanner.nextLine();
                        if(action.equals("1")) targetBook.borrowBook();
                        else if(action.equals("2")) targetBook.returnBook();
                        else System.out.println("Invalid action!");
                    } else {
                        System.out.println("Book not found!");
                    }
                    break;

                case "4":
                    System.out.print("Enter ISBN of the book to modify: ");
                    String modIsbn = scanner.nextLine();
                    Book bookToMod = library.getBooks().stream().filter(b -> b.getIsbn().equals(modIsbn)).findFirst().orElse(null);
                    
                    if(bookToMod != null) {
                        System.out.println("Leave blank and press Enter to keep current values.");
                        
                        System.out.print("New Title (" + bookToMod.getTitle() + "): "); 
                        String nTitle = scanner.nextLine();
                        if(nTitle.trim().isEmpty()) nTitle = bookToMod.getTitle(); 
                        
                        System.out.print("New Author (" + bookToMod.getAuthor() + "): "); 
                        String nAuthor = scanner.nextLine();
                        if(nAuthor.trim().isEmpty()) nAuthor = bookToMod.getAuthor(); 
                        
                        Command modCommand = new ModifyBookCommand(bookToMod, nTitle, nAuthor);
                        library.executeCommand(modCommand);
                    } else {
                        System.out.println("Book not found!");
                    }
                    break;

                case "5":
                    library.undoLastCommand();
                    break;

                case "0":
                    running = false;
                    // CIKIS YAPMADAN ONCE TUM VERILERI DOSYAYA KAYDET
                    library.saveBooksToFile();
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
        scanner.close();
    }
}