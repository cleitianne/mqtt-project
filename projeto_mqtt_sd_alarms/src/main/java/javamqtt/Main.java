package javamqtt;
public class Main {
    public static void main(String[] args) throws InterruptedException {
       
        System.out.println("Bem vindo ao projeto de exibição de altertas!!");

        new Subscriber("sensor/alert/temperatura-alta", 0, "ALERTA!! TEMPERATURA MUITO ELEVADA!!");

        new Subscriber("sensor/alert/temperatura-variando", 0, "ALERTA!! A TEMPERATURA ESTÁ VARIANDO MUITO!!");
    }

}
