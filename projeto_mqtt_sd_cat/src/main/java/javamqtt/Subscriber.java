package javamqtt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Subscriber implements IMqttMessageListener {

    public List<Double> Medias; 
    public MqttServices _clientMQTT;
    private TemperatureAlert _temperatureAlert;
    private TemperatureVariationAlert _temperatureVariationAlert;
    private List<Integer> _temperaturas;
    public Subscriber(MqttServices clientMQTT, String topic, int qos) {
        clientMQTT.subscribe(qos, this, topic);
        _clientMQTT = clientMQTT;
        _temperatureAlert = new TemperatureAlert();
        _temperatureVariationAlert = new TemperatureVariationAlert();
        _temperaturas = new ArrayList<Integer>();
    }

    public void messageArrived(String topico, MqttMessage mm) throws Exception {
        System.out.println("Received message:");
        System.out.println("\tTopic: " + topico);
        String message = new String(mm.getPayload());
        System.out.println("\t A Temperatura é: " + message);
        int temperatura = Integer.parseInt(message);
        //Essa é apenas uma solução para testes!! Em um ambiente real o ideal seria armazenar em um banco ou algo do tipo!!
        _temperaturas.add(temperatura);
        if(temperatura > 200) {
           _temperatureAlert = new TemperatureAlert();
           _temperatureAlert.SendAlert(message);
        }
        if(_temperaturas.size() >= 4){
            int size = _temperaturas.size();
            double mediaAtual = (_temperaturas.get(size-1) - _temperaturas.get(size-2))/2;
            double mediaAnterior = (_temperaturas.get(size-3) - _temperaturas.get(size-4))/2;
            if(Math.abs(mediaAtual - mediaAnterior) >= 5){
                _temperatureVariationAlert.SendAlert(message);
            }
        }
    }

}
