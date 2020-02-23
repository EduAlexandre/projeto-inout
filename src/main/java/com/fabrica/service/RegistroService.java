package com.fabrica.service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
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
	
	public List<Registro> listarTodos(){
		return repository.listarTodos();
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
	
	
	public boolean gerarExcel(List<Registro> registro, ServletContext context, HttpServletRequest request,HttpServletResponse response) {
		
		String filePath = context.getRealPath("/resources/reports");
		File file = new File(filePath);
		boolean exists = new File(filePath).exists();
		if(!exists) {
			new File(filePath).mkdirs();
		}
		
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file+"/"+"Relatorio"+".xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Relatorio");
			sheet.setDefaultColumnWidth(30);
			
			HSSFCellStyle headerCell = workbook.createCellStyle();
			headerCell.setFillBackgroundColor(HSSFColor.BLUE.index);
			
			HSSFRow hssfRow = sheet.createRow(0);
			
			HSSFCell nome = hssfRow.createCell(0);
			nome.setCellValue("Nome");
			nome.setCellStyle(headerCell);
			
			HSSFCell cpf = hssfRow.createCell(1);
			cpf.setCellValue("CPF");
			cpf.setCellStyle(headerCell);
			
			HSSFCell horarioEntrada = hssfRow.createCell(2);
			horarioEntrada.setCellValue("Horario de Entrada");
			horarioEntrada.setCellStyle(headerCell);
			
			int i = 1;
			
			for (Registro registro2 : registro) {
				HSSFRow bodyRow = sheet.createRow(i);
				
				
				HSSFCellStyle bodyCell = workbook.createCellStyle();
				bodyCell.setFillBackgroundColor(HSSFColor.WHITE.index);
				
				HSSFCell nomeValor = bodyRow.createCell(0);
				nomeValor.setCellValue(registro2.getNome());
				nomeValor.setCellStyle(bodyCell);
				
				HSSFCell cpfValor = bodyRow.createCell(1);
				cpfValor.setCellValue(registro2.getCpf());
				cpfValor.setCellStyle(bodyCell);
				
				HSSFCell horarioEntradaValor = bodyRow.createCell(2);
				horarioEntradaValor.setCellValue(registro2.getDataEntrada());
				horarioEntradaValor.setCellStyle(bodyCell);
				
				i++;
			}
			
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return true;
			
			
		} catch (Exception e) {
			return false;
		}
		
		
	}

	public List<Registro> listarTodosMaior1() {
		return repository.listarTodosMaior1();
	}
	

}
