package crvnluz.boletos.jms;

import javax.jms.Destination;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class JmsConfig {
	
	@Value("${activemq.broker.url}")
	private String brokerUrl ;
	@Value("${activemq.broker.username}")
	private String usuario; 
	@Value("${activemq.broker.password}")
	private String senha;
	@Value("${activemq.broker.topic.boletos}")
	private String topicoBoletos;
	@Value("${activemq.broker.client.id.prefix}")
	private String clientIdPrefix;
	@Autowired
	private BoletosListener boletosListener;
	
	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerUrl);
		connectionFactory.setPassword(usuario);
		connectionFactory.setUserName(senha);
		//connectionFactory.setWatchTopicAdvisories(false);
		connectionFactory.setClientID(clientIdPrefix);
		return connectionFactory;
	}
	
	@Bean
	public DefaultMessageListenerContainer getBoletosListener() {
		DefaultMessageListenerContainer dml = new DefaultMessageListenerContainer();
		dml.setConnectionFactory(connectionFactory());
		dml.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		dml.setSessionTransacted(true);
		Destination destino = new ActiveMQTopic(topicoBoletos);
		dml.setDestination(destino);
		dml.setDurableSubscriptionName(topicoBoletos);
		dml.setClientId(clientIdPrefix.concat(".").concat(topicoBoletos));
		dml.setMessageListener(boletosListener);
		dml.start();
		return dml;
	}
	
}
