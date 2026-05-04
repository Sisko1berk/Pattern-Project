package com.bim492;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibraryManager library = LibraryManager.getInstance();
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
                    System.out.print("Publisher: "); String publisher = scanner.nextLine();
                    System.out.print("Description: "); String description = scanner.nextLine(); 
                    
                    Book newBook = new Book(title, author, year, isbn, publisher, description);
                    
                    System.out.println("\n-- Add Categories (Up to 3) --");
                    for (int i = 1; i <= 3; i++) {
                        System.out.print("Enter Category " + i + " (Leave blank to skip): ");
                        String cat = scanner.nextLine();
                        if (cat.trim().isEmpty()) break;
                        newBook.addCategory(cat);
                    }

                    System.out.println("\n-- Add Tags (Up to 3) --");
                    for (int i = 1; i <= 3; i++) {
                        System.out.print("Enter Tag " + i + " (Leave blank to skip): ");
                        String tag = scanner.nextLine();
                        if (tag.trim().isEmpty()) break;
                        newBook.addTag(tag);
                    }
                    
                    System.out.print("\nSave Book to the system? (y/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("y")) {
                        library.addBook(newBook);
                    } else {
                        System.out.println("Book creation cancelled.");
                    }
                    break;

                case "2":
                    System.out.println("\n--- Search Book ---");
                    System.out.print("Enter search keyword (Title, Author, Tag, Category, or ISBN) [Leave blank to list all]: ");
                    String keyword = scanner.nextLine().toLowerCase().trim();
                    
                    List<Book> searchResults = new ArrayList<>();
                    
                    // Kütüphanedeki kitapları filtrele
                    for (Book b : library.getBooks()) {
                        boolean match = false;
                        if (b.getTitle().toLowerCase().contains(keyword) || 
                            b.getAuthor().toLowerCase().contains(keyword) || 
                            b.getIsbn().toLowerCase().contains(keyword)) {
                            match = true;
                        } else {
                            for (String cat : b.getCategories()) {
                                if (cat.toLowerCase().contains(keyword)) { match = true; break; }
                            }
                            if (!match) {
                                for (String t : b.getTags()) {
                                    if (t.toLowerCase().contains(keyword)) { match = true; break; }
                                }
                            }
                        }
                        
                        if (match || keyword.isEmpty()) {
                            searchResults.add(b);
                        }
                    }
                    
                    // Sonuçlar ve Sıralama
                    if (searchResults.isEmpty()) {
                        System.out.println("No books found matching the keyword: '" + keyword + "'");
                    } else {
                        System.out.print("Sort Order by Title (1: Ascending, 2: Descending): ");
                        String sortChoice = scanner.nextLine();
                        
                        SortStrategy strategy = sortChoice.equals("1") ? new AscendingSort() : new DescendingSort();
                        strategy.sort(searchResults);
                        
                        System.out.println("\nSearch Results:");
                        // Detayları görmek için önce sadece başlık ve yazarı listeliyoruz
                        for (int i = 0; i < searchResults.size(); i++) {
                            System.out.println((i + 1) + ". " + searchResults.get(i).getTitle() + " (by " + searchResults.get(i).getAuthor() + ")");
                        }
                        
                        // Örnek Kullanım Adım 5: Listeden kitap seçip detay görme
                        System.out.print("\nSelect a book number to view details (or 0 to return to main menu): ");
                        int selectedNum = 0;
                        try {
                            selectedNum = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Returning to main menu.");
                            break;
                        }
                        
                        if (selectedNum > 0 && selectedNum <= searchResults.size()) {
                            Book selectedBook = searchResults.get(selectedNum - 1);
                            System.out.println("\n--- Book Details ---");
                            // Burada toString() çalışır ve tüm detaylar (yıl, ISBN, etiket vs.) ekrana basılır
                            System.out.println(selectedBook.toString());
                            
                            // Örnek Kullanım Adım 6: Detayını gördüğün kitabı hemen oradan ödünç alma
                            System.out.print("\nDo you want to Borrow this book? (y/n): ");
                            if (scanner.nextLine().equalsIgnoreCase("y")) {
                                selectedBook.borrowBook();
                            }
                        }
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

                        System.out.print("New Publication Year (" + bookToMod.getPublicationYear() + "): "); 
                        String nYearStr = scanner.nextLine();
                        int nYear = nYearStr.trim().isEmpty() ? bookToMod.getPublicationYear() : Integer.parseInt(nYearStr);

                        System.out.print("New ISBN (" + bookToMod.getIsbn() + "): "); 
                        String nIsbn = scanner.nextLine();
                        if(nIsbn.trim().isEmpty()) nIsbn = bookToMod.getIsbn();

                        System.out.print("New Publisher (" + bookToMod.getPublisher() + "): "); 
                        String nPublisher = scanner.nextLine();
                        if(nPublisher.trim().isEmpty()) nPublisher = bookToMod.getPublisher();

                        System.out.print("New Description (" + bookToMod.getDescription() + "): "); 
                        String nDescription = scanner.nextLine();
                        if(nDescription.trim().isEmpty()) nDescription = bookToMod.getDescription();
                        
                        // KATEGORİ VE ETİKETLER İÇİN YENİ "AKILLI" DÜZENLEME MANTIĞI
                        List<String> nCategories = new ArrayList<>(bookToMod.getCategories());
                        List<String> nTags = new ArrayList<>(bookToMod.getTags());
                        
                        System.out.print("Do you want to modify Categories & Tags? (y/n): ");
                        if (scanner.nextLine().equalsIgnoreCase("y")) {
                            
                            // Kategorileri Güncelle
                            System.out.println("\n-- Update Categories (Leave blank to keep current) --");
                            List<String> tempCats = new ArrayList<>();
                            for (int i = 0; i < 3; i++) {
                                String oldCat = (i < nCategories.size()) ? nCategories.get(i) : "";
                                System.out.print("Category " + (i + 1) + (oldCat.isEmpty() ? "" : " [" + oldCat + "]") + ": ");
                                String input = scanner.nextLine();
                                
                                if (!input.trim().isEmpty()) {
                                    tempCats.add(input); // Yeni değer girildiyse onu al
                                } else if (!oldCat.isEmpty()) {
                                    tempCats.add(oldCat); // Boş bırakıldıysa ve eskisi varsa eskiyi koru
                                }
                            }
                            nCategories = tempCats;
                            
                            // Etiketleri Güncelle
                            System.out.println("\n-- Update Tags (Leave blank to keep current) --");
                            List<String> tempTags = new ArrayList<>();
                            for (int i = 0; i < 3; i++) {
                                String oldTag = (i < nTags.size()) ? nTags.get(i) : "";
                                System.out.print("Tag " + (i + 1) + (oldTag.isEmpty() ? "" : " [" + oldTag + "]") + ": ");
                                String input = scanner.nextLine();
                                
                                if (!input.trim().isEmpty()) {
                                    tempTags.add(input);
                                } else if (!oldTag.isEmpty()) {
                                    tempTags.add(oldTag);
                                }
                            }
                            nTags = tempTags;
                        }

                        Command modCommand = new ModifyBookCommand(bookToMod, nTitle, nAuthor, nYear, nIsbn, nPublisher, nDescription, nCategories, nTags);
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