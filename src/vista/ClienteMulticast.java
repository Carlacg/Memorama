/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
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
        String IP = "228.5.6.1";
        int PUERTO = 7890;

        Socket socket = null;
        try {
            socket = new Socket(IP, PUERTO);
            socket.joinGroup(group);
            String mensaje = "";
            while (true) {
                byte[] buffer = new byte[1000];
                if (mensaje.equalsIgnoreCase("FIN")) {
                    break;
                }
                DatagramPacket mensajeEntrada = new DatagramPacket(buffer, buffer.length);
                socket.receive(mensajeEntrada);
                String msj = new String(mensajeEntrada.getData());
                System.out.println("Se recibió el mensaje: " + msj);
//                System.out.println(socket.getInetAddress());
                analizarMensaje(msj);
                mensajeEntrada = null;

            }
            socket.leaveGroup(group);

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
