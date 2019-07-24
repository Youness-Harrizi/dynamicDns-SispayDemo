package client;

import java.io.Serializable;
import java.net.InetAddress;

public class MessageClient implements Serializable {
    private InetAddress lastIp;
    private InetAddress ip;

    public InetAddress getLastIp() {
        return lastIp;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getLastPort() {
        return lastPort;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public String getDomain() {
        return domain;
    }

    private int lastPort;
    private int port;
    private String password;
    private String domain;

    public MessageClient(String domain,InetAddress lastIp, InetAddress newIp , int lastPort, int newPort, String password)
    {
        this.lastIp=lastIp;
        this.ip=newIp;
        this.lastPort=lastPort;
        this.port=newPort;
        this.password=password;
        this.domain=domain;
    }
    public String toString()
    {
        return "domain :"+domain+" ip "+ip.getHostAddress()+"last ip :"+ lastIp.getHostAddress()
                + " port "+port+"lastPort :"+lastPort+ "and his password is :"+password;

    }

}
