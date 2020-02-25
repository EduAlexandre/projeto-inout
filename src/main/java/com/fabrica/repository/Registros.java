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
	
	
	
	@Query(value = "select registro.nome,registro.cpf ,registro.quant_ent , GROUP_CONCAT(dia.dia ORDER BY dia.id_dia)  from registro inner join dia on (registro.id = dia.id_registro) GROUP BY registro.cpf",nativeQuery = true)
	List<String> listarTodos();

	@Query(value = "select * from registro where quant_ent = ?1", nativeQuery = true)
	List<Registro> listarTodosIgualAUmNumero(Integer numero);
	
	@Query(value =  "select * from dia where dia = ?1", nativeQuery = true)
	List<Registro> lista(String string);
	
	
	@Query(value = "select registro.nome,registro.cpf ,registro.quant_ent , GROUP_CONCAT(dia.dia ORDER BY dia.id_dia)  from registro inner join dia on (registro.id = dia.id_registro) where quant_ent = ?1 GROUP BY registro.cpf",nativeQuery = true)
	List<String> listarTodosIgual(Integer numero);	
	
	
	

}
