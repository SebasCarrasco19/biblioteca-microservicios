package com.biblioteca.copy_service.exception;
public class CopyNotFoundException extends RuntimeException { public CopyNotFoundException(Long id){ super("No existe la copia con id: "+id);} }
