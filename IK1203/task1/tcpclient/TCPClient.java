package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
  public static String AskServer(String hostname, int port, String input) throws IOException {
    /* Final output string. */
    String output = "";

    /* New socket. */
    Socket socket = new Socket();
    /* Server address by name. */
    InetAddress srv_address = InetAddress.getByName(hostname);
    /* SocketAddress. */
    SocketAddress socket_address = new InetSocketAddress(srv_address, port);
    /* If the server does not close the connection, wait 1.5 seconds then read. */
    socket.setSoTimeout(1500);
    /* BOOM, CONNECT! */
    socket.connect(socket_address);

    /* If client is connected, start server request. */
    if (socket != null && socket.isConnected()) {
      output = ServerRequest(socket, input);
      socket.close();
    }
    return output;
  }

  private static String ServerRequest(Socket socket, String input) throws IOException {
    /* Message length */
    int msg_size = 0;
    /* Input & Output buffers for the socket. */
    byte[] buffer_input = new byte[socket.getReceiveBufferSize()];
    byte[] buffer_output = new byte[socket.getSendBufferSize()];

    /* If the client has an input. */
    if (input != null) {
      msg_size = input.length();
      buffer_output = input.getBytes();
      socket.getOutputStream().write(buffer_output, 0, msg_size);
      socket.getOutputStream().write('\n');
    }

    /* Server output. */
    StringBuilder output = new StringBuilder();
    InputStream input_stream = socket.getInputStream();
    int buffer_reads = 0;

    while (msg_size != -1 && buffer_reads < 10) {
      try {
        /* Read, if server refuses to close catch timeout exception. */
        msg_size = input_stream.read(buffer_input);
        output.append(new String(buffer_input, 0, msg_size)); 
        buffer_reads++;
      } catch (Exception e) {
        /* Timed out, end read loop. */
        msg_size = -1;
      }
    }
    return output.toString();
  }
}
