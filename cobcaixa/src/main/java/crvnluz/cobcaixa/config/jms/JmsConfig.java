package crvnluz.cobcaixa.config.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class JmsConfig {
	
	@Value("${activemq.broker.url}")
	private String brokerUrl ;
	@Value("${activemq.broker.username}")
	private String usuario = "admin"; 
	@Value("${activemq.broker.password}")
	private String senha = "admin";
	@Value("${activemq.broker.client.id.prefix}")
	private String clientIdPrefix;
	
	@Bean
	public ActiveMQConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerUrl);
		connectionFactory.setPassword(usuario);
		connectionFactory.setUserName(senha);
		connectionFactory.setWatchTopicAdvisories(false);
		connectionFactory.setClientID(clientIdPrefix);
		return connectionFactory;
	}
	
}