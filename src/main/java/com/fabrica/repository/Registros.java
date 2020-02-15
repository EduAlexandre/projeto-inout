package com.fabrica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabrica.model.Registro;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface Registros extends JpaRepository<Registro, Long> {

	Registro findByCpf(String cpf);

	@Transactional
	@Modifying
	@Query("Update Registro t SET t.flag=:title")
	public void updateStatus(@Param("title") boolean title);
}
