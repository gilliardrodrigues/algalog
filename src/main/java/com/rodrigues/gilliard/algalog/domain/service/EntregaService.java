package com.rodrigues.gilliard.algalog.domain.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodrigues.gilliard.algalog.domain.exception.EntidadeNaoEncontradaException;
import com.rodrigues.gilliard.algalog.domain.model.Cliente;
import com.rodrigues.gilliard.algalog.domain.model.Entrega;
import com.rodrigues.gilliard.algalog.domain.model.StatusEntrega;
import com.rodrigues.gilliard.algalog.domain.repository.EntregaRepository;

@Service
public class EntregaService {
    
    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private ClienteService clienteService;

    @Transactional
    public Entrega solicitar(Entrega entrega) {

	Cliente cliente = clienteService.buscarPeloId(entrega.getCliente().getId()).get();
	entrega.setCliente(cliente);
	entrega.setStatus(StatusEntrega.PENDENTE);
	entrega.setDataPedido(OffsetDateTime.now());

	return entregaRepository.save(entrega);
    }

    @Transactional
    public void finalizar(Long idEntrega) {
	Entrega entrega = buscarPeloId(idEntrega).get();
	
	entrega.finalizar();
	
	entregaRepository.save(entrega);
    }
    
    @Transactional
    public void cancelar(Long idEntrega) {
	Entrega entrega = buscarPeloId(idEntrega).get();
	
	entrega.cancelar();
	
	entregaRepository.save(entrega);
    }

    public List<Entrega> buscarTodas() {
	return entregaRepository.findAll();
    }

    public Optional<Entrega> buscarPeloId(Long idEntrega) {
	return Optional.of(entregaRepository.findById(idEntrega)
		.orElseThrow(() -> new EntidadeNaoEncontradaException("Entrega não encontrada!")));
    }
}
