package com.platformBuilders.testeJava.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.*;
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

    //Não preciso colocar as validações aqui pois já estou validando no ClienteDTO
    private String nome;

    private String cpf;

    private LocalDate dataNascimento;


    public Cliente(String nome, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }
}
