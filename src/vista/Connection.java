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
    private final ArrayList<Integer> volteadas = new ArrayList<>();
    private String miIp;
    private String jugador = "";
    private String turnoActual = "";

    public Connection() {
    }

    public ArrayList<Integer> saludarServer() {
        try {

            String ip = JOptionPane.showInputDialog("Ingrese la IP del servidor:");
            socket = new Socket(ip, PORT);
            miIp = socket.getLocalAddress().toString();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("holi");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            String lista = in.readUTF();
            String[] tarjetas = lista.split("@");
            String[] orden = tarjetas[0].split("\n");
            for (String tarjeta : orden) {
                ordenTarjetas.add(new Integer(tarjeta));
            }
            this.turnoActual = tarjetas[1];
//            boolean turno = turnoActual.equals(Panel.getInstance().getMiIp());
//            Panel.getInstance().setMiTurno(turno);
            this.jugador = tarjetas[2];
            if (tarjetas.length == 4) {
                String[] volteadas = tarjetas[3].split("\n");
                for (String volteada : volteadas) {
                    this.volteadas.add(new Integer(volteada));
                }
            }
            
            JOptionPane.showMessageDialog(null, "Bienvenido, eres el jugador "+ getJugador());
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordenTarjetas;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getMiIp() {
        return miIp;
    }

    public ArrayList<Integer> getVolteadas() {
        return volteadas;
    }

    public String getJugador() {
        return jugador;
    }

    public String getTurnoActual() {
        return turnoActual;
    }
    
    
}
