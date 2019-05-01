package crvnluz.boletos.mensageria;

import javax.jms.JMSException;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import crvnluz.boletos.service.ArquivoRemessaService;

@Component
public class ArquivoRemReceptor extends AbstractReceptor {
	
	private final Logger logger = LoggerFactory.getLogger(ArquivoRemReceptor.class);

	@Autowired
	private ArquivoRemessaService arquivoRemessaService;
	
	@Override
	protected void escreverMensagemErro(Throwable t) {
		logger.error("Falha ao receber o arquivo de remessa", t);
	}

	@Override
	protected void processarMensagem(ActiveMQTextMessage message) throws JMSException {
		String conteudoArquivo = message.getText();
		
		if (StringUtils.hasText(conteudoArquivo)) {
			arquivoRemessaService.processarArquivo(conteudoArquivo);
			
		} else {
			logger.warn("Arquivo de remessa vazio");
		}
	}
}
