package vista;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Connection {

    private Socket socket;
    private static final int PORT = 7896;
    private static final ArrayList<Integer> ordenTarjetas = new ArrayList<>();
    private static final ArrayList<Integer> volteadas = new ArrayList<>();
    private String miIp;
    private String jugador = "";
    private String turnoActual = "";

    public Connection() {
    }

    public ArrayList<Integer> saludarServer() {
        try {
            volteadas.clear();
            ordenTarjetas.clear();
            establecerConexion();
            String mensaje = recibirMensaje();
            analizarMensaje(mensaje);
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordenTarjetas;
    }

    private void establecerConexion() throws IOException {
        String ip = JOptionPane.showInputDialog("Ingrese la IP del servidor:");
        socket = new Socket(ip, PORT);
        miIp = socket.getLocalAddress().toString();
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF("holi");
    }

    private String recibirMensaje() throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        String lista = in.readUTF();
        return lista;
    }

    private void analizarMensaje(String mensaje) {
        String lista = mensaje;
        String[] elementos = lista.split("@");
        if (elementos.length == 1) {
            JOptionPane.showMessageDialog(null, "Límite de jugadores alcanzado");
            ordenTarjetas.clear();
        } else {
            establecerOrdenTarjetas(elementos[0]);
            this.turnoActual = elementos[1];
            this.jugador = elementos[2];
            boolean juegoComenzado = elementos.length == 4;
            if (juegoComenzado) {
                establecerTarjetasVolteadas(elementos[3]);
            }
            JOptionPane.showMessageDialog(null, "Bienvenido, eres el jugador " + getJugador());
        }
    }

    private void establecerOrdenTarjetas(String elemento) {
        String[] orden = elemento.split("\n");
        for (String tarjeta : orden) {
            ordenTarjetas.add(new Integer(tarjeta));
        }
    }

    private void establecerTarjetasVolteadas(String elemento) {
        String[] volteadas = elemento.split("\n");
        for (String volteada : volteadas) {
            this.volteadas.add(new Integer(volteada));
        }
    }

    public void setSocket(String ip) {
        try {
            this.socket.close();
            this.socket = new Socket(ip,PORT);
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Socket getSocket() {
        return socket;
    }

    public String getMiIp() {
        return miIp;
    }

    public static ArrayList<Integer> getVolteadas() {
        return volteadas;
    }

    public static ArrayList<Integer> getOrdenTarjetas() {
        return ordenTarjetas;
    }
    
    public String getJugador() {
        return jugador;
    }

    public String getTurnoActual() {
        return turnoActual;
    }

}
