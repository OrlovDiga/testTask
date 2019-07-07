package libs;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class Subscriber {

    private Channel channel;
    private static final String TYPE = "fanout";
    private static final boolean AUTO_ACK = true;


    Subscriber(Channel channel) {
        this.channel = channel;
    }


    public static String getTYPE() {
        return TYPE;
    }


    public static boolean isAutoAck() {
        return AUTO_ACK;
    }

    //Sending message
    public void acceptMsg(String exchangeName, DeliverCallback deliverCallback) throws IOException {
        String nameQueue = channel.queueDeclare().getQueue();
        channel.exchangeDeclare(exchangeName, "fanout");
        channel.queueBind(nameQueue, exchangeName, "");

        channel.basicConsume(nameQueue, AUTO_ACK, deliverCallback, consumerTag -> {});
    }
}
