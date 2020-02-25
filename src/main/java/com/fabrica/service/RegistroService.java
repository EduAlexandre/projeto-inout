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
	
	public List<String> listarTodos(){
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
	
	
	public boolean gerarExcel(List<String> registro, ServletContext context, HttpServletRequest request,HttpServletResponse response) {
		
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
			
			HSSFCell numero = hssfRow.createCell(0);
			numero.setCellValue("Nº");
			numero.setCellStyle(headerCell);
			
			HSSFCell nome = hssfRow.createCell(1);
			nome.setCellValue("Nome");
			nome.setCellStyle(headerCell);
			
			HSSFCell cpf = hssfRow.createCell(2);
			cpf.setCellValue("CPF");
			cpf.setCellStyle(headerCell);
			
			HSSFCell horarioEntrada = hssfRow.createCell(3);
			horarioEntrada.setCellValue("Quantidade de Entradas");
			horarioEntrada.setCellStyle(headerCell);
			
			HSSFCell Entrada1 = hssfRow.createCell(4);
			Entrada1.setCellValue("Entrada 1");
			Entrada1.setCellStyle(headerCell);
			
			HSSFCell Entrada2 = hssfRow.createCell(5);
			Entrada2.setCellValue("Entrada 2");
			Entrada2.setCellStyle(headerCell);
			
			HSSFCell Entrada3 = hssfRow.createCell(6);
			Entrada3.setCellValue("Entrada 3");
			Entrada3.setCellStyle(headerCell);
			
			HSSFCell Entrada4 = hssfRow.createCell(7);
			Entrada4.setCellValue("Entrada 4");
			Entrada4.setCellStyle(headerCell);
			
			HSSFCell Entrada5 = hssfRow.createCell(8);
			Entrada5.setCellValue("Entrada 5");
			Entrada5.setCellStyle(headerCell);
			
			int i = 1;
			int cont = 1;
			
			for (String list: registro) {
				
				String[] obj = list.split(",");
				System.out.println(obj);
				
				HSSFRow bodyRow = sheet.createRow(i);
				
				
				HSSFCellStyle bodyCell = workbook.createCellStyle();
				bodyCell.setFillBackgroundColor(HSSFColor.WHITE.index);
				
				HSSFCell numeroValor = bodyRow.createCell(0);
				numeroValor.setCellValue(cont);
				numeroValor.setCellStyle(headerCell);
				
				HSSFCell nomeValor = bodyRow.createCell(1);
				nomeValor.setCellValue(obj[0]);
				nomeValor.setCellStyle(bodyCell);
				
				HSSFCell cpfValor = bodyRow.createCell(2);
				cpfValor.setCellValue(obj[1]);
				cpfValor.setCellStyle(bodyCell);
				
				HSSFCell horarioEntradaValor = bodyRow.createCell(3);
				horarioEntradaValor.setCellValue(obj[2]);
				horarioEntradaValor.setCellStyle(bodyCell);
				
				HSSFCell Entrada1Valor = bodyRow.createCell(4);
				Entrada1Valor.setCellValue(obj[3]);
				Entrada1Valor.setCellStyle(bodyCell);
				
				int t = obj.length;
				
				if(t >= 5){
					HSSFCell Entrada2Valor = bodyRow.createCell(5);
					Entrada2Valor.setCellValue(obj[4]);
					Entrada2Valor.setCellStyle(bodyCell);
				}
				
				if(t >= 6){
					HSSFCell Entrada3Valor = bodyRow.createCell(6);
					Entrada3Valor.setCellValue(obj[5]);
					Entrada3Valor.setCellStyle(bodyCell);
				}
				
				if(t >= 7){
					HSSFCell Entrada4Valor = bodyRow.createCell(7);
					Entrada4Valor.setCellValue(obj[6]);
					Entrada4Valor.setCellStyle(bodyCell);
				}
				
				if(t >= 8){
					HSSFCell Entrada5Valor = bodyRow.createCell(8);
					Entrada5Valor.setCellValue(obj[7]);
					Entrada5Valor.setCellStyle(bodyCell);
				}
				
				i++;
				cont++;
			}
			
			workbook.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			return true;
			
			
		} catch (Exception e) {
			return false;
		}
		
		
	}

	public List<Registro> listarTodosMaior1(Integer numero) {
		return repository.listarTodosIgualAUmNumero(numero);
	}

	public List<String> listarTodosIgual(Integer numero) {
		return repository.listarTodosIgual(numero);
	}

}
