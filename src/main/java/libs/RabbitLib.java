package libs;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class RabbitLib {

    private Channel channel = null;
    private Connection connection = null;

    private String host;
    private int port;
    private String userName;
    private String password;


    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RabbitLib() {
    }

    public RabbitLib(String host, int port, String userName, String password) {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }


    public RabbitLib(String pathConfig) {
        //Reading configuration file:
        Properties property = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(pathConfig);
            property.load(inputStream);

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
        try  {
            connection = factory.newConnection();
            this.channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            this.close();
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.connection.close();
        } catch (IOException e) {
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
