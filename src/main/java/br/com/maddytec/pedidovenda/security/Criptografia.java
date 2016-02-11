package br.com.maddytec.pedidovenda.security;  
  
import java.math.BigInteger;  
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  

public class Criptografia {  
  
    public static String md5(String senha) {  
        String senhaCriptograda = "";  
        MessageDigest md = null;  
        try {  
            md = MessageDigest.getInstance("MD5");  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
        senhaCriptograda = hash.toString(16);  
        return senhaCriptograda;  
          
      
    }  
}  