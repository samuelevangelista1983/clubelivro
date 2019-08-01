package br.org.crvnluz.editora.clubelivro.entidade.financeiro;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InadimplenciaMensal implements Serializable {
	
	private static final long serialVersionUID = -5828843873501417426L;
	
	private List<String> labels;
	private List<Double> valores;
	
	// MÉTODOS PÚBLICOS
	
	public void preencherValores(List<Object[]> dados) {
		labels = new ArrayList<>(dados.size());
		valores = new ArrayList<>(dados.size());
		int ano = 0;
		
		for (Object[] item: dados) {
			int anoInadimplencia = Integer.parseInt(item[0].toString());
			String label = LocalDate.of(2018, Integer.parseInt(item[1].toString()), 1).format(DateTimeFormatter.ofPattern("MMM", new Locale("pt", "BR")));
			label = new StringBuilder().append(Character.toUpperCase(label.charAt(0))).append(label.substring(1)).toString();
			
			if (ano != anoInadimplencia) {
				ano = anoInadimplencia;
				label = new StringBuilder(label).append(" - ").append(ano).toString();
			}
			
			labels.add(label);
			valores.add(Double.parseDouble(item[2].toString()));
		}
	}
	
	public List<String> getLabels() {
		if (labels == null) {
			labels = new ArrayList<>();
		}
		
		return labels;
	}
	
	public List<Double> getValores() {
		if (valores == null) {
			valores = new ArrayList<>();
		}
		
		return valores;
	}
	
}
