package javamqtt;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Subscriber implements IMqttMessageListener {
    String _messageAlert;
    private MqttService _mqttService;
    public Subscriber(String topic, int qos, String messageAlert) {
        _messageAlert = messageAlert;
        _mqttService = new MqttService("tcp://localhost:1883", null, null);
        _mqttService.start();
        _mqttService.subscribe(qos, this, topic);
    }

    public void messageArrived(String topico, MqttMessage mm) throws Exception {
        System.out.println("Received message:");
        System.out.println("\tTopic: " + topico);
        String message = new String(mm.getPayload());
        System.out.println(_messageAlert);
        System.out.println("\t A Temperatura é: " + message);
        
        System.out.println("");
    }

}
