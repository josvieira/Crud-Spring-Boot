package com.platformBuilders.testeJava.controller;

import com.platformBuilders.testeJava.dto.ClienteDto;
import com.platformBuilders.testeJava.dto.ClienteRespostaDto;
import com.platformBuilders.testeJava.models.Cliente;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(value = "Api Rest Cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Cadastrar um Cliente")
    @ApiResponses(value= {
            @ApiResponse(code= 201, message= "Cliente persistido no banco"),
            @ApiResponse(code= 409, message = "Já existe Cliente com o mesmo CPF"),
            @ApiResponse(code = 400, message = "Parametros de entrada inválidos")
    })
    private ResponseEntity<ClienteRespostaDto> cadastrarCliente(@RequestBody @Valid ClienteDto clienteDto) {
        /*Ainda não consigo capturar a exceção que o @Valid dispara, as mensagens aparecem no terminal da IDE mas
         *não no Json resposta para o client
         */
        Optional<Cliente> cliente = clienteService.buscarClientePorCpf(clienteDto.getCpf());
        if (!cliente.isPresent()) {
            return new ResponseEntity<>(clienteService.cadastrarCliente(clienteDto), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /*
     *Esse get é parecido com o buscarCliente, porém qiz testar uma abordagem diferente no retorno
     */
    @GetMapping(path = "/idade", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Lista todos os clientes cadastrados")
    @ApiResponses(value= @ApiResponse(code = 200, message = "Todos os Clientes listados"))
    private ResponseEntity<Page<List<Map<String, String>>>> buscar(Pageable pageable){
        return new ResponseEntity<>(clienteService.find(pageable), HttpStatus.OK);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Lista todos os Clientes ou faz uma buscar por meio da QueryString passada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todos os Clientes listados"),
            @ApiResponse(code = 302, message = "Cliente encontrado, com o parametro passado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado, com o parametro passado")
    })
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
                try {
                    return new ResponseEntity<>(clienteService.buscarClienteNome(nome, pageable), HttpStatus.FOUND);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        }

    }

    @PatchMapping(path = "/{id}", consumes = {MediaType.ALL_VALUE})
    @ApiOperation(value = "Atualizar nome de um cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Dados do Cliente atualizado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado, não pode ser atualizado")
    })
    private ResponseEntity<Cliente> atualizarDadosCliente(@RequestBody String nome, @PathVariable(value = "id") Long id){
        try {
            Optional<Cliente> clienteOp = clienteService.buscarClienteId(id);
            System.out.println(nome);
            if (!clienteOp.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Cliente cliente = new Cliente(clienteOp.get().getId(), nome.intern(), clienteOp.get().getCpf(), clienteOp.get().getDataNascimento());
            return new ResponseEntity<>(clienteService.atualizarNomeCliente(cliente), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}")
    @ApiOperation(value = "Atualizar um cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Cliente não encontrado, não pode ser atualizado" ),
            @ApiResponse(code = 200, message = "Cliente atualizado"),
            @ApiResponse(code = 400, message = "Dados para atualizar Cliente, inválidos, ou cliente está duplicado")
    })
    private ResponseEntity<Cliente> atualizarCliente(@RequestBody @Valid ClienteDto clienteDto, @PathVariable(value = "id") Long id){
        try {
            Optional<Cliente> clienteOp = clienteService.buscarClienteId(id);
            if (!clienteOp.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Cliente cliente = new Cliente(clienteOp.get().getId(), clienteDto.getNome(), clienteDto.getCpf(), clienteDto.getDataNascimento());
            return new ResponseEntity<>(clienteService.atualizarCliente(cliente), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping(path = "/{cpf}")
    @ApiOperation(value = "deleta um cliente pelo cpf passado")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Cliente não encontrado, não pode ser deletado"),
            @ApiResponse(code = 200, message = "Cliente Deletado"),
            @ApiResponse(code = 400, message = "Cliente não retornado por haver duplicidade")
    })
    private ResponseEntity<Void> deletarCliente(@PathVariable(value = "cpf") String cpf){
        try{
            Optional<Cliente> cliBuscado = clienteService.buscarClientePorCpf(cpf);
            if(!cliBuscado.isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Cliente cliDeletar = new Cliente(cliBuscado.get().getId(), cliBuscado.get().getNome(),
                    cliBuscado.get().getCpf(), cliBuscado.get().getDataNascimento());
            clienteService.removerCliente(cliDeletar);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
