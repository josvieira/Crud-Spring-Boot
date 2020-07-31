package com.platformBuilders.testeJava.mapperFake;

import com.platformBuilders.testeJava.dto.ClienteDto;
import com.platformBuilders.testeJava.models.Cliente;

public class ClienteMapeador {

    public static Cliente transformarEmCliente(ClienteDto clienteDto){
        return new Cliente(clienteDto.getNome(), clienteDto.getCpf(), clienteDto.getDataNascimento());
    }
}
