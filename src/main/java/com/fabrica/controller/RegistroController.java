package com.fabrica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fabrica.model.Registro;
import com.fabrica.service.RegistroService;

@Controller
public class RegistroController {

	
	@Autowired
	private RegistroService serviceRegistro;
	
	@GetMapping("/")
	public String showPage(Model model) {
		
		Registro registro = new Registro();
		model.addAttribute("registro", registro);
		return "index";
	}
	
	
	@PostMapping("/salvarRegistro")
	public String saveRegistro(Registro registro) {
		
		serviceRegistro.save(registro);
		
		return "redirect:/";
	}
	
	@PostMapping("/verificationCPF")
	@ResponseBody
	public String valideCpf(@RequestParam(name = "cpf") String cpf) {

		Boolean cpfChecado = serviceRegistro.verificaCPF(cpf) == null;
		System.out.println(cpfChecado);
		return cpfChecado.toString();
		
	}
}
