/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david
 */
public class ClienteMulticast extends Thread {

    public void run() {
        
        int PUERTO = 7890;
        DataInputStream mensajeEntrada = null;
        ServerSocket socketServidor = null;
        Socket socket = null;
        
        try {
            socketServidor = new ServerSocket(PUERTO);
            while (true) {
                socket = socketServidor.accept();
                mensajeEntrada = new DataInputStream(socket.getInputStream());
                String mensaje = mensajeEntrada.readUTF();
                System.out.println("Se recibió el mensaje: " + mensaje);
                analizarMensaje(mensaje);
                socket.close();
            }

        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    private void analizarMensaje(String mensaje) {
        String[] respuesta = mensaje.split("\n");
        System.out.println("Tamaño: " + respuesta.length);
        if (respuesta.length == 3) {
            int index = Integer.parseInt(respuesta[0]);
            String ip = respuesta[1];
            System.out.println("turno actual: " + ip);
            System.out.println("Mi turno: " + Panel.getInstance().getMiIp());
            Panel.getInstance().setMiTurno(Panel.getInstance().getMiIp().equals(ip));
            Panel.getInstance().voltearTarjeta(index);
        } else if (respuesta.length == 2) {
            String ip = respuesta[0];
            Panel.getInstance().setMiTurno(Panel.getInstance().getMiIp().equals(ip));
            System.out.println(Panel.getInstance().isMiTurno());

        }

    }
}
