package com.fabrica.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabrica.model.Registro;
import com.fabrica.repository.Registros;

@Service
public class RegistroService {
	
	@Autowired
	private Registros repository;
	

	public void save(Registro registro){
	
		LocalDateTime data = LocalDateTime.now();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String formatDateTime = data.format(formatter);
		
		registro.setDataEntrada(formatDateTime);
		registro.setFlag(true);
		repository.save(registro);
	}
	
	public Registro verificaCPF(String cpf) {
		return repository.findByCpf(cpf);
	}

}
