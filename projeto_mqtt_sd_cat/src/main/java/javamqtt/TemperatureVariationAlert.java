package javamqtt;

public class TemperatureVariationAlert {
    private MqttServices _mqtt;
    
    public TemperatureVariationAlert()
    {
        _mqtt = new MqttServices("tcp://localhost:1883", null, null);
        _mqtt.start();
    } 

    public void SendAlert(String message){
        _mqtt.publish("sensor/alert/temperatura-variando", message.getBytes(), 0);;
    }
}