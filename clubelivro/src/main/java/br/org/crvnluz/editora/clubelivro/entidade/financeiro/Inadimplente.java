package br.org.crvnluz.editora.clubelivro.entidade.financeiro;

public class Inadimplente  {
	
	private static final long serialVersionUID = -7316189824845664282L;
	
	private String nomePessoa;
	private String nomeCategoria;
	private int qtdEmAtraso;
	
	// CONSTRUTORES PÚBLICOS
	
	public Inadimplente() {}
	
	public Inadimplente(String nomePessoa, String nomeCategoria, int qtdEmAtraso) {
		super();
		this.nomePessoa = nomePessoa;
		this.nomeCategoria = nomeCategoria;
		this.qtdEmAtraso = qtdEmAtraso;
	}
	
	// MÉTODOS PÚBLICOS
	
	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public int getQtdEmAtraso() {
		return qtdEmAtraso;
	}

	public void setQtdEmAtraso(int qtdEmAtraso) {
		this.qtdEmAtraso = qtdEmAtraso;
	}
	
}
