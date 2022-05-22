package javamqtt;

import java.util.Arrays;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public class TemperatureSensor implements MqttCallbackExtended {

    private final String serverURI;
    private MqttClient mqttClient;
    private final MqttConnectOptions mqttConnectOptions;

    public TemperatureSensor(String serverURI, String user, String pwd) {
        this.serverURI = serverURI;

        mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setMaxInflight(200);
        mqttConnectOptions.setConnectionTimeout(3);
        mqttConnectOptions.setKeepAliveInterval(10);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);

        if (user != null && pwd != null) {
            mqttConnectOptions.setUserName(user);
            mqttConnectOptions.setPassword(pwd.toCharArray());
        }
    }

    public IMqttToken subscribe(int qos, IMqttMessageListener managementMensagemMQTT, String... topics) {
        if (mqttClient == null || topics.length == 0) {
            return null;
        }
        int length = topics.length;
        int[] qoss = new int[length];
        IMqttMessageListener[] listners = new IMqttMessageListener[length];

        for (int i = 0; i < length; i++) {
            qoss[i] = qos;
            listners[i] = managementMensagemMQTT;
        }
        try {
            return mqttClient.subscribeWithResponse(topics, qoss, listners);
        } catch (MqttException ex) {
            System.out.println(String.format("Error when subscribing to topics %s - %s", Arrays.asList(topics), ex));
            return null;
        }
    }

    public void unsubscribe(String... topics) {
        if (mqttClient == null || !mqttClient.isConnected() || topics.length == 0) {
            return;
        }
        try {
            mqttClient.unsubscribe(topics);
        } catch (MqttException ex) {
            System.out.println(String.format("Error when unsubscribing to topics %s - %s", Arrays.asList(topics), ex));
        }
    }

    public void start() {
        try {
            System.out.println("Connecting on MQTT broker in " + serverURI);
            mqttClient = new MqttClient(serverURI, String.format("cliente_java_%d", System.currentTimeMillis()), new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir")));
            mqttClient.setCallback(this);
            mqttClient.connect(mqttConnectOptions);
        } catch (MqttException ex) {
            System.out.println("Error when connecting on MQTT broken " + serverURI + " - " + ex);
        }
    }

    public void finish() {
        if (mqttClient == null || !mqttClient.isConnected()) {
            return;
        }
        try {
            mqttClient.disconnect();
            mqttClient.close();
        } catch (MqttException ex) {
            System.out.println("Error when disconnecting on MQTT broken - " + ex);
        }
    }

    public void publish(String topic, byte[] payload, int qos) {
        publish(topic, payload, qos, false);
    }

    public synchronized void publish(String topic, byte[] payload, int qos, boolean retained) {
        try {
            if (mqttClient.isConnected()) {
                mqttClient.publish(topic, payload, qos, retained);
                System.out.println(String.format("Topic %s published. %dB", topic, payload.length));
            } else {
                System.out.println("Client disconnected, could not publish topic " + topic);
            }
        } catch (MqttException ex) {
            System.out.println("Error when publishing " + topic + " - " + ex);
        }
    }

    
    public void connectionLost(Throwable thrwbl) {
        System.out.println("Broker connection lost -" + thrwbl);
    }

    
    public void connectComplete(boolean reconnect, String serverURI) {
        System.out.println("MQTT Client " + (reconnect ? "reconectado" : "conectado") + " com o broker " + serverURI);
    }

    
    public void deliveryComplete(IMqttDeliveryToken imdt) {
    }

    
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
    }

}
