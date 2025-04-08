package com.sananatural.backend.controller;

import com.sananatural.backend.model.Tienda;
import com.sananatural.backend.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiendas")
@CrossOrigin(origins = "*")
public class TiendaController {

    @Autowired
    private TiendaRepository tiendaRepository;

    @GetMapping
    public List<Tienda> listarTodas() {
        return tiendaRepository.findAll();
    }
}
