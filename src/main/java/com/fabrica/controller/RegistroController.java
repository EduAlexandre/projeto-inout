package com.fabrica.controller;

import java.sql.SQLException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fabrica.model.Registro;
import com.fabrica.service.RegistroService;
import com.google.gson.Gson;

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
	
	
	@GetMapping("/lista")
	public String showPageList(Model model) {
		
		List<Registro> registro = serviceRegistro.listAll();
		model.addAttribute("registros", registro);
		return "lista";
	}
	
	@PostMapping("/pesquisar")
	public String pesqui(@RequestParam(name = "cpf")String cpf,Model model) {
		
		if(!cpf.isEmpty()) {
			Registro registro = serviceRegistro.buscarPorCpf(cpf);
			model.addAttribute("registros", registro);
			
			return "lista";
		}else {
			List<Registro> registro = serviceRegistro.listAll();
			model.addAttribute("registros", registro);
			
			return "lista";
		}
		
	}
	
	@PostMapping("/salvarRegistro")
	public String saveRegistro(Registro registro,RedirectAttributes model) {
		
		
		serviceRegistro.save(registro);
		Registro registro1 = serviceRegistro.searchForCpf(registro.getCpf());
		model.addFlashAttribute("ms", "Seja Bem Vindo "+registro1.getNome());
		
		return "redirect:/";
	}
	
	@PostMapping("/verificationCPF")
	@ResponseBody
	public String valideCpf(@RequestParam(name = "cpf") String cpf) {

		Boolean cpfChecado = serviceRegistro.verificaCPF(cpf) == null;
		
		if(cpfChecado != true) {
			
			return cpfChecado.toString();
		
		}else {
			String valor = "true";
			return valor.toString();
		}
		
	}
	
	@GetMapping("/verificationFlag")
	@ResponseBody
	public String valideFlag(@PathParam("cpf") String cpf) {
		
		Boolean cpfChecado = serviceRegistro.verificaCPF(cpf) == null;

		if(cpfChecado != true) {
			Registro registro = serviceRegistro.searchForCpf(cpf);
			
			if(registro.getFlag() != true) {
				String valor = "false";
				
				serviceRegistro.save(registro);
				
				return valor.toString();
			}else {
				String valor = "true";
				return valor.toString();
			}
		}else {
			return "";
		}
		
	}
	
	@GetMapping("/getName")
    public @ResponseBody String atualizaTotal(@PathParam("cpf") String cpf) throws SQLException{	
	
		Registro registro = serviceRegistro.searchForCpf(cpf);
		
		Gson gson = new Gson(); 
		String valor = gson.toJson(registro);
		
        return valor;

    }

    @GetMapping("/resetarStatusRegistrosNoBanco")
	public String resetar(){

		return "reset";
	}

	@PostMapping("/resetStatus")
	public ModelAndView statusResetar(RedirectAttributes redirectAttributes, Model model){
      serviceRegistro.alterarStatus();
      redirectAttributes.addFlashAttribute("message", "campos alterados com sucesso");
      return new ModelAndView("reset");
	}
	
}
