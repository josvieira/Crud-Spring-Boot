package com.platformBuilders.testeJava.mapper;

import com.platformBuilders.testeJava.dto.ClienteRespostaDto;
import com.platformBuilders.testeJava.models.Cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteRespostaMapeador {

    public static ClienteRespostaDto transformarEmDto(Cliente cliente){
        return new ClienteRespostaDto(cliente.getId(), cliente.getNome(), calcularIdade(cliente.getDataNascimento()));
    }

    public static List<ClienteRespostaDto> converterEm(List<Cliente> clientes){
        List<ClienteRespostaDto> dtoList = new ArrayList<ClienteRespostaDto>();
        for (Cliente iterator: clientes){
            dtoList.add(transformarEmDto(iterator));
        }
        return dtoList;
    }

    /**
     *
     * @param dataNascimento
     * @return valor negativo se ano de nascimento maior que ano atual
     * @return 0 se ano de nascimento igual a ano atual
     * @return idade correta
     */
    public static Integer calcularIdade(LocalDate dataNascimento){
        if (dataNascimento == null)
            return 0;

        LocalDate data = LocalDate.now();
        Integer idade = data.getYear() - dataNascimento.getYear();
        if(dataNascimento.getMonthValue() > data.getMonthValue()){
            idade -=1;
        }
        else{
            if (dataNascimento.getMonthValue() == data.getMonthValue()
                    && dataNascimento.getDayOfMonth() > data.getDayOfMonth()){
                idade -= 1;
            }
        }
        return idade;
    }

}
