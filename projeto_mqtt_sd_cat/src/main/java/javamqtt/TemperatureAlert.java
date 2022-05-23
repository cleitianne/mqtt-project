package javamqtt;

public class TemperatureAlert {
    private MqttServices _mqtt;
    
    public TemperatureAlert()
    {
        _mqtt = new MqttServices("tcp://localhost:1883", null, null);
        _mqtt.start();
    } 

    public void SendAlert(String message){
        System.out.println("ENVIANDO ALERTA!!");
        _mqtt.publish("sensor/alert/temperatura-alta", message.getBytes(), 0);;
    }
}
