package com.sananatural.backend.controller;

import com.sananatural.backend.dto.CrearListaDePedidoDTO;
import com.sananatural.backend.dto.ListaDePedidoConDetallesDTO;
import com.sananatural.backend.dto.ListaDePedidoDTO;
import com.sananatural.backend.model.*;
import com.sananatural.backend.repository.*;
import com.sananatural.backend.service.ExcelExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sananatural.backend.dto.DetallePedidoDTO;
import java.io.ByteArrayInputStream;
import java.util.stream.Collectors;


import java.util.List;

@RestController
@RequestMapping("/api/listas")
@CrossOrigin(origins = "*")
public class ListaDePedidoController {

    @Autowired
    private ListaDePedidoRepository listaRepo;

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private TiendaRepository tiendaRepo;

    @Autowired
    private ExcelExportService excelExportService;

    @GetMapping
    public List<ListaDePedidoDTO> obtenerListas() {
        return listaRepo.findAll().stream()
                .map(lista -> new ListaDePedidoDTO(
                        lista.getId(),
                        lista.getTienda() != null ? lista.getTienda().getNombre() : "Sin tienda",
                        lista.getFechaCreacion().toString()
                ))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> crearLista(@RequestBody CrearListaDePedidoDTO dto) {
        Tienda tienda = tiendaRepo.findById(dto.getTiendaId())
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada"));

        ListaDePedido nuevaLista = new ListaDePedido();
        nuevaLista.setTienda(tienda);

        List<DetallePedido> detalles = dto.getDetalles().stream().map(detalleDTO -> {
            Producto producto = productoRepo.findById(detalleDTO.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detalleDTO.getProductoId()));
            return new DetallePedido(producto, detalleDTO.getCantidad());
        }).toList();

        nuevaLista.setDetalles(detalles);
        listaRepo.save(nuevaLista);

        return ResponseEntity.ok("Lista creada correctamente con ID " + nuevaLista.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaDePedidoConDetallesDTO> obtenerListaConDetalles(@PathVariable Long id) {
        ListaDePedido lista = listaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista no encontrada"));

        List<DetallePedidoDTO> detallesDTO = lista.getDetalles().stream()
                .map(detalle -> new DetallePedidoDTO(
                        detalle.getId(),
                        detalle.getProducto().getId(),
                        detalle.getProducto().getNombre(),
                        detalle.getCantidad()
                ))
                .collect(Collectors.toList());

        ListaDePedidoConDetallesDTO dto = new ListaDePedidoConDetallesDTO(
                lista.getId(),
                lista.getTienda() != null ? lista.getTienda().getNombre() : "Sin tienda",
                lista.getFechaCreacion().toString(),
                detallesDTO
        );

        return ResponseEntity.ok(dto);
    }
    @GetMapping("/{id}/exportar")
    public ResponseEntity<byte[]> exportarLista(@PathVariable Long id) {
        ListaDePedido lista = listaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista no encontrada"));
        List<DetallePedidoDTO> detallesDTO = lista.getDetalles().stream().map(detalle ->
                new DetallePedidoDTO(
                        detalle.getId(),
                        detalle.getProducto().getId(),
                        detalle.getProducto().getNombre(),
                        detalle.getCantidad()
                )).toList();

        ByteArrayInputStream excelStream = excelExportService.exportarDetallesAExcel(detallesDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=lista_" + id + ".xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelStream.readAllBytes());
    }
}
