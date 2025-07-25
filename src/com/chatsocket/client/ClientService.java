package com.chatsocket.client;

import java.io.IOException;

import com.chatsocket.connection.SocketConnection;
import com.chatsocket.screen.ClientGUI;
import com.chatsocket.screen.SimpleScreen;
import com.chatsocket.message.Message;

public class ClientService {

  private SimpleScreen simpleScreen;

  public ClientService() {
    configAll();
  }

  public void configAll() {
    this.simpleScreen = new SimpleScreen();

    // Create Thread to receive messages from the server
    new IncomingMessageListener().start();
  }

  private class IncomingMessageListener extends Thread {
    @Override
    public void run() {
      try {
        while (true) {
          Message message = (Message) SocketConnection.getObjInputStream().readObject();
          System.out.println(">> " + message.getMessage());

          // call the method from the graphic screen
          simpleScreen.getClientGUI().appendToHistoricMessages(message, true);
        }
      } catch (ClassNotFoundException | IOException e) {
        System.err.println("ClientService - 35 - StackTrace: " + e.getMessage());
      }
    }
  }
}
