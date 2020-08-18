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
        //Ainda não consigo capturar a exceção que o @Valid dispara, as mensagens aparecem no terminal da IDE mas não na resposta para o client
        Optional<Cliente> cliente = clienteService.buscarClientePorCpf(clienteDto.getCpf());
        if (!cliente.isPresent()) {
            return new ResponseEntity<>(clienteService.cadastrarCliente(clienteDto), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping(path = "/idade")
    /*
    *Esse get é parecido com o buscarCliente, porém quiz testar uma abordagem diferente no retorno
    */
    private ResponseEntity<Page<List<Map<String, String>>>> find(Pageable pageable){
        return new ResponseEntity<>(clienteService.find(pageable), HttpStatus.OK);
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    private ResponseEntity<Page<ClienteRespostaDto>> buscarCliente(Pageable pageable,
                                              @RequestParam(required = false, defaultValue = "") String nome,
                                              @RequestParam(required = false, defaultValue = "") String cpf){
        if (nome.isEmpty() && cpf.isEmpty()){
            return new ResponseEntity<>(clienteService.listarClientes(pageable), HttpStatus.OK);
        }
        else {
            //Da forma que está implementada prioriza a busca pelo nome, não fará a busca combinando Cpf e nome
            if (nome.isEmpty()) {
                try {
                    return new ResponseEntity<>(clienteService.buscarClienteCpf(cpf, pageable), HttpStatus.FOUND);
                } catch (Exception e) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            else{
                System.out.println("Entrou no cpf.isEmpty");
                try {
                    return new ResponseEntity<>(clienteService.buscarClienteNome(nome, pageable), HttpStatus.FOUND);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }

    }

}
