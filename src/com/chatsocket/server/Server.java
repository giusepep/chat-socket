package com.chatsocket.server;

import java.io.*;
import java.net.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.chatsocket.screen.ServerInfoScreen;
import com.chatsocket.connection.NetworkUtils;

public class Server {

  private static Set<ObjectOutputStream> clientWriters = ConcurrentHashMap.newKeySet();
  private ServerSocket serverSocket;
  private static ServerInfoScreen serverInfoScreen;

  public static void main(String[] args) {
    serverInfoScreen = new ServerInfoScreenImpl();
    new Server().runServer();
  }

  public void runServer() {
    try {
      Socket clientSocket;
      serverSocket = new ServerSocket(NetworkUtils.PORT);

      // 1 put in a loop to accept multiple clients connections
      // 2 put on a synchronized block because accept is not thread safer
      while (true) {
        synchronized (serverSocket) {
          clientSocket = serverSocket.accept();
          new ServerClientThread(clientSocket, clientWriters, serverInfoScreen).start();
        }
        System.out.println("Client connected!");
      }
    } catch (IOException e) {
      System.err.println("IOException: " + e.getMessage());
      System.exit(1);
    } finally {
      try {
        serverSocket.close();
      } catch (Exception e) {
        System.err.println("IOException: " + e.getMessage());
      }
    }
  }
}
