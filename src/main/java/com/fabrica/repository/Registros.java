package com.fabrica.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fabrica.model.Registro;

public interface Registros extends JpaRepository<Registro, Long> {

	Registro findByCpf(String cpf);

	@Transactional
	@Modifying
	@Query("Update Registro t SET t.flag=:title")
	public void updateStatus(@Param("title") boolean title);
	
	
	
	@Query(value = "select * from registro r join dia d on r.id = d.id_registro",nativeQuery = true)
	List<Registro> listarTodos();

	@Query(value = "select * from registro where quant_ent > 1", nativeQuery = true)
	List<Registro> listarTodosMaior1();
	
	@Query(value =  "select * from dia where dia = ?1", nativeQuery = true)
	List<Registro> lista(String string);	
	
	
	

}
