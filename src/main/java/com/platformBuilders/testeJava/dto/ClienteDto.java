package com.platformBuilders.testeJava.dto;

import com.platformBuilders.testeJava.models.Cliente;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClienteDto {

    @NotNull(message = "{Nome não pode ser nulo}")
    @NotEmpty(message = "{Nome precisa ser inserido}")
    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;

    @Size(min = 11, max = 11, message = "Digite um CPF válido, mínimo onze dígitos")
    @NotBlank(message = "CPF não pode estar em branco")
    @NotNull(message = "CPF não pode ser nulo")
    private String cpf;

    @NotNull(message = "Data de nascimento não pode ser nulo")
    //Não pode usar @NotBlank e @NotEmpty pois data de nascimento é uma classe
    //@NotEmpty(message = "Data de nascimento precisa ser inserido")
    //@NotBlank(message = "Data de nascimento não pode estar em branco")
    private LocalDate dataNascimento;

}
