package crvnluz.pessoas.mensageria;

import org.springframework.stereotype.Component;

@Component
public class AtualizacaoPessoaRemetente {
	/*
	@Value("${activemq.broker.topic.cadastro.pessoas.atualizacao}")
	private String topicAtualizacaoPessoa;
	@Autowired
	private JmsTemplate jsmTemplate;
	*/
	public void escreverMensagem(Mensagem mensagem) {
		/*
		 *  TODO implementar assim que houver um outro programa alimentando o cadastro de pessoas.
		 *  Atualmente somente o Clube do Livro está integrado com o cadastro de pessoas.
		 */
		//jsmTemplate.convertAndSend(topicAtualizacaoPessoa, mensagem);
	}
	
}
