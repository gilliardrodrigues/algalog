package com.rodrigues.gilliard.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigues.gilliard.algalog.api.mapper.EntregaMapper;
import com.rodrigues.gilliard.algalog.api.model.EntregaModel;
import com.rodrigues.gilliard.algalog.api.model.input.EntregaInput;
import com.rodrigues.gilliard.algalog.domain.model.Entrega;
import com.rodrigues.gilliard.algalog.domain.service.EntregaService;

@RestController
@RequestMapping("/entregas")
public class EntregaController {
    
    @Autowired
    private EntregaService entregaService;

    @Autowired
    private EntregaMapper entregaMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EntregaModel solicitar(@Valid @RequestBody EntregaInput entregaInput) {
	Entrega novaEntrega = entregaMapper.toEntity(entregaInput);
	Entrega entregaSolicitada = entregaService.solicitar(novaEntrega);
	return entregaMapper.toModel(entregaSolicitada);
    }

    @GetMapping
    public List<EntregaModel> listar() {
	return entregaMapper.toCollectionModel(entregaService.buscarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntregaModel> buscarPeloId(@PathVariable Long id) {
	return entregaService.buscarPeloId(id).map(entrega -> ResponseEntity.ok(entregaMapper.toModel(entrega)))
		.orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/finalizacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable Long id) {
	entregaService.finalizar(id);
    }
    
    @PutMapping("/{id}/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable Long id) {
	entregaService.cancelar(id);
    }
}
