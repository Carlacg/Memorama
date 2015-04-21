package vista;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Connection extends Thread {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream in;
    private final int PORT = 7891;

    public Connection() {
        //this.start();
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
//            while (true) {
            clientSocket = null;
//                clientSocket.getLocalPort();
            clientSocket = serverSocket.accept();
            in = new DataInputStream(clientSocket.getInputStream());
            String cadena = in.readUTF();
            JOptionPane.showMessageDialog(null, cadena);
            clientSocket.close();
            serverSocket.close();
//            }
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
