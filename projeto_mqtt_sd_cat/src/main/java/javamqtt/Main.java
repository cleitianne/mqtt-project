package javamqtt;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
	
    private final static int minTemp = 0;
    private final static int maxTemp = 2000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Bem vindo ao projeto CAT, para monitorar temperatura!!");
        MqttServices temperatureSensor = new MqttServices("tcp://localhost:1883", null, null);
        temperatureSensor.start();


        new Subscriber(temperatureSensor, "sensor/temperature/#", 0);

    }

}
