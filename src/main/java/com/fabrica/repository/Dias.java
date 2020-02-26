package com.fabrica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fabrica.model.Dia;

public interface Dias extends JpaRepository<Dia, Integer> {

	@Query(value =  "select * from dia where dia = ?1", nativeQuery = true)
	public List<Dia> listarTodos(String dia);
	
	@Query(value =  "select * from dia where id_registro = ?1", nativeQuery = true)
	public List<Dia> listarPorIdRegistro(Integer id);
	
	@Query(value =  "select * from dia where id_registro = ?1", nativeQuery = true)
	public Dia listarPorIdRegist(Integer id);
}
