package br.org.crvnluz.editora.clubelivro.integracao.mensageria;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

import br.org.crvnluz.editora.clubelivro.integracao.mensageria.boleto.ArquivoRemReceptor;

@Configuration
@Component
public class JmsConfig {
	
	@Value("${activemq.broker.url}")
	private String brokerUrl ;
	@Value("${activemq.broker.username}")
	private String usuario = "admin"; 
	@Value("${activemq.broker.password}")
	private String senha = "admin";
	@Value("${activemq.broker.topic.arquivos.rem}")
	private String topicoArquivosRem;
	@Value("${activemq.broker.client.id.prefix}")
	private String clientIdPrefix;
	@Autowired
	private ArquivoRemReceptor arquivoRemReceptor;
	
	@Bean 
	public Connection connection() throws JMSException{
		ActiveMQConnection connection = (ActiveMQConnection) connectionFactory().createConnection();
		connection.setClientID(clientIdPrefix);
		connection.start();
		return connection;
	}
	
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
	
	@Bean
	public DefaultMessageListenerContainer getArquivosRemessaListener() {
		DefaultMessageListenerContainer dml = new DefaultMessageListenerContainer();
		dml.setConnectionFactory(connectionFactory());
		dml.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		dml.setSessionTransacted(true);
		Destination destino = new ActiveMQTopic(topicoArquivosRem);
		dml.setDestination(destino);
		dml.setDurableSubscriptionName(topicoArquivosRem);
		dml.setClientId(clientIdPrefix.concat(".").concat(topicoArquivosRem));
		dml.setMessageListener(arquivoRemReceptor);
		dml.start();
		return dml;
	}
	
}