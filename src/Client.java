import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by YapYap on 2016-01-03.
 */
public class Client extends  Thread{

    private int port= 50000;
    private String host = "localhost";
    private Socket socket;
    //private PrintWriter outputStream;
   // private BufferedReader inputStream;
    private int JPose;


    public Client(){

        JPose = -1;

        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    public void run(){



        System.out.println("Zaczyna odbierac");
        String lol = receiveStringMessage();
        System.out.println("Odebrał");
        System.out.println(lol);
        sendStringMessage("Welcome!!!");
        System.out.println("HOHO");

        while(true){

            JPose = -(JPose+5);



            for(int i =1; i<=6; i++){

                JPose++;
                sendIntMessage(JPose);;


            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }





    }

    private String receiveStringMessage() {



        BufferedReader inputStream = null;
        try {
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        String message=null;
        try {
            message = inputStream.readLine();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return message;
    }

    private void sendStringMessage(String messsage) {


        PrintWriter outputStream=null;
        try {
            outputStream = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputStream.println(messsage);
    }

    private void sendIntMessage(int message) {


        PrintWriter outputStream=null;
        try {
            outputStream = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        String strMessage = Integer.toString(message);
        outputStream.println(strMessage);
    }










}
