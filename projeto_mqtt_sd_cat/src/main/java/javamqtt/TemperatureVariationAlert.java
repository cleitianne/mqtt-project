package javamqtt;

public class TemperatureVariationAlert {
    private MqttServices _mqtt;
    
    public TemperatureVariationAlert()
    {
        _mqtt = new MqttServices("tcp://localhost:1883", null, null);
        _mqtt.start();
    } 

    public void SendAlert(String message){
        System.out.println("ENVIANDO ALERTA!!");
        _mqtt.publish("sensor/alert/temperatura-variando", message.getBytes(), 0);;
    }
}