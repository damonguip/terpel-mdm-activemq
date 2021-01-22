package com.terpel.mdm.controller;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
 
import org.apache.activemq.ActiveMQSslConnectionFactory;
/**
* A simple message producer which sends the message to ActiveMQ Broker
*
 * @author Mary.Zheng
*
*/
public class QueueMessageProducer {
    private String activeMqBrokerUri;
    private String username;
    private String password;
   
    public QueueMessageProducer(String activeMqBrokerUri, String username, String password) {
        super();
        this.activeMqBrokerUri = activeMqBrokerUri;
        this.username = username;
        this.password = password;
    }
    public void sendDummyMessages(String queueName) {
        System.out.println("QueueMessageProducer started " + this.activeMqBrokerUri);
        ActiveMQSslConnectionFactory connFactory = null;
        Connection connection = null;
        Session session = null;
        MessageProducer msgProducer = null;
        try {
            connFactory = new ActiveMQSslConnectionFactory(activeMqBrokerUri);
            connFactory.setTrustStore("clientCertificateBroker.ts");
            connFactory.setTrustStorePassword("changeit");
            connFactory.setUserName(username);
            connFactory.setPassword(password);
            connection = connFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            msgProducer = session.createProducer(session.createQueue(queueName));
            for (int i = 0; i < 10; i++) {
                TextMessage textMessage = session.createTextMessage(buildDummyMessage(i));
                msgProducer.send(textMessage);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
            System.out.println("QueueMessageProducer completed");
        } catch (JMSException e) {
            e.printStackTrace();
            System.out.println("Caught exception: " + e.getMessage());
        } catch (Exception e1) {
            System.out.println("Caught exception: " + e1.getMessage());
        }
        try {
            if (msgProducer != null) {
                msgProducer.close();
            }
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Throwable ignore) {
        }
    }
    private String buildDummyMessage(int value) {
        return "dummy message " + value;
    }
   
}