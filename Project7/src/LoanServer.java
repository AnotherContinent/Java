
import java.io.*;
import java.net.*;

public class LoanServer {
  private ObjectOutputStream outputToFile;
  private ObjectInputStream inputFromClient;

  public static void main(String[] args) {
    new LoanServer();
  }

  public LoanServer() {
    try {
      // Create a server socket
      ServerSocket serverSocket = new ServerSocket(8001);
      System.out.println("Server started ");


      while (true) {
        // Listen for a new connection request
        Socket socket = serverSocket.accept();

        // Create an input stream from the socket
        inputFromClient =
          new ObjectInputStream(socket.getInputStream());

        // Read from input
        Object object = inputFromClient.readObject();

       
      }
    }
    catch(ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    catch(IOException ex) {
      ex.printStackTrace();
    }
    finally {
      try {
        inputFromClient.close();
        outputToFile.close();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}
