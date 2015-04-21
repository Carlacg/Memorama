package vista;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Connection{

    private Socket socket;
    private static final int PORT = 7896;
    private static final ArrayList<Integer> ordenTarjetas= new ArrayList<>();
    
    public Connection() {
    }

    public ArrayList<Integer> saludarServer() {
        try {
            String ip = JOptionPane.showInputDialog("Ingrese la IP del servidor:");
            socket = new Socket(ip, PORT);
            
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("holi");
            
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String orden = in.readUTF();
            
            String[] tarjetas = orden.split("\n");
            for (String tarjeta : tarjetas) {
                ordenTarjetas.add(new Integer (tarjeta));
            }
        
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ordenTarjetas;
    }
    
    public Socket getSocket() {
        return socket;
    }
}
