package br.org.crvnluz.editora.clubelivro.entidade.financeiro;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FluxoReceita implements Serializable {
	
	private static final long serialVersionUID = 832988869648233693L;
	
	private List<String> labels;
	private List<Double> valoresPrevistos;
	private List<Double> valoresRealizados;
	
	// CONSTRUTORES PÚBLICOS
	
	public FluxoReceita() {}
	
	// MÉTODOS PÚBLICOS
	
	public void preencherValores(List<Object[]> dados) {
		labels = new ArrayList<>(dados.size());
		valoresPrevistos = new ArrayList<>(dados.size());
		valoresRealizados = new ArrayList<>(dados.size());
		int ano = 0;
		
		for (Object[] fluxo: dados) {
			int anoFluxo = Integer.parseInt(fluxo[0].toString());
			String label = LocalDate.of(2018, Integer.parseInt(fluxo[1].toString()), 1).format(DateTimeFormatter.ofPattern("MMM", new Locale("pt", "BR")));
			label = new StringBuilder().append(Character.toUpperCase(label.charAt(0))).append(label.substring(1)).toString();
			
			if (ano != anoFluxo) {
				ano = anoFluxo;
				label = new StringBuilder(label).append(" - ").append(ano).toString();
			}
			
			labels.add(label);
			valoresPrevistos.add(Double.parseDouble(fluxo[2].toString()));
			valoresRealizados.add(Double.parseDouble(fluxo[3].toString()));
		}
	}

	public List<String> getLabels() {
		if (labels == null) {
			labels = new ArrayList<>();
		}
		
		return labels;
	}

	public List<Double> getValoresPrevistos() {
		if (valoresPrevistos == null) {
			valoresPrevistos = new ArrayList<>();
		}
		
		return valoresPrevistos;
	}

	public List<Double> getValoresRealizados() {
		if (valoresRealizados == null) {
			valoresRealizados = new ArrayList<>();
		}
		
		return valoresRealizados;
	}
	
}
