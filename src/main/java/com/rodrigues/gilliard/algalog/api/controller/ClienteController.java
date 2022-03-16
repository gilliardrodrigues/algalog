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

import com.rodrigues.gilliard.algalog.domain.model.Cliente;
import com.rodrigues.gilliard.algalog.domain.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPeloId(@PathVariable Long id) {
        return clienteService.buscarPeloId(id)
        		.map(cliente -> ResponseEntity.ok(cliente))
				.orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cliente salvar(@Valid @RequestBody Cliente cliente){
        return clienteService.salvar(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> alterar(@Valid @PathVariable Long id, @RequestBody Cliente cliente){
        cliente.setId(id);
        return clienteService.verificaSeClienteExistePeloId(id) ? ResponseEntity.ok(clienteService.salvar(cliente)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        if(!clienteService.verificaSeClienteExistePeloId(id))
            return ResponseEntity.notFound().build();
        clienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
