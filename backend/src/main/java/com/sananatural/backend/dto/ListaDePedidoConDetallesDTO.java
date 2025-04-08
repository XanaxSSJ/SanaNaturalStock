package com.sananatural.backend.dto;

import java.util.List;

public class ListaDePedidoConDetallesDTO {
    private Long id;
    private String tienda;
    private String fechaCreacion;
    private List<DetallePedidoDTO> detalles;

    public ListaDePedidoConDetallesDTO(Long id, String tienda, String fechaCreacion, List<DetallePedidoDTO> detalles) {
        this.id = id;
        this.tienda = tienda;
        this.fechaCreacion = fechaCreacion;
        this.detalles = detalles;
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

    public List<DetallePedidoDTO> getDetalles() {
        return detalles;
    }
}
