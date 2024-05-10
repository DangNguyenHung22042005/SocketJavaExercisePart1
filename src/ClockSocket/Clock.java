package ClockSocket;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Clock extends JFrame {
	private JLabel timeLabel;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Clock() {
        super("Clock");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        timeLabel = new JLabel("Time: ");
        add(timeLabel, BorderLayout.CENTER);
        setVisible(true);
        final String SERVER_ADDRESS = "localhost";
        final int PORT = 4321;

        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Timer timer = new Timer(1000, e -> {
                out.println("time");
                try {
                    String response = in.readLine();
                    timeLabel.setText("Time: " + response);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            timer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Clock();
    }
}
