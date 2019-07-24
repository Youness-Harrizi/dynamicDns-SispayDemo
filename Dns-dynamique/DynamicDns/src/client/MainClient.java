package client;
import java.io.*;
import java.net.InetAddress;
import java.util.Scanner;

public class MainClient {
    /**
     * parametres sont:
     *  -Djavax.net.ssl.trustStore=keystore -Djavax.net.ssl.trustStorePassword=password
     */
    public static void main(String[] args) throws java.io.IOException{

        Scanner scanner=new Scanner(System.in);
        String serverName; String password; String domain;
        System.out.println("Enter the server name\n");
        serverName=scanner.nextLine();
        System.out.println("Enter the domain name\n");
        domain=scanner.nextLine();
        System.out.println("Enter the password\n");
        password=scanner.nextLine();
        Console console = System.console();
        if(console==null) System.out.println("CONSOLE EST NULLE");
       // Client client=new Client(5555,serverName,password,domain);
        System.out.println(InetAddress.getLocalHost().getHostAddress());








    }

}
