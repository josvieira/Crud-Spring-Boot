package com.platformBuilders.testeJava.dto;

import com.platformBuilders.testeJava.models.Cliente;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRespostaDto {

    private String nome;
    private Integer idade;

}
