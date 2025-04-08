package com.sananatural.backend.dto;

public class CrearDetalleDTO {
    private Long productoId;
    private int cantidad;

    public CrearDetalleDTO() {}

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
