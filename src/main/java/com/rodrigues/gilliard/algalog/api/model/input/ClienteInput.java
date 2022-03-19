package com.rodrigues.gilliard.algalog.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteInput
{
	@NotBlank
	private String nome;
	
	@NotBlank
    private String email;
    
    @NotBlank
    private String telefone;
}
