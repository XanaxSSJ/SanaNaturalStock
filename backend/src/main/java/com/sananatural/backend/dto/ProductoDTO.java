package com.sananatural.backend.dto;

public class ProductoDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private String categoria;
    private String proveedor;

    public ProductoDTO(Long id, String codigo, String nombre, String categoria, String proveedor) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.proveedor = proveedor;
    }

// Getters y setters

    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getProveedor() {
        return proveedor;
    }

}
