package com.rodrigues.gilliard.algalog.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rodrigues.gilliard.algalog.domain.exception.NegocioException;
import com.rodrigues.gilliard.algalog.domain.model.Cliente;
import com.rodrigues.gilliard.algalog.domain.repository.ClienteRepository;

@Service
public class ClienteService
{
	@Autowired
	private ClienteRepository clienteRepository;
		
	public boolean verificaSeClienteExistePeloId(Long idCliente) {
		return clienteRepository.existsById(idCliente);
	}

	public List<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}
	
	public Optional<Cliente> buscarPeloId(Long idCliente) {
		return clienteRepository.findById(idCliente);
	}
	
	@Transactional
    public Cliente salvar(Cliente cliente) {
        boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
                .stream()
                .anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
        if(emailEmUso)
            throw new NegocioException("Já existe um cliente cadastrado com este e-mail!");
        return clienteRepository.save(cliente);
    }
	
	@Transactional
    public void excluir(Long idCliente) {
        clienteRepository.deleteById(idCliente);
    }
}
