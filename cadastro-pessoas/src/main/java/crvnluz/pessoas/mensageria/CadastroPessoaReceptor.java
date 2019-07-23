package crvnluz.pessoas.mensageria;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import crvnluz.pessoas.entidade.Pessoa;
import crvnluz.pessoas.service.PessoaService;

@Component
public class CadastroPessoaReceptor implements SessionAwareMessageListener<Message> {
	
	private final Logger logger = LoggerFactory.getLogger(CadastroPessoaReceptor.class);
	
	@Autowired
	private AtualizacaoPessoaRemetente escritor;
	@Autowired
	private PessoaService service;
	
	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		if (message instanceof ActiveMQObjectMessage) {
			ActiveMQObjectMessage activeMQObjectMessage = (ActiveMQObjectMessage) message;
			Mensagem mensagem = (Mensagem) activeMQObjectMessage.getObject();
			
			if (mensagem != null) {
				Integer operacao = mensagem.getOperacao();
				Pessoa pessoa = mensagem.getPessoa();
				
				if (operacao != null && pessoa != null) {
					if (operacao > -1 && operacao < 4) {
						try {
							Mensagem msgRetorno = new Mensagem();
							msgRetorno.setOperacao(operacao);
							
							if (operacao.equals(Mensagem.ADICIONAR)) {
								// Implementa a adição de um novo registro no cadastro
								Pessoa pessoaAdicionada = service.adicionar(pessoa);
								msgRetorno.setPessoa(pessoaAdicionada);
								logger.info("Pessoa adicionada");
								
							} else if (operacao.equals(Mensagem.ATUALIZAR)) {
								// Implementa a atualização de um registro do cadastro
								Pessoa pessoaAtualizada = service.atualizar(pessoa);
								msgRetorno.setPessoa(pessoaAtualizada);
								logger.info("Pessoa atualizada");
								
							} else if (operacao.equals(Mensagem.REMOVER)) {
								// Implementa a lógica de remoção de um registro no cadastro
								service.remover(pessoa);
								msgRetorno.setPessoa(pessoa);
								logger.info("Pessoa removida");
							}
							
							escritor.escreverMensagem(msgRetorno);
							
						} catch (IllegalArgumentException ex) {
							logger.error("Falha na validação de argumentos", ex);
						}
						
					} else {
						logger.warn(String.format("Mensagem com atributo operação inválida. Operação recebida: %s", operacao));
					}
					
				} else {
					logger.warn(String.format("Mensagem inválida. %s", mensagem.toString()));
				}
			}
		}
	}
	
}
