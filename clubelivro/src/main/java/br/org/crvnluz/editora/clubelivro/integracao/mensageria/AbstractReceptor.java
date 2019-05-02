package br.org.crvnluz.editora.clubelivro.integracao.mensageria;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.listener.SessionAwareMessageListener;

public abstract class AbstractReceptor implements SessionAwareMessageListener<Message> {
	
	protected abstract void escreverMensagemErro(Throwable t);
	
	protected abstract void processarMensagem(ActiveMQTextMessage activeMQTextMessage) throws JMSException;
	
	@Override
	public void onMessage(Message message, Session session) throws JMSException {
		if (message instanceof ActiveMQTextMessage) {
			ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
			
			try {
				processarMensagem(activeMQTextMessage);
				activeMQTextMessage.acknowledge();
				session.commit();
				
			} catch (Throwable t) {
				escreverMensagemErro(t);
				session.rollback();
			}
		}
	}

}
