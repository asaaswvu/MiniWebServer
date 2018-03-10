/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniwebserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author baron
 */
public class ClientHandler extends Thread {
    PrintWriter outWriter;
    BufferedReader inReader;
    Socket socket;
    String strMessage = "Jeffrey Moe<br>\n" +
        "<button onclick='alert(\"Someone is a gullibull.\")'>CLICK</button>";
    
    public ClientHandler(Socket sock){
        try{
            socket = sock;
            outWriter = new PrintWriter(sock.getOutputStream());
            inReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        }catch(IOException ioe){
            System.out.println(ioe);
        }
    }
    
    @Override
    public void run(){
        String inString;
        try{
            boolean notSent =true;
            System.out.println("----------");
            while(notSent && ((inString = inReader.readLine()) != null)){
                System.out.println(inString);
                if(inString.toLowerCase().contains("get /")){
                    outWriter.println("HTTP/1.1 200 OK");
                    outWriter.println("Content-Type: text/html; charset=UTF-8");
                    outWriter.println("Connection: Closed");
                    outWriter.println("");
                    outWriter.println("<html><head></head><body>" + strMessage + "</body></html>");
                    outWriter.flush();
                    System.out.println("Page Sent to " + socket.getInetAddress());  //IPv6
                    outWriter.close();
                    inReader.close();
                    
                    notSent = false;
                }
            }
            System.out.println("----------");
        }catch (IOException ioe){
            System.out.println(ioe);
        }
    }
}
