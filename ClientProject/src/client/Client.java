package client;

import client.guiClient.TextingFrame;
import org.omg.CORBA.TRANSACTION_MODE;

import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client implements ClientInterface {
    private InetAddress lastIp;
    private InetAddress ip;
    private int lastPort;
    private int port;
    private String password;
    private  Socket client;
    private  HashMap<String,String> fileDomainMap;
    private Boolean bool=true;
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getBool()
    {
        return bool;
    }
    // the max numbre of clients the server can handle
    public static int nbreClientsMax =1000;

    private InetAddress serverAddress;


    // constructor

    public Client(int port, InetAddress serverAddress,String password,String domain) throws IOException {

        this.serverAddress = serverAddress;
        this.lastPort=port;
        this.port = port;
        this.password=password;



        fileDomainMap=new HashMap<>(0);
        for(int i = 1; i< nbreClientsMax +1; i++){
            fileDomainMap.put("domain"+i,"" +"clientLastIp"+i);
        }
        String path=fileDomainMap.get(domain);
        if(path==null)
        {
            this.bool=false;
        }
        else {
            this.setParameters("src/client/clients/"+path);
            //initClient(port, serverAddress, password, domain);
        }

    }



    public void initClient(int port, InetAddress serverAddress, String password, String domain) {
        // creating our ssl maker
        SSLSocketFactory sslSocketFactory =(SSLSocketFactory)SSLSocketFactory.getDefault();
        try {

            System.out.println("Connecting to " + serverAddress + " on port " + port);
            // ssl client
            try {
                // donner l'addresse du serveur 127.0.0.1
                client = sslSocketFactory.createSocket(serverAddress,port);
            }catch (Exception e){
                System.err.println("The server name that you've put isn't valid");
                System.exit(0);
            }

            MessageClient message = new MessageClient(domain, lastIp, ip, lastPort, port, password);
            sendAuthentificationMessage(client, message);

            // if connection is not finished send our normal message
             bool = sendMessage(client);
            // the lastIp should be the newIp if there was a change after that
            // on ne change l'ip que si on a recu un message du serveur
            if (setChanged() && bool) {
                System.out.println("last Ip has changed");
                System.out.println("The file :"+"src/client/clients/"+fileDomainMap.get(domain));
                changeLastIpFile("src/client/clients/"+fileDomainMap.get(domain));
            }
            if(bool){
                System.out.println("receiving some commands from THE SERVER LOCALHOST");
                if(client==null){
                    System.out.println("The socket is null!!!!!!");
                }
                else{
                    System.out.println("start receiving commands");
                    try {
                        receiveCommands(client);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }


                }

            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // receive commands and ACK them
    private void receiveCommands(Socket socket) throws InterruptedException {
        try {
           // BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataInputStream in = new DataInputStream(socket.getInputStream());

            String line="Pas encore";
            String bufferLine;
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            int compt=0;


            TimeUnit.SECONDS.sleep(10);

            try {
                while((line = in.readUTF()) != null) {
                    System.out.println("The command from the server is "+line);

                    System.out.println("I am in the loop for the "+compt +"time");
                    compt++;
                }

            }catch (EOFException ex){
                System.err.println("Fini les commandes");
            }

                    socket.close();



        }catch (IOException e){
            e.printStackTrace();
        }
    }
    // for THE GUI
    public  void sendSimpleMessages2(Socket socket, String text){
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    // sending the message to the server
                    out.println(text);
                    System.out.println(bufferedReader.readLine());

    }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void changeLastIpFile(String fileName) {
        try {
            File file=new File(fileName);
            if(file.exists()) System.out.println("the file exists.....\n");
            FileWriter fileWriter=new FileWriter(fileName,false);
            BufferedWriter bufferedWriter =new BufferedWriter(fileWriter);
            bufferedWriter.write(this.ip.getHostAddress());
            bufferedWriter.close(); // c'est necessaire (ou on fait du flush pour vider notre buffer)
            String newIp=this.ip.getHostAddress();
            System.out.println(newIp);



        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setParameters(String fileName) throws java.net.UnknownHostException{
        // to be geneeralized
        this.lastIp=readLastIp(fileName);
        System.out.println("Last IP is "+ lastIp);
        this.ip=InetAddress.getLocalHost();

    }

    private InetAddress readLastIp(String fileName) {
        // read the LastIp and then change it to the
        try {
            System.out.println("I am reading my Last IP...");
            String lastIp="";
            File file=new File(fileName);
            if (file.exists()) System.out.println("file does exist");
            else System.out.println("file does not exist");
            Scanner scanner=new Scanner(file);
            lastIp=scanner.next();

            return  InetAddress.getByName(lastIp);

        } catch (IOException e) {
            e.printStackTrace();
        }

    return null;


    }

    public boolean setChanged() {

        return (lastPort != port || lastIp != ip);
    }


    private void sendAuthentificationMessage(Socket client,MessageClient message) {
        // send the ClientMessage type
        try {
            OutputStream outToServer = client.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outToServer);
            out.writeObject(message);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    // returns true if we have received something from the server
    private Boolean sendMessage(Socket client) throws java.io.IOException{

        System.out.println("Just connected to " + client.getRemoteSocketAddress());
        OutputStream outToServer = client.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer);
        out.writeUTF("Hello from " + client.getLocalSocketAddress());
        InputStream inFromServer = client.getInputStream();
        DataInputStream in = new DataInputStream(inFromServer);
        if(in==null){
            System.out.println("The connection was broke");
            // close the socket in client side when it is closed in server side
            client.close();
            return false;
        }
        try {
            System.out.println("Server says " + in.readUTF());
            // test test
            //sendSimpleMessages(client);
            /**
             * The socket is temporarly not closed

             */
            //client.close();
        }catch(EOFException e){
            System.out.println("The connection is over ....\n retry again ....");
            return false;
        }catch (SocketException ex){
            System.out.println("The connection is over ....\n retry again ....");
            return false;
        }
        return true;

    }


}
