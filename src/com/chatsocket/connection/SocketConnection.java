package com.chatsocket.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketConnection {
  // Singleton pattern to handle connection with server
  private static SocketConnection instance;
  private static final String SERVERADDRESS = "localhost";
  private static final int PORT = 12345;
  private Socket socket;
  private static ObjectInputStream input;
  private static ObjectOutputStream output;

  public SocketConnection() {
    if (instance != null) {
      throw new RuntimeException("Use getInstance()");
    }
    try {
      System.out.println("SocketConnection.SocketConnection() - 22 ");
      this.socket = new Socket(SERVERADDRESS, PORT);
      System.out.println("SocketConnection.SocketConnection() - 24");
      output = new ObjectOutputStream(socket.getOutputStream());
      output.flush();
      System.out.println("SocketConnection.SocketConnection() - 27");
      input = new ObjectInputStream(socket.getInputStream());
      System.out.println("SocketConnection.SocketConnection() - 29");

    } catch (IOException ex) {
      System.err.println("27 - StackTrace: " + ex.getMessage());
      closeSocket();
      closeOutput();
      closeInput();
    }
  }

  public static SocketConnection getInstance() {
    if (instance == null) {
      instance = new SocketConnection();
    }

    return instance;
  }

  public Socket getSocket() {
    return this.socket;
  }

  public static ObjectInputStream getObjInputStream() {
    return input;
  }

  public static ObjectOutputStream getObjOutputStream() {
    return output;
  }

  private void closeSocket() {
    try {
      if (socket != null) {
        socket.close();
      }
    } catch (IOException ex) {
      System.err.println("51 - StackTrace: " + ex.getMessage());
    }
  }

  private void closeOutput() {
    try {
      if (output != null) {
        output.close();
      }
    } catch (Exception ex) {
      System.err.println("60 - StackTrace: " + ex.getMessage());
    }
  }

  private void closeInput() {
    try {
      if (input != null) {
        input.close();
      }
    } catch (IOException ex) {
      System.err.println("80 - StackTrace: " + ex.getMessage());
    }
  }
}
