package client;
import java.io.*;
import java.net.InetAddress;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainClient {
    /**
     * parametres sont:
     *  -Djavax.net.ssl.trustStore=keystore -Djavax.net.ssl.trustStorePassword=password
     */
    public static int compteur=0;

    // to be changed if you want
    private final static  String domain="domain2";
    private final static  String password="admin2";
    private final static String serverName="localhost";
    private final static int serverPort=8888;
    private final static int portsource=2000; // for the NAT protocol


    public static void main(String[] args) throws java.io.IOException{

      while (true){
          System.out.println("The password is "+password);
          Client client=new Client(serverPort,InetAddress.getByName(serverName),password,domain);
          client.initClient(domain);
          // sleep for 1 minute
          try {
              System.out.println("sleeping for 10 seconds");
              // total time of sleeping is 10 s
              TimeUnit.SECONDS.sleep(10);
              System.out.println("end of sleep"+compteur);
              compteur++;
          }catch (InterruptedException ex){
              ex.printStackTrace();
              break;
          }
      }




    }

}
