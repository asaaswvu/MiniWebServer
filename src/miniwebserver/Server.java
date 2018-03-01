/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniwebserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author baron
 */
public class Server {
    boolean alive = true;
    
    public Server(){
        try{
            ServerSocket ss = new ServerSocket(31416);
            while(alive){
                Socket sock = ss.accept();
                ClientHandler ch = new ClientHandler(sock);
                ch.start();
            }

        }catch(IOException ioe){
            System.out.println(ioe);
        }
    }
}
