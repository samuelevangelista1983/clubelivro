package crvnluz.pessoas.entidade;

public enum Operacao {
	
	ADICIONAR("adicionar"),
	ATUALIZAR("atualizar"),
	REMOVER("remover");
	
	private String valor;
	
	private Operacao(String valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return valor;
	}
}
