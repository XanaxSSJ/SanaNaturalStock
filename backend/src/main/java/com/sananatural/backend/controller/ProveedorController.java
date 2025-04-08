package com.sananatural.backend.controller;

import com.sananatural.backend.model.Proveedor;
import com.sananatural.backend.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@CrossOrigin(origins = "*")
public class ProveedorController {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping
    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }
}
