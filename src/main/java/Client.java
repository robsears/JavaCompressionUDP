import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.zip.GZIPOutputStream;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Client implements Runnable {

    private InetAddress destinationHost;
    private Integer     destinationPort;
    private Thread      t;
    private Message     message;

    Client(String destinationHost, Integer destinationPort, Message message) throws IOException {
        this.destinationPort = destinationPort;
        this.destinationHost = InetAddress.getByName(destinationHost);
        this.message         = message;
        System.out.printf("Client: Preparing to broadcast to %s:%d.\n", destinationHost.toString(), destinationPort);
        start();
    }

    public void start() {
        if (t == null) {
            t = new Thread (this, "ClientThread");
            t.start();
        }
    }

    @Override
    public void run() {

        try {
            // Convert message to JSON object using Jackson
            ObjectMapper mapper = new ObjectMapper();
            String message = mapper.writeValueAsString(this.message);

            // Convert to a byte array stream. Using an output stream will allow
            // us to manipulate the bytes
            byte[] messageBytes = message.getBytes();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(messageBytes.length);

            // Compress the stream using GZIPOutputStream
            System.out.printf("Client: Compressing test message.\n");
            GZIPOutputStream compressed = new GZIPOutputStream(outputStream);

            // Write the compressed message out to messageBytes, then close stream
            compressed.write(messageBytes, 0, messageBytes.length);
            compressed.flush();
            compressed.close();

            // Set up the socket to the server. Datagram is UDP.
            DatagramSocket clientSocket = new DatagramSocket();

            // Construct a packet using the outputStream
            DatagramPacket packet = new DatagramPacket(
                outputStream.toByteArray(),
                outputStream.toByteArray().length,
                destinationHost,
                destinationPort
            );

            // Send away!
            System.out.printf(
                "Client: Sending compressed message. %d compressed bytes, %d uncompressed.\n",
                outputStream.toByteArray().length,
                messageBytes.length
            );
            clientSocket.send(packet);

            // The client is now finished.

        } catch (IOException e) {
            // Catch any exception that might occur above and print a stack trace
            e.printStackTrace();
        }
    }

}
