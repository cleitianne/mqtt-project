package javamqtt;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Listener implements IMqttMessageListener {

    public Listener(TemperatureSensor clientMQTT, String topic, int qos) {
        clientMQTT.subscribe(qos, this, topic);
    }

    public void messageArrived(String topico, MqttMessage mm) throws Exception {
        System.out.println("Received message:");
        System.out.println("\tTopic: " + topico);
        System.out.println("\tconsummer messager: " + new String(mm.getPayload()));
        System.out.println("");
    }

}
