package com.rodrigues.gilliard.algalog.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodrigues.gilliard.algalog.domain.model.Entrega;
import com.rodrigues.gilliard.algalog.domain.model.Ocorrencia;

@Service
public class OcorrenciaService {

    @Autowired
    private EntregaService entregaService;

    @Transactional
    public Ocorrencia registrar(Long idEntrega, String descricao) {

	Entrega entrega = entregaService.buscarPeloId(idEntrega).get();

	return entrega.adicionarOcorrencia(descricao);
    }
}
