package org.example;

import jssc.SerialPortException;
import org.example.clavier.In;

public class Main {
    public static void main(String[] args) throws InterruptedException, SerialPortException {
        System.out.println("Le numero de l'esclave svp ?");
        ModBus modbus ;
        modbus= new ModBus(In.readByte());
        System.out.println("Le COM ?");
        try {
            modbus.connecterEsclave(In.readString(),9600,0,0,1);

            ModBus finalModbus = modbus;

            Thread receptionThread = new Thread(() -> {
                while (true) {
                    try {

                        float resultat = finalModbus.lectureCoils(8192, 2);
                        System.out.println("Résultat de la lecture : " + resultat);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            receptionThread.start();


            receptionThread.join();
        } catch (SerialPortException e){
            throw new RuntimeException(e);
        }
    }
}
