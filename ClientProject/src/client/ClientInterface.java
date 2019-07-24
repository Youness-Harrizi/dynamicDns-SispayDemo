package client;

import java.net.InetAddress;
import java.net.Socket;

public interface ClientInterface {


    // this will set the new and the old ips
     void setParameters(String fileName) throws java.net.UnknownHostException;

    // this will return true if a certain parameter has been changed
     boolean setChanged();

    // main method for initialisation the communication with the server
     void initClient( String domain);




}
