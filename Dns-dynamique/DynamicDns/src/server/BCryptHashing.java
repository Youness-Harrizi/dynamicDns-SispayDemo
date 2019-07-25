package server;


import java.security.SecureRandom;

public class BCryptHashing {

    public static String hash(String password){
        SecureRandom secureRandom=new SecureRandom();
        String salt=  BCrypt.gensalt(10,secureRandom);
        return BCrypt.hashpw(password,salt);
    }

    public static void main(String[] args) {
        String password="admin5";
        System.out.println(hash(password));

    }
}