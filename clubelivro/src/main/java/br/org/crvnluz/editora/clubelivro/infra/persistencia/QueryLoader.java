package br.org.crvnluz.editora.clubelivro.infra.persistencia;

import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

public class QueryLoader {
	
	public static String getConsulta(String arquivo) throws Exception {
		InputStream inputStream = new ClassPathResource("classpath:consultas/" + arquivo).getInputStream();
		return new String(FileCopyUtils.copyToByteArray(inputStream));
	}
	
}
