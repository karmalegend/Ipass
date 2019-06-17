package nl.hu.ipass.project.webservices;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import nl.hu.ipass.project.persistance.UserDoaPostgressImpl;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;
import java.util.Calendar;

@Path("/auth")
public class JwtAuth {
    final static public Key key = MacProvider.generateKey();


    /*This function provides an SHA-512 Hash
    * of any given password
    * this is done to secure the users password
    * and store it safely into the database*/

    
    public String get_SHA_512_SecurePassword(String passwordToHash){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString().toUpperCase();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }



    private String createToken(String username, String role) throws JwtException{
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE,30);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration.getTime())
                .claim("role",role)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }


    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) throws IllegalAccessException {
        try{
            UserDoaPostgressImpl userDao = new UserDoaPostgressImpl();
            String pwHash = get_SHA_512_SecurePassword(password);

            System.out.println(pwHash);
            System.out.println(username);

            String role = userDao.findRoleForUser(username,pwHash);

            if(role == null){ throw new IllegalAccessException("No user found!"); }

            String token = createToken(username,role);

            AbstractMap.SimpleEntry<String,String> JWT = new AbstractMap.SimpleEntry<String,String>("JWT",token);
            return Response.ok(JWT).build();


        }
        catch(JwtException | IllegalArgumentException e){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }
}
