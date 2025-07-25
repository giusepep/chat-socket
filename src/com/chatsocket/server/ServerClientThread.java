package com.chatsocket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Set;

import com.chatsocket.message.Message;
import com.chatsocket.message.MessageType;
import com.chatsocket.screen.ServerInfoScreen;

public class ServerClientThread extends Thread {
  private Socket clientSocket;
  private Set<ObjectOutputStream> clientWriters;
  private ObjectInputStream input;
  private ObjectOutputStream output;
  private Message message;
  private ServerInfoScreen serverInfoScreen;

  public ServerClientThread(Socket clientSocket, Set<ObjectOutputStream> clientWriters,
      ServerInfoScreen serverInfoScreen) {
    this.clientSocket = clientSocket;
    this.clientWriters = clientWriters;
    this.serverInfoScreen = serverInfoScreen;
  }

  public void run() {
    try {
      this.output = new ObjectOutputStream(clientSocket.getOutputStream());
      this.output.flush();
      System.out.println("ServerClientThread.run() - 29");
      this.input = new ObjectInputStream(this.clientSocket.getInputStream());
      System.out.println("ServerClientThread.run() >> Thread created" + Thread.currentThread().getName());

      clientWriters.add(output);
      try {
        while (true) {
          System.out.println("Waiting for object...");
          this.message = (Message) this.input.readObject();
          System.out.println("Object received " + this.message.getMessage());

          switch (this.message.getType()) {
            case NORMAL:
              sendMessageReceived2OtherClients();
              break;
            case HELLO:
              registerUserScreenServer();
              break;
            default:
              sendMessageReceived2OtherClients();
              break;
          }
        }
      } catch (ClassNotFoundException e) {
        System.err.println("34 - Reading Object - Stacktrace: " + e.getMessage());
      }

    } catch (IOException e) {
      System.err.println("48 - Stacktrace: " + e.getMessage());
      try {
        clientSocket.close();

        if (output != null) {
          output.close();
          clientWriters.remove(output);
        }
      } catch (IOException ex) {
        System.err.println("57 - Stacktrace: " + e.getMessage());
      }
    }
  }

  public void sendMessageReceived2OtherClients() {
    // Broadcast message to all clients
    try {
      for (ObjectOutputStream objectOutputStream : clientWriters) {
        System.out.println("35 - printWriter " + message.getMessage());
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
      }
    } catch (IOException e) {
      System.out.println("ServerClientThread.sendMessageReceived2OtherClients() - 65 - Stacktrace: " + e.getMessage());
    }
  }

  public void registerUserScreenServer() {
    this.serverInfoScreen.addClient(this.message.getAuthor());
  }
}
