package application;

import libs.Producer;
import libs.RabbitLib;

import java.util.Random;

public class Publisher {

    private static final String NAME_EXCHANGE = "nameExchange";


    public static void main(String[] args) throws Exception {

        RabbitLib rabbit = new RabbitLib("configProducer.properties");
        rabbit.open();

        Random random = new Random();
        int numberOfWords = 2 + random.nextInt(100);
        String[] randomStrings = new String[numberOfWords];

        //random words algorithm:
        for (int i = 0; i < numberOfWords; i++) {
            char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)

            for(int j = 0; j < word.length; j++) {
                word[j] = (char)('a' + random.nextInt(26));
            }

            randomStrings[i] = new String(word);
        }
        //send messages:
        for (String randomString : randomStrings) {
            Producer producer = rabbit.producer();
            producer.PushMessage(NAME_EXCHANGE, randomString.getBytes("UTF-8"));
        }
    }
}
