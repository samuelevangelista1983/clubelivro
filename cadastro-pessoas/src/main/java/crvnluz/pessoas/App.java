package crvnluz.pessoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class App /*implements CommandLineRunner*/ {
/*
	@Autowired
	private PessoaService service;
	@Autowired
	private PessoaRepositorio repositorio;
	*/
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
/*
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Limpando a coleção de dados com o método deleteAll()");
		repositorio.deleteAll();
		
		Pessoa pessoaInicial = new Pessoa();
		pessoaInicial.setNome("Samuel");
		pessoaInicial.getAlteracoes().add(new Atualizacao(LocalDateTime.now(), "Teste0"));
		pessoaInicial.adicionarDocumento(new Informacao("CPF", "06140913659", "ADICIONAR"));
		pessoaInicial = service.adicionar(pessoaInicial);
		System.out.println("Pessoa adicionada inicialmente");
		System.out.println(pessoaInicial);
		Pessoa pessoaAdicionadaNovamente = new Pessoa(null, "Samuel Evangelista do Nascimento", LocalDate.of(1983, 10, 31));
		pessoaAdicionadaNovamente.getAlteracoes().add(new Atualizacao(LocalDateTime.now(), "Teste1"));
		pessoaAdicionadaNovamente.adicionarDocumento(new Informacao("CPF", "06140913659", "ADICIONAR"));
		pessoaAdicionadaNovamente.adicionarDocumento(new Informacao("RG", "MG 11779273", "ADICIONAR"));
		pessoaAdicionadaNovamente = service.adicionar(pessoaAdicionadaNovamente);
		System.out.println("Pessoa adicionada novamente");
		System.out.println(pessoaAdicionadaNovamente);
		Pessoa pessoaAdicionarTerceiraVez = new Pessoa(null, "Samuel Evangelista do Nascimento", LocalDate.of(1983, 10, 31));
		pessoaAdicionarTerceiraVez.getAlteracoes().add(new Atualizacao(LocalDateTime.now(), "Teste2"));
		pessoaAdicionarTerceiraVez.adicionarDocumento(new Informacao("CPF", "06140913659", "ADICIONAR"));
		pessoaAdicionarTerceiraVez.adicionarDocumento(new Informacao("RG", "MG 11779273", "ADICIONAR"));
		pessoaAdicionarTerceiraVez.adicionarContato(new Informacao("Telefone fixo", "03133638107", "ADICIONAR"));
		pessoaAdicionarTerceiraVez.adicionarContato(new Informacao("Telefone celular", "031992231028", "ADICIONAR"));
		pessoaAdicionarTerceiraVez.adicionarContato(new Informacao("e-mail", "samuelevangelista@gmail.com.br", "ADICIONAR"));
		pessoaAdicionarTerceiraVez = service.adicionar(pessoaAdicionarTerceiraVez);
		System.out.println("Pessoa adicionada pela terceira vez");
		System.out.println(pessoaAdicionarTerceiraVez);
		Pessoa pessoaAtualizada = new Pessoa(pessoaAdicionarTerceiraVez.getId(), "Samuel Evangelista do Nascimento", LocalDate.of(1983, 10, 31));
		pessoaAtualizada.getAlteracoes().add(new Atualizacao(LocalDateTime.now(), "Teste2"));
		pessoaAtualizada.adicionarDocumento(new Informacao("CPF", "06140913659", "ADICIONAR"));
		pessoaAtualizada.adicionarDocumento(new Informacao("RG", "MG 11779273", "ADICIONAR"));
		pessoaAtualizada.adicionarContato(new Informacao("Telefone fixo", "03133638107", "REMOVER"));
		pessoaAtualizada.adicionarContato(new Informacao("Telefone celular", "031992231028", "ADICIONAR"));
		pessoaAtualizada.adicionarContato(new Informacao("e-mail", "samuelevangelista@gmail.com", "ATUALIZAR"));
		Endereco endereco = new Endereco("ADICIONAR");
		endereco.setId("RESIDENCIAL");
		endereco.setCep("30840530");
		endereco.setLogradouro("Rua Elson Nunes de Souza");
		endereco.setNumero("321");
		endereco.setComplemento("apto 503");
		endereco.setBairro("Castelo");
		endereco.setCidade("Belo Horizonte");
		endereco.setUf("MG");
		pessoaAtualizada.adicionarEndereco(endereco);
		pessoaAtualizada = service.atualizar(pessoaAtualizada);
		System.out.println("Pessoa atualizada");
		System.out.println(pessoaAtualizada);
		Pessoa pessoaPesquisada = service.pesquisar(pessoaAtualizada.getId());
		System.out.println("Pessoa pesquisada pelo ID");
		System.out.println(pessoaPesquisada);
		pessoaPesquisada = service.pesquisarPorDocumento("RG", "MG 11779273");
		System.out.println("Pessoa pesquisada pelo documento RG");
		System.out.println(pessoaPesquisada);
		pessoaPesquisada = service.pesquisarPorDocumento("CPF", "06140913659");
		System.out.println("Pessoa pesquisada pelo documento CPF");
		System.out.println(pessoaPesquisada);
		List<Pessoa> pessoas = service.pesquisarPorNome("Samuel");
		System.out.println("Pessoa pesquisada pelo nome Samuel");
		
		for (Pessoa p: pessoas) {
			System.out.println(p);
		}
		
		pessoas = service.pesquisarPorNome("Evangelista");
		System.out.println("Pessoa pesquisada pelo nome Evangelista");
		
		for (Pessoa p: pessoas) {
			System.out.println(p);
		}

		pessoas = service.pesquisarPorNome("Nascimento");
		System.out.println("Pessoa pesquisada pelo nome Nascimento");
		
		for (Pessoa p: pessoas) {
			System.out.println(p);
		}
		
		System.out.println("Pessoa removida");
		service.remover(pessoaAtualizada);
		pessoas = repositorio.findAll();
		System.out.println("Pessoas encontradas " + pessoas.size());
	}
*/
}
