package com.fabrica.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fabrica.model.Registro;
import com.fabrica.service.DiasService;
import com.fabrica.service.RegistroService;

@Controller
public class RelatorioController {

	
	@Autowired
	private RegistroService serviceRegistro;
	
	@Autowired
	private DiasService serviceDias;
	
	@Autowired
	private ServletContext context;

	@GetMapping("/gerarExcel")
	public void gerarExcel(HttpServletRequest request, HttpServletResponse response) {
		List<String> registro = serviceRegistro.listarTodos();
		boolean isFlag = serviceRegistro.gerarExcel(registro, context, request, response);
		if(isFlag) {
			String  fullPath = request.getServletContext().getRealPath("/resources/reports/"+"Relatorio"+".xls");
			filedownload(fullPath, response ,"Relatorio.xls");
		}
	}
	
	private void filedownload(String fullPath, HttpServletResponse response, String fileName) {
		File file = new File(fullPath);
		final int BUFFER_SIZE = 4096;
		
		if(file.exists()) {
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				String mimeType = context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader("content-disposition", "attachment; filename="+fileName);
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while((bytesRead = fileInputStream.read(buffer))!= -1) {
					outputStream.write(buffer, 0 ,bytesRead);
				}
				fileInputStream.close();
				outputStream.close();
				file.delete();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@GetMapping("/gerarExcel1/{numero}")
	public void gerarExcel1(@PathVariable("numero")Integer numero,HttpServletRequest request, HttpServletResponse response) {
		List<String> registro = serviceRegistro.listarTodosIgual(numero);
		boolean isFlag = serviceRegistro.gerarExcel(registro, context, request, response);
		if(isFlag) {
			String  fullPath = request.getServletContext().getRealPath("/resources/reports/"+"Relatorio"+".xls");
			filedownload(fullPath, response ,"Relatorio.xls");
		}
	}

	
}
