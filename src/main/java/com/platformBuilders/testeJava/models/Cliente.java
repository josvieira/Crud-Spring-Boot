package com.platformBuilders.testeJava.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//Não funcionou com o identity
    private Long id;

    @NotNull(message = "{Nome não pode ser nulo}")
    @NotEmpty(message = "{Nome precisa ser inserido}")
    @NotBlank(message = "Nome não pode estar em branco")
    private String nome;

    @Size(min = 11, max = 11, message = "Digite um CPF válido, mínimo onze dígitos")
    @NotBlank(message = "CPF não pode estar em branco")
    @NotNull(message = "CPF não pode ser nulo")
    private String cpf;

    @NotNull(message = "Data de nascimento não pode ser nulo")
    private LocalDate dataNascimento;


    public Cliente(String nome, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }
}
