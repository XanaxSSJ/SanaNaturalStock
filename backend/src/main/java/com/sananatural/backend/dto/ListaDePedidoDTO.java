package com.sananatural.backend.dto;

public class ListaDePedidoDTO {
    private Long id;
    private String tienda;
    private String fechaCreacion;

    public ListaDePedidoDTO(Long id, String tienda, String fechaCreacion) {
        this.id = id;
        this.tienda = tienda;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public String getTienda() {
        return tienda;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }
}
