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
    private final static  String domain="domain5";
    private final static  String password="admin5";
    private final static String serverName="localhost";
    private  static int serverPort;
    private static int localPort;
    private final static int portsource=2000; // for the NAT protocol


    public static void main(String[] args) throws java.io.IOException{

      while (true){
          Scanner scanner=new Scanner(System.in);
          System.out.println("type the port corresponding to the domainName "+domain+ " : ");
          serverPort=scanner.nextInt();
          System.out.println("type the local port corresponding to the domainName "+domain+ " : ");
            localPort=scanner.nextInt();

          Client client=new Client(serverPort,InetAddress.getByName(serverName),password,domain);
          client.setlocalPort(localPort);
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
