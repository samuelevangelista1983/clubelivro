package crvnluz.boletos.mensageria;

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
	private String usuario = "admin"; 
	@Value("${activemq.broker.password}")
	private String senha = "admin";
	@Value("${activemq.broker.topic.arquivos.rem}")
	private String topicoArquivosRem;
	@Value("${activemq.broker.topic.arquivos.ret}")
	private String topicoArquivosRet;
	@Value("${activemq.broker.client.id.prefix}")
	private String clientIdPrefix;
	@Autowired
	private ArquivoRemReceptor arquivoRemReceptor;
	@Autowired
	private ArquivoRetReceptor arquivoRetReceptor;
	/*
	@Bean 
	public Connection connection() throws JMSException{
		ActiveMQConnection connection = (ActiveMQConnection) connectionFactory().createConnection();
		connection.setClientID(clientIdPrefix);
		connection.start();
		return connection;
	}
	*/
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
	
	@Bean
	public DefaultMessageListenerContainer getArquivosRetornoListener() {
		DefaultMessageListenerContainer dml = new DefaultMessageListenerContainer();
		dml.setConnectionFactory(connectionFactory());
		dml.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		dml.setSessionTransacted(true);
		
		Destination destino = new ActiveMQTopic(topicoArquivosRet);
		dml.setDestination(destino);
		dml.setDurableSubscriptionName(topicoArquivosRet);
		dml.setClientId(clientIdPrefix.concat(".").concat(topicoArquivosRet));
		dml.setMessageListener(arquivoRetReceptor);
		dml.start();
		return dml;
	}
}
