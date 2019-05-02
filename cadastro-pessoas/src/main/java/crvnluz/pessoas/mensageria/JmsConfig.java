package crvnluz.pessoas.mensageria;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
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
	@Value("${activemq.broker.queue.cadastro.pessoas}")
	private String queueCadastroPessoas;
	//@Value("${activemq.broker.topic.cadastro.pessoas.atualizacao}")
	//private String topicoAtualizacao;
	@Value("${activemq.broker.client.id.prefix}")
	private String clientIdPrefix;
	@Autowired
	private CadastroPessoaReceptor leitor;
	
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
		List<String> trustedPackages = new ArrayList<>();
		trustedPackages.add("java.lang");
		trustedPackages.add("java.time");
		trustedPackages.add("java.util");
		trustedPackages.add("crvnluz.pessoas.mensageria");
		trustedPackages.add("crvnluz.pessoas.entidade");
		connectionFactory.setTrustedPackages(trustedPackages);
		return connectionFactory;
	}
	
	@Bean
	public DefaultMessageListenerContainer getCadastroPessoasListener() {
		DefaultMessageListenerContainer dml = new DefaultMessageListenerContainer();
		dml.setConnectionFactory(connectionFactory());
		dml.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		dml.setSessionTransacted(true);
		dml.setDestination(new ActiveMQQueue(queueCadastroPessoas));
		dml.setDurableSubscriptionName(queueCadastroPessoas);
		dml.setClientId(clientIdPrefix.concat(".").concat(queueCadastroPessoas));
		dml.setMessageListener(leitor);
		dml.start();
		return dml;
	}
	/*
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
	*/
}
