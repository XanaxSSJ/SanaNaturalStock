package com.sananatural.backend.dto;

public class ProductoDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private String proveedor;

    public ProductoDTO(Long id, String nombre, String categoria, String proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.proveedor = proveedor;
    }

// Getters y setters

    public Long getId() {
        return id;
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
