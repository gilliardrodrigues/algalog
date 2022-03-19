package com.rodrigues.gilliard.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigues.gilliard.algalog.api.mapper.OcorrenciaMapper;
import com.rodrigues.gilliard.algalog.api.model.OcorrenciaModel;
import com.rodrigues.gilliard.algalog.api.model.input.OcorrenciaInput;
import com.rodrigues.gilliard.algalog.domain.model.Entrega;
import com.rodrigues.gilliard.algalog.domain.model.Ocorrencia;
import com.rodrigues.gilliard.algalog.domain.service.EntregaService;
import com.rodrigues.gilliard.algalog.domain.service.OcorrenciaService;

@RestController
@RequestMapping("/entregas/{idEntrega}/ocorrencias")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @Autowired
    private OcorrenciaMapper ocorrenciaMapper;

    @Autowired
    private EntregaService entregaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OcorrenciaModel registrar(@PathVariable Long idEntrega, @Valid @RequestBody OcorrenciaInput ocorrenciaInput) {
	Ocorrencia ocorrencia = ocorrenciaService.registrar(idEntrega, ocorrenciaInput.getDescricao());
	return ocorrenciaMapper.toModel(ocorrencia);
    }

    @GetMapping
    public List<OcorrenciaModel> listar(@PathVariable Long idEntrega) {
	Entrega entrega = entregaService.buscarPeloId(idEntrega).get();
	return ocorrenciaMapper.toCollectionModel(entrega.getOcorrencias());
    }
}
