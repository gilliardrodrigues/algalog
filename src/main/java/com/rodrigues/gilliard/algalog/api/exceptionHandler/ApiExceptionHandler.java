package com.rodrigues.gilliard.algalog.api.exceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rodrigues.gilliard.algalog.domain.exception.EntidadeNaoEncontradaException;
import com.rodrigues.gilliard.algalog.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
    private MessageSource msgSource;
	
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, montarCorpoDoErro(ex, "Erro: um ou mais campos incorretos!", status), headers, status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(ex, montarCorpoDoErro(ex.getMessage(), status), new HttpHeaders(), status, request);
    }
    
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(ex, montarCorpoDoErro(ex.getMessage(), status), new HttpHeaders(), status, request);
    }
    
    private List<Erro.Campo> retornarListaDeErros(MethodArgumentNotValidException ex){
        List<Erro.Campo> erros = new ArrayList<>();

        for(ObjectError erro : ex.getBindingResult().getAllErrors()){
            String nomeCampo = ((FieldError) erro).getField();
            String mensagem = msgSource.getMessage(erro, LocaleContextHolder.getLocale());

            erros.add(new Erro.Campo(nomeCampo, mensagem));
        }
        
        return erros;
    }
	
	private Erro montarCorpoDoErro(MethodArgumentNotValidException ex, String mensagem, HttpStatus status){
        var erro = new Erro();

        erro.setStatus(status.value());
        erro.setDataHora(OffsetDateTime.now());
        erro.setTitulo(mensagem);
        erro.setCampos(retornarListaDeErros(ex));

        return erro;
    }
	
	private Erro montarCorpoDoErro(String mensagem, HttpStatus status){
        var erro = new Erro();

        erro.setStatus(status.value());
        erro.setDataHora(OffsetDateTime.now());
        erro.setTitulo(mensagem);

        return erro;
    }
}
