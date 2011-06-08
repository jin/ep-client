/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package easyparkserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author AdminNUS
 */
public class displayThread extends Thread{

    private Socket clientSocket = null;
    private PrintWriter socketOut = null;
    private BufferedReader socketIn = null;

    public displayThread(Socket socket) {
        super("displayThread");
        this.clientSocket = socket;
    }

    public void run(){
        try {
            socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (Exception e) {
        }

    }//end run

}//end displayThread
