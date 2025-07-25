package com.chatsocket.connection;

import java.net.*;
import java.util.Enumeration;

public class NetworkUtils {
  public static final int PORT = 12345;

  public static String getLocalIpAddress() {
    try {
      Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
      while (interfaces.hasMoreElements()) {
        NetworkInterface iface = interfaces.nextElement();

        if (iface.isLoopback() || !iface.isUp() || iface.isVirtual()) {
          continue;
        }

        Enumeration<InetAddress> addresses = iface.getInetAddresses();

        while (addresses.hasMoreElements()) {
          InetAddress addr = addresses.nextElement();
          if (addr instanceof Inet4Address && addr.isSiteLocalAddress()) {
            return addr.getHostAddress();
          }
        }
      }
    } catch (SocketException e) {
      e.printStackTrace();
    }

    return "Unknown";
  }
}
