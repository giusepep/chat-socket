package com.chatsocket.message;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.chatsocket.message.MessageType;

public class Message implements Serializable {
  private static final long serialVersionUID = 1L;
  private String message;
  private String author;
  private String date;
  private MessageType type;
  private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

  public Message(String message, String author, MessageType type) {
    this.message = message;
    this.author = author;
    this.type = type;

    LocalDateTime now = LocalDateTime.now();
    this.date = now.format(formatter);
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getDate() {
    return date;
  }

  public void setDate() {
    LocalDateTime now = LocalDateTime.now();
    this.date = now.format(formatter);
  }

  public MessageType getType() {
    return this.type;
  }

  public void setType(MessageType type) {
    this.type = type;
  }

}
