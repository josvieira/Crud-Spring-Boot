package com.platformBuilders.testeJava.controller;

import com.platformBuilders.testeJava.dto.ClienteDto;
import com.platformBuilders.testeJava.dto.ClienteRespostaDto;
import com.platformBuilders.testeJava.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.platformBuilders.testeJava.service.ClienteService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    private ResponseEntity<ClienteRespostaDto> cadastrarCliente(@RequestBody @Valid ClienteDto clienteDto) {
        //Ainda não consigo capturar a exceção que o @Valid dispara
        Optional<Cliente> cliente = clienteService.buscarClienteCpf(clienteDto.getCpf());
        if (!cliente.isPresent()) {
            return new ResponseEntity<>(clienteService.cadastrarCliente(clienteDto), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    private ResponseEntity<Page<ClienteRespostaDto>> buscarCliente(Pageable pageable,
                                              @RequestParam(required = false, defaultValue = "") String nome,
                                              @RequestParam(required = false, defaultValue = "") String cpf){
        //if (nome.isEmpty() && cpf.isEmpty()){
            return new ResponseEntity<>(clienteService.listarClientes(pageable), HttpStatus.OK);
       // }
//        Page<ClienteRespostaDto> cliente = clienteService.buscarClientes(nome, cpf, pageable);
//        if(cliente.getContent().isEmpty()){
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//        else{
//            return new ResponseEntity<>(cliente, HttpStatus.FOUND);
//        }

    }

//
//    }
}
