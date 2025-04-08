package com.sananatural.backend.dto;

public class DetallePedidoDTO {

    private Long id;
    private Long productoId;
    private String codigoProducto;
    private String nombreProducto;
    private int cantidad;

    public DetallePedidoDTO() {}

    public DetallePedidoDTO(Long id, Long productoId,String codigoProducto, String nombreProducto, int cantidad) {
        this.id = id;
        this.productoId = productoId;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }
    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
