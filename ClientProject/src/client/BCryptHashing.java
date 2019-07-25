package client;

import java.security.SecureRandom;

public class BCryptHashing {

    public static String hash(String password){
        SecureRandom secureRandom=new SecureRandom();
        String salt=  "$2a$10$wBH5onUpAEzqhgBu3wvMT.";
        //System.out.println(salt);
        return BCrypt.hashpw(password,salt);
    }

    public static void main(String[] args) {
        String password="admin1";
        System.out.println(hash(password));

    }
}