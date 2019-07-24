package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainServer {
    /**
     * Parametres sont :
     *  -Djavax.net.ssl.trustStore=keystore -Djavax.net.ssl.trustStorePassword=password
     *
     */
    // ports arbitraires
    private final static int[] ports={5555,8881,8882,8883,8884,8885,8886,8887,8888,8889};

    public static void main(String[] args) throws UnknownHostException {
        System.out.println("The Ip is "+ InetAddress.getLocalHost());
        for (int i = 0; i < 10; i++) {
            try {
                Thread thread = new Server(ports[i]);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}