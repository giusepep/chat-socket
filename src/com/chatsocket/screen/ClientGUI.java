package com.chatsocket.screen;

import com.chatsocket.connection.SocketConnection;
import com.chatsocket.message.Message;
import com.chatsocket.message.MessageType;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ClientGUI extends JFrame {
  private JTextPane historicMessagesArea;
  private JTextField inputField;
  private JButton submitButton;
  private String nameClient;
  private StyledDocument doc;
  private SimpleAttributeSet normal;
  private SimpleAttributeSet sentColored;
  private SimpleAttributeSet receivedColored;
  private Message message;

  public ClientGUI(String nameClient) {
    this.nameClient = nameClient;

    configLayout();
  }

  private void configLayout() {
    setTitle("Chat - " + nameClient);
    setSize(600, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Center the window on the screen
    setLocationRelativeTo(null);

    // Layout manager for the overall frame
    setLayout(new BorderLayout());

    // 1. Historic Messages Section (3/4 of the screen)
    historicMessagesArea = new JTextPane();
    historicMessagesArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(historicMessagesArea);
    add(scrollPane, BorderLayout.CENTER);

    this.doc = historicMessagesArea.getStyledDocument();

    normal = new SimpleAttributeSet();
    sentColored = new SimpleAttributeSet();
    receivedColored = new SimpleAttributeSet();

    StyleConstants.setForeground(normal, Color.BLACK);
    StyleConstants.setForeground(sentColored, Color.BLUE);
    StyleConstants.setForeground(receivedColored, Color.RED);

    // 2. Input and Submit Section (1/4 of the screen)
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BorderLayout());

    // Text field at the bottom left
    inputField = new JTextField();
    inputPanel.add(inputField, BorderLayout.CENTER);

    // Submit button at the bottom right
    submitButton = new JButton("Send");
    inputPanel.add(submitButton, BorderLayout.EAST);

    // Add the input panel to the bottom of the frame
    add(inputPanel, BorderLayout.SOUTH);

    // Button action: when clicked, send the messageu to the server
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String chatInput = inputField.getText().trim();

        if (chatInput != null && !chatInput.isEmpty() && !chatInput.isBlank()) {
          sendMessageNormal2Server(chatInput);
        }
      }
    });

    setVisible(true);

    inputField.requestFocusInWindow();
    inputField.addActionListener(e -> submitButton.doClick());
  }

  private void sendMessageNormal2Server(String chatInput) {
    message = new Message(chatInput, nameClient, MessageType.NORMAL);

    writeMessageOnStream();

    appendToHistoricMessages(message, false);

    // Clear the input field after sending
    inputField.setText("");
  }

  public void sendHelloMessage2Server() {
    message = new Message("", nameClient, MessageType.HELLO);

    writeMessageOnStream();
  }

  public void writeMessageOnStream() {
    try {
      SocketConnection.getObjOutputStream().writeObject(message);
      // flush() to avoid that the socket hangs waiting for a server message.
      SocketConnection.getObjOutputStream().flush();

      System.out.println("Message sent to server: " + message.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Method to update the historic messages area
  public void appendToHistoricMessages(Message msg, boolean isServer) {
    try {
      if (isServer) {
        // Received message from server and is a different user
        if (!msg.getAuthor().equals(nameClient)) {
          doc.insertString(doc.getLength(), msg.getAuthor() + " ", receivedColored);
        } else {
          // same user, so it don't print the message on screen
          return;
        }
      } else {
        // user is sending message to the server, so it update the screen
        doc.insertString(doc.getLength(), msg.getAuthor() + " ", sentColored);
      }
      doc.insertString(doc.getLength(), msg.getDate() + ": ", normal);
      doc.insertString(doc.getLength(), msg.getMessage() + "\n", normal);
    } catch (BadLocationException e) {
      e.printStackTrace();
    }
    // Scroll to the bottom
    historicMessagesArea.setCaretPosition(historicMessagesArea.getDocument().getLength());
  }
}
