package crvnluz.pessoas.entidade;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * MongoDB armazena os dados em coleções, dessa feita o Spring Data MongoDB irá mapear a classe Pessoa 
 * de modo à vinculá-la à uma coleção chamada pessoa (minúsculo mesmo).
 * Para mapear a classe à uma coleção de nome diferente basta marcar a classe com a anotação @Document 
 * do Spring Data MongoDB. 
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@TypeAlias("pessoa")
public class Pessoa implements Serializable {
	
	private static final long serialVersionUID = -5020813872383303890L;
	
	@Id
	private String id;
	private String nome;
	private LocalDate nascimento;
	private List<Informacao> contatos;
	private List<Informacao> documentos;
	private List<Endereco> enderecos;
	private List<Atualizacao> alteracoes; // usado para registrar os momentos e origens das alterações (quando e qual sistema alterou os dados)
	@Transient
	private Map<String, Informacao> mapContatos;
	@Transient
	private Map<String, Informacao> mapDocumentos;
	@Transient
	private Map<String, Endereco> mapEnderecos;
	
	public Pessoa() {
		contatos = new ArrayList<>();
		documentos = new ArrayList<>();
		enderecos = new ArrayList<>();
		mapContatos = new HashMap<>();
		mapDocumentos = new HashMap<>();
		mapEnderecos = new HashMap<>();
		alteracoes = new ArrayList<>();
	}

	public Pessoa(String id, String nome, LocalDate nascimento) {
		this();
		this.id = id;
		this.nome = nome;
		this.nascimento = nascimento;
	}
	
	public void adicionarContato(Informacao contato) {
		if (!mapContatos.containsKey(contato.getNome())) {
			contatos.add(contato);
			mapContatos.put(contato.getNome(), contato);
		}
	}
	
	public void adicionarDocumento(Informacao documento) {
		if (!mapDocumentos.containsKey(documento.getNome())) {
			documentos.add(documento);
			mapDocumentos.put(documento.getNome(), documento);
		}
	}
	
	public void adicionarEndereco(Endereco endereco) {
		if (!mapEnderecos.containsKey(endereco.getId())) {
			enderecos.add(endereco);
			mapEnderecos.put(endereco.getId(), endereco);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (alteracoes == null) {
			if (other.alteracoes != null)
				return false;
		} else if (!alteracoes.equals(other.alteracoes))
			return false;
		if (contatos == null) {
			if (other.contatos != null)
				return false;
		} else if (!contatos.equals(other.contatos))
			return false;
		if (documentos == null) {
			if (other.documentos != null)
				return false;
		} else if (!documentos.equals(other.documentos))
			return false;
		if (enderecos == null) {
			if (other.enderecos != null)
				return false;
		} else if (!enderecos.equals(other.enderecos))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nascimento == null) {
			if (other.nascimento != null)
				return false;
		} else if (!nascimento.equals(other.nascimento))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alteracoes == null) ? 0 : alteracoes.hashCode());
		result = prime * result + ((contatos == null) ? 0 : contatos.hashCode());
		result = prime * result + ((documentos == null) ? 0 : documentos.hashCode());
		result = prime * result + ((enderecos == null) ? 0 : enderecos.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nascimento == null) ? 0 : nascimento.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", nascimento=" + nascimento + ", contatos=" + contatos
				+ ", documentos=" + documentos + ", enderecos=" + enderecos + ", alteracoes=" + alteracoes + "]";
	}

	public static void validar(Pessoa pessoa) {
		if (pessoa == null) {
			throw new IllegalArgumentException("A pessoa não pode ser nula");
		}
		
		if (pessoa.alteracoes.isEmpty()) {
			throw new IllegalArgumentException("A lista de alterações dos dados da pessoa não pode ser vazia");
		}
		
		pessoa.alteracoes.stream().forEach(a -> Atualizacao.validar(a));
		
		if (!StringUtils.hasText(pessoa.nome)) {
			throw new IllegalArgumentException("O nome da pessoa não pode ser vazio");
		}
		
		if (pessoa.nascimento != null) {
			if (pessoa.nascimento.isAfter(LocalDate.now())) {
				throw new IllegalArgumentException("O nascimento da pessoa não pode ser uma data futura");
			}
		}
		
		pessoa.contatos.stream().forEach(contato -> Informacao.validar(contato));
		
		if (pessoa.documentos.isEmpty()) {
			throw new IllegalArgumentException("A pessoa não pode ser cadastrada sem documentos");
		}
		
		pessoa.documentos.stream().forEach(documento -> Informacao.validar(documento));
		pessoa.enderecos.stream().forEach(endereco -> Endereco.validar(endereco));
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public List<Informacao> getContatos() {
		return contatos;
	}

	public void setContatos(List<Informacao> contatos) {
		this.contatos = contatos;
		mapContatos = new HashMap<>();
		this.contatos.stream().forEach(contato -> mapContatos.put(contato.getNome(), contato));
	}

	public List<Informacao> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<Informacao> documentos) {
		this.documentos = documentos;
		mapDocumentos = new HashMap<>();
		this.documentos.stream().forEach(documento -> mapDocumentos.put(documento.getNome(), documento));
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
		mapEnderecos = new HashMap<>();
		this.enderecos.stream().forEach(endereco -> mapEnderecos.put(endereco.getId(), endereco));
	}

	public Map<String, Informacao> getMapContatos() {
		if (mapContatos.isEmpty()) {
			contatos.stream().forEach(contato -> mapContatos.put(contato.getNome(), contato));
		}
		
		return mapContatos;
	}

	public Map<String, Informacao> getMapDocumentos() {
		if (mapDocumentos.isEmpty()) {
			documentos.stream().forEach(documento -> mapDocumentos.put(documento.getNome(), documento));
		}
		
		return mapDocumentos;
	}

	public Map<String, Endereco> getMapEnderecos() {
		if (mapEnderecos.isEmpty()) {
			enderecos.stream().forEach(endereco -> mapEnderecos.put(endereco.getId(), endereco));
		}
		
		return mapEnderecos;
	}

	public List<Atualizacao> getAlteracoes() {
		return alteracoes;
	}

	public void setAlteracoes(List<Atualizacao> alteracoes) {
		this.alteracoes = alteracoes;
	}
	
}
