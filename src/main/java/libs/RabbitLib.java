package libs;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class RabbitLib {

    private String host;
    private int port;
    private String userName;
    private String password;

    Channel channel = null;


    public RabbitLib(String pathConfig) {
        //Reading configuration file:
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream(new File(pathConfig));
            property.load(fis);

            this.host = property.getProperty("mq.host");
            this.port = Integer.parseInt(property.getProperty("mq.port"));
            this.userName = property.getProperty("mq.userName");
            this.password = property.getProperty("mq.pswd");

        } catch (IOException e) {
            System.err.println("Error: File not found.");
        }
    }

    //connection setup:
    public void open() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(this.userName);
        factory.setPassword(this.password);
        factory.setVirtualHost("/");
        factory.setHost(this.host);
        factory.setPort(port);

        Connection connection;
        try {
            connection = factory.newConnection();
            this.channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }


    public Producer producer() {
        return new Producer(this.channel);
    }


    public Subscriber subscriber() {
        return new Subscriber(this.channel);
    }
}
