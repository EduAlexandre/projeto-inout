package com.fabrica.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabrica.model.Dia;
import com.fabrica.model.Registro;
import com.fabrica.repository.Dias;

@Service
public class DiasService {

	@Autowired
	private Dias repository;
	
	
	public void save(Registro registro) {
		
		Dia dia = new Dia();
		
		LocalDateTime data = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		Locale.setDefault (new Locale ("pt", "BR"));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE");
		String formatDateTime = data.format(formatter);
		
		dia.setRegistro(registro);
		dia.setDia(formatDateTime);
		
		repository.save(dia);
		
	}
	
	
	public List<Dia> listarPornome(String nome){
		return repository.listarTodos(nome);
	}
	
	public List<Dia> listarPorIdRegistro(Integer id){
		return repository.listarPorIdRegistro(id);
	}
}
