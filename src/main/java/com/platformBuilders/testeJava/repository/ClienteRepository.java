package com.platformBuilders.testeJava.repository;

import com.platformBuilders.testeJava.dto.ClienteRespostaDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.platformBuilders.testeJava.models.Cliente;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

//    @Query(value = "select nome, extract(year from age(cliente.data_nascimento))) " +
//            "from cliente", nativeQuery = true)
//    public Page<Map<String, String>> find(Pageable pageable);

    public Optional<Cliente> findByCpf(String cpf);

    public Page<Cliente> findByCpfContainingIgnoreCaseAndNomeContainingIgnoreCase(String cpf, String nome, Pageable pageable);

    public Page<Cliente> findByCpfContainingIgnoreCase(String cpf, Pageable pageable);

    public Page<Cliente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

}
