package com.sananatural.backend.dto;

import java.util.List;

public class CrearListaDePedidoDTO {
    private Long tiendaId;
    private List<CrearDetalleDTO> detalles;

    public CrearListaDePedidoDTO() {}

    public Long getTiendaId() {
        return tiendaId;
    }

    public void setTiendaId(Long tiendaId) {
        this.tiendaId = tiendaId;
    }

    public List<CrearDetalleDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CrearDetalleDTO> detalles) {
        this.detalles = detalles;
    }
}
