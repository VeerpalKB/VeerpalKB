/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany;

/**
 *
 * @author veerp
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
// add new imports
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClientGUI extends JFrame {
  private JTextArea messageArea;
  private JTextField textField;
  private ChatClient client;
  private JButton exitButton;
  
  public ChatClientGUI() {
      super("Chat Application");
      setSize(400, 500);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      // Styling variables
      Color backgroundColor = new Color(240, 240, 240); // Light gray background
      Color buttonColor = new Color(75, 75, 75); // Darker gray for buttons
      Color textColor = new Color(50, 50, 50); // Almost black for text
      Font textFont = new Font("Arial", Font.PLAIN, 14);
      Font buttonFont = new Font("Arial", Font.BOLD, 12);

      
      
      messageArea = new JTextArea();
      messageArea.setEditable(false);
      messageArea.setBackground(backgroundColor);
      messageArea.setForeground(textColor);
      messageArea.setFont(textFont);
      JScrollPane scrollPane = new JScrollPane(messageArea);
      add(scrollPane, BorderLayout.CENTER);
      
      // Prompt for user name
      String name = JOptionPane.showInputDialog(this, "Enter your name:", "Name Entry", JOptionPane.PLAIN_MESSAGE);
      // Update the window title to include user's name
      this.setTitle("Chat Application - " + name);


      textField = new JTextField();
      textField.setFont(textFont);
      textField.setForeground(textColor);
      textField.setBackground(backgroundColor);
      textField.addActionListener(new ActionListener()
              {
          @Override
          public void actionPerformed(ActionEvent e) {
   String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + name + ": "
           
          + textField.getText();
              client.sendMessage(message);
              textField.setText("");
          }
      });
      add(textField, BorderLayout.SOUTH);
  
  // Initialize and start the ChatClient
      try {
          this.client = new ChatClient("127.0.0.1", 5000, this::onMessageReceived);
          client.startClient();
      } catch (IOException e) {
          e.printStackTrace();
          JOptionPane.showMessageDialog(this, "Error connecting to the server", "Connection error",
                  JOptionPane.ERROR_MESSAGE);
          System.exit(1);
      }
      
      // Prompt for user name
  //String name = JOptionPane.showInputDialog(this, "Enter your name:", "Name Entry", JOptionPane.PLAIN_MESSAGE);
  this.setTitle("Chat Application - " + name); // Set window title to include user name
  
  // Modify actionPerformed to include the user name and time stamp
  textField.addActionListener(e -> {
      String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + name + ": " + textField.getText();
      client.sendMessage(message);
      textField.setText("");
  });
 // Initialize and start the ChatClient with modifications for user name
  
  // Initialize the exit button
  // Apply styles to the exit button and initialize it
  exitButton = new JButton("Exit");
  exitButton.setFont(buttonFont);
  exitButton.setBackground(buttonColor);
  exitButton.setForeground(Color.WHITE);
  exitButton.addActionListener(e -> {
      
  // Send a departure message to the server
    String departureMessage = name + " has left the chat.";
    client.sendMessage(departureMessage);
  
  // Delay to ensure the message is sent before exiting
  try {
      Thread.sleep(1000); // Wait for 1 second to ensure message is sent
  } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
  }
 
  // Exit the application
  System.exit(0);
});
  
   // Creating a bottom panel to hold the text field and exit button
      JPanel bottomPanel = new JPanel(new BorderLayout());
      bottomPanel.setBackground(backgroundColor); // Apply background color to the panel
      bottomPanel.add(textField, BorderLayout.CENTER);
      bottomPanel.add(exitButton, BorderLayout.EAST); // Add the exit button to the bottom panel
      add(bottomPanel, BorderLayout.SOUTH); // Add the bottom panel to the frame
      
      // Initialize and start the ChatClient
      try {
          this.client = new ChatClient("127.0.0.1", 5000, this::onMessageReceived);
          client.startClient();
      } catch (IOException e) {
          e.printStackTrace();
          JOptionPane.showMessageDialog(this, "Error connecting to the server", "Connection error",
                  JOptionPane.ERROR_MESSAGE);
          System.exit(1);
      }
  }
  // Existing client initialization code...

  private void onMessageReceived(String message) {
      // Use SwingUtilities.invokeLater to ensure thread safety when updating the GUI
      SwingUtilities.invokeLater(() -> messageArea.append(message + "\n"));
  }

  public static void main(String[] args) {
      // Ensure the GUI is created and updated on the Event Dispatch Thread
      SwingUtilities.invokeLater(() -> {
          new ChatClientGUI().setVisible(true);
      });
  }
}
  
  

