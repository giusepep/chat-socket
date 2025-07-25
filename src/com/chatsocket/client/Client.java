package com.chatsocket.client;

import com.chatsocket.connection.SocketConnection;

public class Client {
  public static void main(String[] args) {
    SocketConnection.getInstance();
    new ClientService();
  }
}
