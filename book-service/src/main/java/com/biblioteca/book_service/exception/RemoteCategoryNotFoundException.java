package com.biblioteca.book_service.exception;

public class RemoteCategoryNotFoundException extends RuntimeException {
    public RemoteCategoryNotFoundException(Long categoryId) {
        super("Category not found in category-service. categoryId=" + categoryId);
    }
}
