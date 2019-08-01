package br.org.crvnluz.editora.clubelivro.entidade.financeiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InadimplenciaCategoria implements Serializable {
	
	private static final long serialVersionUID = 5986750504715305631L;
	
	private List<String> categorias;
	private List<Double> valores;
	
	// MÉTODOS PÚBLICOS
	
	public void preencherValores(List<Object[]> dados) {
		categorias = new ArrayList<>(dados.size());
		valores = new ArrayList<>(dados.size());
		
		for (Object[] item: dados) {
			categorias.add(item[0] != null ? item[0].toString() : null);
			valores.add(item[1] != null ? Double.valueOf(item[1].toString()) : null);
		}
	}
	
	public List<String> getCategorias() {
		if (categorias == null) {
			categorias = new ArrayList<>();
		}
		
		return categorias;
	}
	
	public List<Double> getValores() {
		if (valores == null) {
			valores = new ArrayList<>();
		}
		
		return valores;
	}
	
}
