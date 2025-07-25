package com.chatsocket.screen;

public interface ServerInfoScreen {
  public void configFrame();

  public void configComponents();

  public void addClient(String clientName);

  public void removeClient(String clientName);
}
