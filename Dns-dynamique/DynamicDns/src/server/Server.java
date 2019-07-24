package server;
import client.MessageClient;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Thread{

    private ServerSocket serverSocket;
    private int port;

    public Server(int port) throws IOException {
        super();


        this.port=port;
        SSLServerSocketFactory sslServerSocketFactory =
                (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
        serverSocket =sslServerSocketFactory.createServerSocket(port);
        serverSocket.setSoTimeout(10000000);

    }

    public void run() {
        while (true) {
            try {

                ArrayList<String> commands=new ArrayList<>();
                // init the commands
                // I've put some basic commands
                initCommands(commands);

                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();

                System.out.println("Just connected to " + server.getRemoteSocketAddress());

                ObjectInputStream objectStream=new ObjectInputStream(server.getInputStream());
                MessageClient messageClient=(MessageClient)objectStream.readObject();

                String[] clientValues={messageClient.getDomain(),""+messageClient.getLastPort(),
                       ""+ messageClient.getPort(),messageClient.getLastIp().getHostAddress(),
                        messageClient.getIp().getHostAddress(),messageClient.getPassword()};


                // modifier ma base de donnée dans le cas d'un check=true
                Boolean check= CsvHandling.readCsv("src/data.csv",clientValues);
                if(check){
                    System.out.println("check is true\n");
                    // optional procedure
                    CsvHandling.deleteEmptyLines("src/data.csv");
                    // the client now will send now a hello message if the authentification is made
                    System.out.println("\n the communication is ok .... \n ");

                    DataOutputStream out = new DataOutputStream(server.getOutputStream());
                    out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
                        + "\nDo you want to add something\n");
                    /**
                     * sending to the client commands
                     */
                    receiveSimpleMessages(server,commands);

                    // deleting non important files
                    File file1=new File("src/data2.csv");
                    if(file1.exists()) file1.delete();
                    File file2=new File("src/myTempFile.csv");
                    if(file2.exists()) file2.delete();}
                    else {
                        System.out.println("check is false and the connexion is over ");
                        server.close();



                    }


            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    public void initCommands(ArrayList<String> commands) {
        for(int i=0;i<100;i++){
            commands.add("command"+i);
        }
    }

    // test test


    private void receiveSimpleMessages(Socket socket,ArrayList<String >commands) {



            System.out.println("SSL ServerSocket started");
            System.out.println("ServerSocket accepted");

            try {
                //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.flush();

                for (int i = 0; i < commands.size(); i++) {
                    System.out.println("I am in the loop for the " + i + "time");
                    out.writeUTF("command From THE SERVER :"+commands.get(i));
                    out.writeUTF("\n");

                }
                System.out.println("Closed");
                socket.close();


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
