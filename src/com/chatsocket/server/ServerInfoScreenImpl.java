package com.chatsocket.server;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.chatsocket.connection.NetworkUtils;
import com.chatsocket.screen.ServerInfoScreen;

public class ServerInfoScreenImpl extends JFrame implements ServerInfoScreen {

  // HashMap to store client ID and Name
  private HashMap<Integer, String> clientData = new HashMap<>();
  private static final int TABLE_SIZE = 16;

  // Table for displaying client information
  private JTable clientTable;
  private DefaultTableModel tableModel;

  public ServerInfoScreenImpl() {
    configFrame();
    configComponents();

    // Make the frame visible
    setVisible(true);
  }

  @Override
  public void configFrame() {
    // Set up JFrame (window) properties
    setTitle("Server Information");
    setSize(600, 400);
    setLocationRelativeTo(null); // Center the window
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Set layout for the frame
    setLayout(new BorderLayout());
  }

  @Override
  public void configComponents() {
    // 1. Panel for showing Server IP and Port
    JPanel infoPanel = new JPanel();
    infoPanel.setLayout(new GridLayout(2, 1)); // Two lines: IP and Port
    JLabel ipLabel = new JLabel("Server IP: " + NetworkUtils.getLocalIpAddress());
    JLabel portLabel = new JLabel("Port: " + NetworkUtils.PORT);
    infoPanel.add(ipLabel);
    infoPanel.add(portLabel);
    add(infoPanel, BorderLayout.NORTH);

    // 2. Panel for displaying clients (ID + Name)
    String[] columnNames = { "Client ID", "Client Name" };
    tableModel = new DefaultTableModel(columnNames, 0);
    clientTable = new JTable(tableModel);
    JScrollPane tableScrollPane = new JScrollPane(clientTable);
    add(tableScrollPane, BorderLayout.CENTER);

    // Make the table read-only
    clientTable.setEnabled(false);
  }

  // Method to add a new client to the HashMap and update the table
  @Override
  public void addClient(String clientName) {
    int hashKey = hash(clientName);
    // Add to the HashMap
    clientData.put(hashKey, clientName);

    // Add a row in the table
    tableModel.addRow(new Object[] { hashKey, clientName });
  }

  @Override
  public void removeClient(String clientName) {
    clientData.remove(hash(clientName));
  }

  private int hash(String key) {
    return (key.hashCode() & 0x7FFFFFFF) % TABLE_SIZE;
  }

}
