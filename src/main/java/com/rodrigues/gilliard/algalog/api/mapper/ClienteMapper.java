package com.rodrigues.gilliard.algalog.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rodrigues.gilliard.algalog.api.model.ClienteModel;
import com.rodrigues.gilliard.algalog.api.model.input.ClienteInput;
import com.rodrigues.gilliard.algalog.domain.model.Cliente;

@Component
public class ClienteMapper
{
	@Autowired
	private ModelMapper modelMapper;
	
	public ClienteModel toModel(Cliente cliente) {
		return modelMapper.map(cliente, ClienteModel.class);
	}
	
	public List<ClienteModel> toCollectionModel(List<Cliente> clientes) {
		return clientes.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Cliente toEntity(ClienteInput clienteInput) {
			return modelMapper.map(clienteInput, Cliente.class);
		}
}
