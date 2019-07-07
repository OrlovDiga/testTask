package application;

import com.rabbitmq.client.DeliverCallback;
import libs.RabbitLib;
import libs.Subscriber;

public class ConsumerClient {

    private static final String NAME_EXCHANGE = "nameExchange";


    public static void main(String[] args) throws Exception {
        RabbitLib rabbit = new RabbitLib("src/main/resources/configSubscriber.properties");
        rabbit.open();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Receive --> : " + message);
        };

        Subscriber subscriber = rabbit.subscriber();
        subscriber.acceptMsg(NAME_EXCHANGE, deliverCallback);
        System.out.println("exit!"); //to check loop exit
    }
}
