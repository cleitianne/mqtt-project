package javamqtt;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
	
    private final static int minTemp = 0;
    private final static int maxTemp = 2000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Bem vindo ao projeto de captura de temperatura e envio de informações!!");
        TemperatureSensor temperatureSensor = new TemperatureSensor("tcp://localhost:1883", null, null);
        temperatureSensor.start();

        while (true) {
            String temperatura = Integer.toString(ThreadLocalRandom.current().nextInt(minTemp, maxTemp + 1));
            System.out.println("Current Temperature: "+ temperatura);
            temperatureSensor.publish("sensor/temperature/caldeira", temperatura.getBytes(), 0);
            Thread.sleep(60000);
        }

    }

}
