package org.example;

import jssc.SerialPortException;
import org.example.clavier.In;

public class Main {
    public static void main(String[] args) throws InterruptedException, SerialPortException {
        System.out.println("Le numero de l'esclave svp ?");
        ModBus modbus = null;
        modbus= new ModBus(In.readByte());
        System.out.println("Le COM ?");
        try {
            modbus.connectEsclave(In.readString(),9600,0,0,1);
            System.out.println(modbus.lectureCoil(8192,2));
            modbus.lectureCoil(8192,2);

        }catch (SerialPortException e){
            throw new RuntimeException(e);
        }

    }
}