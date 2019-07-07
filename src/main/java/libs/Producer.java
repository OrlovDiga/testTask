package libs;

import com.rabbitmq.client.Channel;

import java.io.IOException;

public class Producer {

    private Channel channel;

    private boolean autoDelete = false;
    private boolean durable = false;
    private static final String TYPE = "fanout";


    public static String getTYPE() {
        return TYPE;
    }


    public boolean isAutoDelete() {
        return autoDelete;
    }


    public void setAutoDelete(boolean autoDelete) {
        this.autoDelete = autoDelete;
    }


    public boolean isDurable() {
        return durable;
    }


    public void setDurable(boolean durable) {
        this.durable = durable;
    }


    Producer(Channel channel) {
        this.channel = channel;
    }


    public void PushMessage(String exchange, byte[] data) throws IOException {
        channel.exchangeDeclare(exchange, TYPE, false, autoDelete ,null);
        channel.basicPublish(exchange, "", null, data);
    }
}
