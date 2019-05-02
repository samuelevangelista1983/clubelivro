package crvnluz.pessoas.entidade;

public enum Documento {
	
	CPF("cpf"),
	CNPJ("cnpj");
	
	private String nome;
	
	private Documento(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
}
