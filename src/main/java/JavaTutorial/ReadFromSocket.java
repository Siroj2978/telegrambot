package JavaTutorial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadFromSocket {
    public void go() {
        Socket socket;
        String myPhone = "10.91.70.136";
        String myIP = "185.213.230.62";
        String IP = "127.0.0.1";
        {
            try {
                socket = new Socket(myIP, 5000);
                InputStreamReader stream = new InputStreamReader(socket.getInputStream());
                BufferedReader reader = new BufferedReader(stream);
                String message = reader.readLine();
                System.out.println(message);
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        ReadFromSocket readFromSocket = new ReadFromSocket();
        readFromSocket.go();
    }
}
