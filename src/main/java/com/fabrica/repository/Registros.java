package com.fabrica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabrica.model.Registro;

public interface Registros extends JpaRepository<Registro, Long> {

	Registro findByCpf(String cpf);

}
