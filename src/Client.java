import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by YapYap on 2016-01-03.
 */
public class Client extends Thread {

    private int port = 50000;
    private String host = "localhost";
    private Socket socket;
    //private PrintWriter outputStream;
    // private BufferedReader inputStream;
    private int JPose = 0;
    private  String moveCommand = "0";


    public Client() {


        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void run() {

        BufferedReader inputStream = null;
        try {
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();

        }

        System.out.println(receiveStringMessage(inputStream));
        sendStringMessage("Welcome!!!");
        System.out.println(receiveStringMessage(inputStream));


        while (true) {

            synchronized (this) {
                for (int i = 1; i < 7; i++) {

                    JPose++;
                    sendIntMessage(JPose);
                    System.out.println("Axis"+i+":"+JPose);

                }

                System.out.println(receiveStringMessage(inputStream));


            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (this) {
                moveCommand = receiveStringMessage(inputStream);
                System.out.println(moveCommand);
                Integer lol = Integer.parseInt(moveCommand);
                if (lol == 1) {

                    for (int i = 1; i < 7; i++) {

                        System.out.println("JESTEM WEWNTARZ "+ i);
                        System.out.println(receiveStringMessage(inputStream));

                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    sendStringMessage("Robot moved to JPOS");
                    //moveCommand="0";

                }


            }



        }


    }

    private String receiveStringMessage(BufferedReader inputStream) {

        String message = null;
        while (message == null) {
            try {
                message = inputStream.readLine();
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                ////////////ZAMKNIECIE CLIENTSOCKETA JEZELI BRAK ODPOWIEDZI

            }
        }
        return message;
    }

    private void sendStringMessage(String messsage) {


        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("blad");
            e.printStackTrace();
        }

        outputStream.println(messsage);
        outputStream.flush();
    }

    private void sendIntMessage(int message) {


        PrintWriter outputStream = null;
        try {
            outputStream = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        String strMessage = Integer.toString(message);
        outputStream.println(strMessage);
        outputStream.flush();
    }


}