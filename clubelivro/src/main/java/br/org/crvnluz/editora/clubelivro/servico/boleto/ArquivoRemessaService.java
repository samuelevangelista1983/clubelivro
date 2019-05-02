package br.org.crvnluz.editora.clubelivro.servico.boleto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.eti.sen.utilitarios.texto.StringUtil;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Categoria;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.FormaEntrega;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.FormaPgto;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.Frequencia;
import br.org.crvnluz.editora.clubelivro.entidade.configuracao.TipoContato;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Contato;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Endereco;
import br.org.crvnluz.editora.clubelivro.entidade.integrante.Integrante;
import br.org.crvnluz.editora.clubelivro.servico.IntegranteService;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.CategoriaService;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.FormaEntregaService;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.FormaPgtoService;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.FrequenciaService;
import br.org.crvnluz.editora.clubelivro.servico.configuracao.TipoContatoService;

@Service
public class ArquivoRemessaService {
	
	private final Logger logger = LoggerFactory.getLogger(ArquivoRemessaService.class);
	
	@Autowired
	private IntegranteService integranteService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private FormaEntregaService formaEntregaService;
	@Autowired
	private FormaPgtoService formaPgtoService;
	@Autowired
	private FrequenciaService frequenciaService;
	@Autowired
	private TipoContatoService tipoContatoService;
	
	private Integrante getIntegrante(String linha, FormaEntrega formaEntrega, FormaPgto formaPgto, Frequencia frequencia) {
		Integrante integrante = null;
		
		// Somente realiza a importação de pessoas físicas
		if (linha.charAt(17) == '1') {
			String cpf = linha.substring(22, 33);
			integrante = integranteService.pesquisarPorCPF(cpf);
			
			if (integrante == null) {
				integrante = new Integrante();
				integrante.setAtivo(true);
				integrante.setCadastro(LocalDate.now());
				integrante.setCpf(cpf);
				integrante.setFormaEntrega(formaEntrega);
				integrante.setFormaPgtoPref(formaPgto);
				integrante.setFrequencia(frequencia);
				integrante.setDiaVctoPreferencial(20);
			}
			
			integrante.setNome(linha.substring(33, 73).trim());
			String enderecoArquivo = linha.substring(73, 113).trim();
			String[] dadosEndereco = enderecoArquivo.split(" ");
			StringBuilder logradouro = new StringBuilder();
			StringBuilder complemento = new StringBuilder();
			String numero = null;
			
			for (String dado: dadosEndereco) {
				if (!StringUtil.contemDigito(dado) && numero == null) {
					logradouro.append(dado).append(StringUtil.ESPACO);
					
				} else if (StringUtil.contemDigito(dado) && numero == null) {
					numero = dado;
					
				} else {
					complemento.append(dado).append(StringUtil.ESPACO);
				}
			}
			
			Endereco endereco = new Endereco();
			endereco.setIntegrante(integrante);
			endereco.setLogradouro(logradouro.toString().trim());
			endereco.setNumero(numero != null ? numero.trim() : null);
			endereco.setComplemento(!complemento.toString().isEmpty() ? complemento.toString().trim() : null);
			endereco.setBairro(linha.substring(113, 128).trim());
			endereco.setCep(linha.substring(128, 136));
			endereco.setCidade(linha.substring(136, 151).trim());
			endereco.setUf(linha.substring(151, 153));
			
			if (integrante.getId() != null) {
				if (!integrante.getEnderecos().contains(endereco)) {
					endereco.setCobranca(true);
					endereco.setEntrega(true);
					integrante.adicionarEndereco(endereco);
				}
				
			} else {
				endereco.setCobranca(true);
				endereco.setEntrega(true);
				integrante.adicionarEndereco(endereco);
			}
		}
		
		return integrante;
	}
	
	private void preencherCategoria(Integrante integrante, String linha) {
		Categoria categoria = null;
		String mensagem = linha.substring(99, 140).trim();
		
		if (mensagem.contains("ROMANCE_ESTUDO") || mensagem.contains("romance_estudo")) {
			categoria = categoriaService.pesquisarporNome("estudo e romance");
			
		} else if (mensagem.contains("ALTERNADO") || mensagem.contains("alternado")) {
			categoria = categoriaService.pesquisarporNome("estudo e romance alternado");
			
		} else if (mensagem.contains("ROMANCE") || mensagem.contains("romance")) {
			categoria = categoriaService.pesquisarporNome("romance");
			
		} else if (mensagem.contains("ESTUDO") || mensagem.contains("estudo")) {
			categoria = categoriaService.pesquisarporNome("estudo");
			
		} else {
			categoria = categoriaService.pesquisarporNome("estudo");
		}
		
		integrante.setCategoria(categoria);
	}
	
	private void preencherContatos(Integrante integrante, String linha) {
		String contato = linha.substring(19, 69).trim();
		String[] dadosContato = contato.split(" ");
		String email = dadosContato[0].toLowerCase();
		
		if (Pattern.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", 
				email) && !email.endsWith(".combr")) {
			TipoContato tipo = tipoContatoService.pesquisarPorNome("e-mail");
			integrante.adicionarContato(new Contato(tipo, email));
		}
		
		if (dadosContato.length > 1 && StringUtil.valorNumerico(dadosContato[1])) {
			StringBuilder numero = new StringBuilder("31");
			numero.append(dadosContato[1]);
			
			if (Pattern.matches("[1-9]{2}[\\s]?[2-5]{1}[0-9]{3}[-]?[0-9]{4}", numero.toString())) {
				TipoContato tipo = tipoContatoService.pesquisarPorNome("Telefone fixo");
				integrante.adicionarContato(new Contato(tipo, numero.toString()));
			}
			
			if (Pattern.matches("[1-9]{2}[\\s]?9[0-9]{4}[-]?[0-9]{4}", numero.toString())) {
				TipoContato tipo = tipoContatoService.pesquisarPorNome("Telefone celular");
				integrante.adicionarContato(new Contato(tipo, numero.toString()));
			}
		}
	}
	
	// MÉTODOS PÚBLICOS
	
	public void processarArquivo(String arquivo) {
		logger.info("Processando arquivo de remessa para importação dos dados dos integrantes do Clube do Livro");
		String[] linhas = arquivo.split("\n");
		List<Integrante> integrantes = new ArrayList<>();
		Integrante integrante = null;
		FormaEntrega formaEntrega = formaEntregaService.pesquisarporNome("correios");
		FormaPgto formaPgto = formaPgtoService.pesquisarporNome("boleto bancário");
		Frequencia frequencia = frequenciaService.pesquisarporNome("mensal");
		
		for (String linha: linhas) {
			try {
				char tipoRegistro = linha.charAt(7);
				
				if (tipoRegistro == '3') {
					char segmento = linha.charAt(13);
					
					if (segmento == 'Q') {
						integrante = getIntegrante(linha, formaEntrega, formaPgto, frequencia);
						
						if (integrante != null) {
							integrantes.add(integrante);
						}
						
					} else if (segmento == 'R' && integrante != null) {
						preencherCategoria(integrante, linha);
						
					} else if (segmento == 'Y' && integrante != null) {
						preencherContatos(integrante, linha);
					}
				}
				
			} catch (Throwable throwable) {
				logger.error("Ocorreu um erro ao processar arquivo de remessa", throwable);
			}
		}
		
		integrantes.stream().forEach(i -> integranteService.salvar(i));
	}
	
}
