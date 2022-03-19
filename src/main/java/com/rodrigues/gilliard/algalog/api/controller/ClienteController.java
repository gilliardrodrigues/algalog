package com.rodrigues.gilliard.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigues.gilliard.algalog.api.mapper.ClienteMapper;
import com.rodrigues.gilliard.algalog.api.model.ClienteModel;
import com.rodrigues.gilliard.algalog.api.model.input.ClienteInput;
import com.rodrigues.gilliard.algalog.domain.model.Cliente;
import com.rodrigues.gilliard.algalog.domain.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
    private ClienteService clienteService;
	
	@Autowired
	private ClienteMapper clienteMapper;

    @GetMapping
    public List<ClienteModel> listar() {
        return clienteMapper.toCollectionModel(clienteService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> buscarPeloId(@PathVariable Long id) {
        return clienteService.buscarPeloId(id)
        		.map(cliente -> ResponseEntity.ok(clienteMapper.toModel(cliente)))
				.orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ClienteModel salvar(@Valid @RequestBody ClienteInput clienteInput){
    	Cliente novoCliente = clienteMapper.toEntity(clienteInput);
    	Cliente clienteSalvo = clienteService.salvar(novoCliente);
        return clienteMapper.toModel(clienteSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> alterar(@Valid @PathVariable Long id, @RequestBody ClienteInput clienteInput){
        Cliente cliente = clienteMapper.toEntity(clienteInput);
    	cliente.setId(id);
        return clienteService.verificaSeClienteExistePeloId(id) ? ResponseEntity.ok(clienteMapper.toModel(clienteService.salvar(cliente))) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        if(!clienteService.verificaSeClienteExistePeloId(id))
            return ResponseEntity.notFound().build();
        clienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
