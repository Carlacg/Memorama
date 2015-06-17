/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Broker;
import static vista.Panel.J1;
import static vista.Panel.J2;
import static vista.Panel.J3;
import static vista.Panel.J4;

/**
 *
 * @author david
 */
public class MemoramaServer extends Thread{
    private String miIp;
    private ArrayList<InetAddress> ipJugadores;

    public MemoramaServer(String miIp, ArrayList<InetAddress> ipJugadores) {
        this.miIp = miIp;
        this.ipJugadores = ipJugadores;
    }
    
    
    @Override
    public void run() {
        try {
            Broker broker = new Broker();
            InetAddress ip = InetAddress.getByName(miIp);
            ArrayList<Integer> puntuacion = new ArrayList();
            puntuacion.add(Integer.valueOf(J1.getText()));
            puntuacion.add(Integer.valueOf(J2.getText()));
            puntuacion.add(Integer.valueOf(J3.getText()));
            puntuacion.add(Integer.valueOf(J4.getText()));
            broker.setServer(ipJugadores, ip, Connection.getOrdenTarjetas(), puntuacion, Connection.getVolteadas(),miIp);
            broker.listen();
        } catch (UnknownHostException ex) {
            Logger.getLogger(MemoramaServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
