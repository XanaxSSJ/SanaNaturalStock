package com.sananatural.backend.controller;

import com.sananatural.backend.dto.ProductoDTO;
import com.sananatural.backend.model.Producto;
import com.sananatural.backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    /**
     * Devuelve un producto por ID.
     */
    @GetMapping("/{id}")
    public Optional<Producto> obtenerPorId(@PathVariable Long id) {
        return productoRepository.findById(id);
    }

    /**
     * Devuelve productos filtrados en formato DTO (para llenar datagrid o exportar Excel).
     */
    @GetMapping("/filtro")
    public List<ProductoDTO> obtenerProductosFiltradosDTO(
            @RequestParam(required = false) String codigo,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String proveedor
    ) {
        return productoRepository.findAll().stream()
                .filter(p -> codigo == null || p.getCodigo().toLowerCase().contains(nombre.toLowerCase()))
                .filter(p -> nombre == null || p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(p -> categoria == null ||
                        (p.getCategoria() != null &&
                                p.getCategoria().getNombre().equalsIgnoreCase(categoria)))
                .filter(p -> proveedor == null ||
                        (p.getProveedor() != null &&
                                p.getProveedor().getNombre().equalsIgnoreCase(proveedor)))
                .map(p -> new ProductoDTO(
                        p.getId(),
                        p.getCodigo(),
                        p.getNombre(),
                        p.getCategoria() != null ? p.getCategoria().getNombre() : null,
                        p.getProveedor() != null ? p.getProveedor().getNombre() : null
                ))
                .collect(Collectors.toList());
    }

}
