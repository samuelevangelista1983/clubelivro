package br.org.crvnluz.editora.clubelivro.infra.persistencia;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QueryLoader {
	
	public static String getConsulta(String arquivo) throws Exception {
		Path path = Paths.get(QueryLoader.class.getClassLoader().getResource(arquivo).toURI());
		return new String(Files.readAllBytes(path));
	}
	
}
