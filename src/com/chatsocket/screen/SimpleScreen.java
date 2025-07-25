package com.chatsocket.screen;

import javax.swing.*;

import com.chatsocket.screen.ClientGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class SimpleScreen {
  private String msg;
  private JFrame frame;
  private JPanel panel;
  private String nameClient;
  private ClientGUI clientGUI;

  public SimpleScreen() {
    createFrame();
    configPanel();

    // Set the window to be visible
    this.frame.setVisible(true);
    System.out.println("SimpleScreen.createFrame(): frame visible");
  }

  private void createFrame() {
    // Create a new JFrame (window)
    this.frame = new JFrame("Login Screen");
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frame.setSize(250, 130); // Set window size
    this.frame.setLocationRelativeTo(null); // Center the window on the screen

    // Create a JPanel to hold components
    this.panel = new JPanel();
    // Add panel create to the frame
    this.frame.add(this.panel);

  }

  private void configPanel() {
    // Using null layout for manual positioning
    panel.setLayout(null);

    // Create JLabel to display instructions
    JLabel userLabel = new JLabel("Name:");
    userLabel.setBounds(10, 20, 80, 25); // Position the label
    panel.add(userLabel);

    // Create text input field
    JTextField textField = new JTextField(20);
    textField.setBounds(65, 20, 165, 25); // Position the text field
    panel.add(textField);

    // Create submit button
    JButton submitButton = new JButton("Enter Server");
    submitButton.setBounds(50, 60, 150, 25); // Position the button
    panel.add(submitButton);

    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (textField.getText() != null && !textField.getText().trim().isEmpty()) {
          nameClient = textField.getText();
          clientGUI = new ClientGUI(nameClient);

          // send a hello message to server to update server screen
          clientGUI.sendHelloMessage2Server();

          frame.dispose();
        }
      }
    });

    // hit enter to submit text
    textField.addActionListener(e -> submitButton.doClick());
  }

  public String getClientName() {
    return this.nameClient;
  }

  public ClientGUI getClientGUI() {
    return this.clientGUI;
  }
}
