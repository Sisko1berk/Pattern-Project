package com.bim492;

import java.util.Comparator;
import java.util.List;

public class AscendingSort implements SortStrategy {
    @Override
    public void sort(List<Book> books) {
        books.sort(Comparator.comparing(Book::getTitle));
    }
}