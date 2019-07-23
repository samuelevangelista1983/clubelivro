package crvnluz.boletos.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import crvnluz.boletos.entidade.BoletoPendente;
import crvnluz.boletos.repositorio.BoletoPendenteRepositorio;

@Component
public class BoletosListener implements SessionAwareMessageListener<Message> {
	
	private final Logger logger = LoggerFactory.getLogger(BoletosListener.class);
	
	@Autowired
	private BoletoPendenteRepositorio repositorio;
	
	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		if (message instanceof ActiveMQTextMessage) {
			ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
			
			try {
				String conteudoJson = activeMQTextMessage.getText();
				
				if (StringUtils.hasText(conteudoJson)) {
					BoletoPendente pendente = new BoletoPendente();
					pendente.setJson(conteudoJson);
					repositorio.save(pendente);
					
				} else {
					logger.warn(String.format("Mensagem com ID %s está com o conteúdo JSON do boleto vazio", message.getJMSMessageID()));
				}
				
				activeMQTextMessage.acknowledge();
				session.commit();
				logger.info(String.format("Mensagem com ID %s recebida com sucesso", message.getJMSMessageID()));
				
			} catch (Throwable t) {
				logger.error(String.format("Falha ao receber a mensagem com ID %s", message.getJMSMessageID()), t);
				session.rollback();
			}
		}
	}

}
