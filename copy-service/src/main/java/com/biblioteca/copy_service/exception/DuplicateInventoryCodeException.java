package com.biblioteca.copy_service.exception;
public class DuplicateInventoryCodeException extends RuntimeException { public DuplicateInventoryCodeException(String c){ super("Ya existe el codigo de inventario: "+c);} }
