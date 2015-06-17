package vista;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ServidorTCP extends Thread {

    private boolean gameOver = false;

    public void run() {

        int PUERTO = 7890;
        DataInputStream mensajeEntrada = null;
        ServerSocket socketServidor = null;
        Socket socket = null;

        try {
            socketServidor = new ServerSocket(PUERTO);
            while (!gameOver) {
                socket = socketServidor.accept();
                mensajeEntrada = new DataInputStream(socket.getInputStream());
                String mensaje = mensajeEntrada.readUTF();
                System.out.println("Se recibió el mensaje: " + mensaje);
                analizarMensaje(mensaje);
                socket.close();
            }
            socketServidor.close();
            Panel.getInstance().iniciarJuego();
        } catch (UnknownHostException e) {
            System.out.println("UnknownHostException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
    }

    private void analizarMensaje(String mensaje) throws IOException {
        String[] parteDeMensaje = mensaje.split("\n");
        System.out.println("Tamaño: " + parteDeMensaje.length);
        String ip;
        switch (parteDeMensaje[0]) {
            case "movimiento":
                int index = Integer.parseInt(parteDeMensaje[1]);
                ip = parteDeMensaje[2];
                System.out.println("turno actual: " + ip);
                System.out.println("Mi turno: " + Panel.getInstance().getMiIp());
                Panel.getInstance().setMiTurno(Panel.getInstance().getMiIp().equals(ip));
                Panel.getInstance().voltearTarjeta(index);
                break;
            case "puntuacion":
                ip = parteDeMensaje[1];
                Panel.getInstance().setMiTurno(Panel.getInstance().getMiIp().equals(ip));
                System.out.println(Panel.getInstance().isMiTurno());
                establecerPuntuaciones(parteDeMensaje[2]);
                almacenarJugadores(parteDeMensaje[3]);
                break;
            case "fin":
                String ganador = parteDeMensaje[1];
                JOptionPane.showMessageDialog(null, "Ganó el jugador " + ganador);
                gameOver = true;
                break;
            case "cambio":
                String ipServidor = parteDeMensaje[1];
                Connection.setIpServidor(ipServidor);
                //Panel.getInstance().getConexion().setSocket(ipServidor);
                break;
        }
        if (Panel.getInstance().isMiTurno()) {
            Panel.getInstance().turnoLb.setText("Es tu turno");
        } else {
            Panel.getInstance().turnoLb.setText(" ");
        }

    }

    private void establecerPuntuaciones(String respuesta) {
        String[] puntuacion = respuesta.split(",");
        Panel.J1.setText(puntuacion[0]);
        Panel.J2.setText(puntuacion[1]);
        Panel.J3.setText(puntuacion[2]);
        Panel.J4.setText(puntuacion[3]);
    }

    private void almacenarJugadores(String jugadores) {
        try {
            Panel.getInstance().setIpJugadores(jugadores);
        } catch (IOException ex) {
            Logger.getLogger(ServidorTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
