package org.example;

import jssc.SerialPortException;
import org.example.clavier.In;

public class Main {
    public static void main(String[] args) throws InterruptedException, SerialPortException {
        System.out.println("Le numero de l'esclave svp ?");
        ModBus modbus ;
        modbus= new ModBus(In.readByte());
        System.out.println("Le COM ?");
        modbus.connecterEsclave(In.readString(),9600,0,0,1);
        try {
            while (true) {
                try {
                    float resultat = modbus.lectureCoils(8192, 2);
                        System.out.println("Résultat de la lecture : " + resultat);
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    }


