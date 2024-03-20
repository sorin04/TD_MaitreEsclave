package ModBusRtu;

import java.util.Scanner;

public class CRC16 {
    static int calculCrc16(byte[]octects){
         int crc = 0xffff;
         for (int p=0;p<octects.length;p++) {
             crc ^= (octects [p] & 0xFF);
             for (int i = 0; i < 8; i++) {
                 if ((crc & 1) != 0) {
                     crc = (crc >> 1) ^ 0xA0001;
                 } else {
                     crc >>= 1;
                 }
             }
         }
         return crc;
     }




}