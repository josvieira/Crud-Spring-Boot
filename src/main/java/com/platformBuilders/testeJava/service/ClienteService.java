package com.platformBuilders.testeJava.service;

import com.platformBuilders.testeJava.dto.ClienteDto;
import com.platformBuilders.testeJava.dto.ClienteRespostaDto;
import com.platformBuilders.testeJava.mapperFake.ClienteMapeador;
import com.platformBuilders.testeJava.mapperFake.ClienteRespostaMapeador;
import com.platformBuilders.testeJava.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.platformBuilders.testeJava.repository.ClienteRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    public ClienteRespostaDto cadastrarCliente(ClienteDto clienteDto) {
        Cliente cliente = ClienteMapeador.transformarEmCliente(clienteDto);
        return ClienteRespostaMapeador.transformarEmDto(clienteRepository.save(cliente));
    }

    public Page<List<Map<String, String>>> find(Pageable pageable){
        return clienteRepository.find(pageable);
    }


    public Page<ClienteRespostaDto> listarClientes(Pageable pageable) {
        Page<Cliente> pageCliente = clienteRepository.findAll(pageable);
        return new PageImpl<ClienteRespostaDto>(ClienteRespostaMapeador.converterEm(pageCliente.getContent()), pageable, pageCliente.getTotalElements());
    }

    public Page<ClienteRespostaDto> buscarClienteCpf(String cpf, Pageable pageable){
        Page<Cliente> cliente = clienteRepository.findByCpf(cpf, pageable);
        return new PageImpl<>(ClienteRespostaMapeador.converterEm(cliente.getContent()), pageable, cliente.getTotalElements());
    }

    public Optional<Cliente> buscarClientePorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public Page<ClienteRespostaDto> buscarClienteNome(String nome, Pageable pageable) {
        Page<Cliente> cliente = clienteRepository.findByNomeContainingIgnoreCase(nome, pageable);
        return new PageImpl<ClienteRespostaDto>(ClienteRespostaMapeador.converterEm(cliente.getContent()), pageable, cliente.getTotalElements());
    }

}
