package crvnluz.boletos.mensageria;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import crvnluz.boletos.service.ArquivoRetornoService;

@Component
public class ArquivoRetReceptor extends AbstractReceptor {

	private final Logger logger = LoggerFactory.getLogger(ArquivoRetReceptor.class);
	
	@Autowired
	private ArquivoRetornoService arquivoRetornoService;
	
	@Override
	protected void escreverMensagemErro(Throwable t) {
		logger.error("Falha ao receber o arquivo de retorno", t);
	}

	@Override
	protected void processarMensagem(ActiveMQTextMessage message) throws JMSException {
		String conteudoArquivo = message.getText();
		
		if (StringUtils.hasText(conteudoArquivo)) {
			arquivoRetornoService.processarArquivo(conteudoArquivo);
			
		} else {
			logger.warn("Arquivo de retorno vazio");
		}
	}

	
}
