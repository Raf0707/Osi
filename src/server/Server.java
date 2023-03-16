package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    public static final int PORT = 3332;
    public static final int BUFFER_SIZE = 100;
    FileOutputStream location;
    FileOutputStream fos;
    Object o;

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket s = serverSocket.accept();
                saveFile(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveFile(Socket socket) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        if (o instanceof String) {
            File dir = new File("/home/user/");
            fos = new FileOutputStream(o.toString());
            location = new FileOutputStream(new File(dir,o.toString()));

        }
        byte [] buffer = new byte[BUFFER_SIZE];

        // 1. Read file name.
        Object o = ois.readObject();

        if (o instanceof String) {
            fos = new FileOutputStream(o.toString());
        } else {
            throwException("Something is wrong");
        }

        // 2. Read file to the end.
        Integer bytesRead = 0;

        do {
            o = ois.readObject();

            if (!(o instanceof Integer)) {
                throwException("Something is wrong");
            }

            bytesRead = (Integer)o;

            o = ois.readObject();

            if (!(o instanceof byte[])) {
                throwException("Something is wrong");
            }

            buffer = (byte[])o;

            // 3. Write data to output file.
            fos.write(buffer, 0, bytesRead);

        } while (bytesRead == BUFFER_SIZE);

        System.out.println("File transfer success");

        fos.close();

        ois.close();
        oos.close();
    }

    public static void throwException(String message) throws Exception {
        throw new Exception(message);
    }

    public static void main(String[] args) {
        new Server().start();
    }
}