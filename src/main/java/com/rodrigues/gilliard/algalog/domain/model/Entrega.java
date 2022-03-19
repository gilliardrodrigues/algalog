package com.rodrigues.gilliard.algalog.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.rodrigues.gilliard.algalog.domain.ValidationGroups;
import com.rodrigues.gilliard.algalog.domain.exception.NegocioException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Valid
    @ConvertGroup(to = ValidationGroups.ClienteId.class)
    @NotNull
    @ManyToOne
    private Cliente cliente;

    @Valid
    @NotNull
    @Embedded
    private Destinatario destinatario;

    @NotNull
    private BigDecimal taxa;

    @JsonProperty(access = Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private StatusEntrega status;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataPedido;

    @JsonProperty(access = Access.READ_ONLY)
    private OffsetDateTime dataFinalizacao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entrega")
    private List<Ocorrencia> ocorrencias;

    public Ocorrencia adicionarOcorrencia(String descricao) {
	Ocorrencia ocorrencia = new Ocorrencia();
	ocorrencia.setDescricao(descricao);
	ocorrencia.setDataRegistro(OffsetDateTime.now());
	ocorrencia.setEntrega(this);

	this.getOcorrencias().add(ocorrencia);

	return ocorrencia;
    }

    public boolean estaComStatusPendente() {
	return StatusEntrega.PENDENTE.equals(getStatus());
    }
    
    public void finalizar() {
	if(!estaComStatusPendente())
	    throw new NegocioException("Entrega não pode ser finalizada!");
	
	setStatus(StatusEntrega.FINALIZADA);
	setDataFinalizacao(OffsetDateTime.now());
    }

    public void cancelar() {
	if(!estaComStatusPendente())
	    throw new NegocioException("Entrega não pode ser cancelada!");
	
	setStatus(StatusEntrega.CANCELADA);
    }
}
