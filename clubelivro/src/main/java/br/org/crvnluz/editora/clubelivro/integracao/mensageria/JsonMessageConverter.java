package br.org.crvnluz.editora.clubelivro.integracao.mensageria;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMessageConverter implements MessageConverter {

	private final Logger logger = LoggerFactory.getLogger(JsonMessageConverter.class);
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		return ((TextMessage) message).getText();
	}

	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		String json;

		try {
			json = mapper.writeValueAsString(object);
			
		} catch (Exception e) {
			MessageConversionException exception = new MessageConversionException("A mensagem nao pode ser parseada ", e);
			logger.error("Falha ao executar o parser da mensagem", exception);
			throw exception;
		}

		TextMessage message = session.createTextMessage();
		message.setText(json);
		return message;
	}

}
