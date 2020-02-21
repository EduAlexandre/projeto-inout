package com.fabrica.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabrica.model.Registro;
import com.fabrica.repository.Registros;

@Service
public class RegistroService {
	
	@Autowired
	private Registros repository;
	
	public List<Registro> listAll(){
		return repository.findAll();
	}

	public void save(Registro registro){
	
		LocalDateTime data = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String formatDateTime = data.format(formatter);
		
		registro.setDataEntrada(formatDateTime);
		registro.setFlag(true);
		registro.setQuantEnt(registro.getQuantEnt() + 1);
		repository.save(registro);
	}
	
	public Registro get(Long id) {
		return repository.findById(id).get();
	}
	
	public Registro verificaCPF(String cpf) {
		return repository.findByCpf(cpf);
	}

	public Registro searchForCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	public Registro buscarPorCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	public void alterarStatus(){

      boolean flag = false;
		repository.updateStatus(flag);

	}

}
