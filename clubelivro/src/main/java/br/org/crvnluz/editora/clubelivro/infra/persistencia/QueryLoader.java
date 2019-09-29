package br.org.crvnluz.editora.clubelivro.infra.persistencia;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class QueryLoader {
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public String getConsulta(String arquivo) throws Exception {
		Resource resource = resourceLoader.getResource(String.format("classpath:consultas/%s", arquivo));
		InputStream inputStream = resource.getInputStream();
		return new String(FileCopyUtils.copyToByteArray(inputStream));
	}
	
}
