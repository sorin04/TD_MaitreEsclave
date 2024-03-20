package org.example;

import LiaisonSerie.LiaisonSerie;
import ModBusRtu.BigEndian;
import ModBusRtu.CRC16;
import jssc.SerialPortException;
import jssc.SerialPortException;


import java.util.Arrays;

public class ModBus extends LiaisonSerie {
    CRC16 crc16 = new CRC16();
    Byte numeroEsclave;
    byte [] resultatValeur = new byte[9];
    byte [] tramWithCRC16;
    LiaisonSerie liaisonSerie = new LiaisonSerie();

    public ModBus(Byte numeroEsclave) {
        this.numeroEsclave = numeroEsclave;
    }
    public ModeBus() {

    }
    public void fermerLiasonSerie() {
        super.fermerPort();
    }
    public byte[] intDeuxBytes(int number) {
        byte[] deuxBytes = new byte[2];
        deuxBytes[0] = (byte) ((number & 0xFF00) >> 8);
        deuxBytes[1] = (byte) (number & 0xFF);
        return deuxBytes;
    }
    public void connecterEsclave(String port, int vitesse, int donnees, int parite, int stop) throws SerialPortException {
        super.initCom(port);
        super.configurerParametres(vitesse, donnees, parite, stop);
    }

    public float lectureCoils(int register, int longueur) throws InterruptedException {
        byte [] adresse = intDeuxBytes(register);
        byte msbAdresse = adresse[0];
        byte lsbAdresse = adresse[1];

        byte [] taille = intDeuxBytes(longueur);
        byte msbLongueur = taille[0];
        byte lsbLongueur = taille[1];

        byte [] tramWithoutCRC16 = {numeroEsclave, 0x03, msbAdresse, lsbAdresse, msbLongueur, lsbLongueur};
        int crc16Int;
        crc16Int = crc16.calculCRC16(tramWithoutCRC16, crc16.getInitialValue(), crc16.getStdPoly());
        byte [] trameCrc16 = intDeuxBytes(crc16Int);
        byte msbCrc16 = trameCrc16[0];
        byte lsbCrc16 = trameCrc16[1];
        tramWithCRC16 = new byte[]{numeroEsclave, 0x03, msbAdresse, lsbAdresse, msbLongueur, lsbLongueur, lsbCrc16, msbCrc16};

        super.ecrire(tramWithCRC16);
        Thread.sleep(1000);
        if (detecteSiReception() == 9) {
            byte[] reponse = super.lireTrame(9);
            byte[] finalReponse = new byte[4];
            for (int i = 3; i < reponse.length - 2; i++) {
                finalReponse[i - 3] = reponse[i];
            }
            return BigEndian.fromArray(finalReponse);
        } else {
            return 0f;
        }
    }
}