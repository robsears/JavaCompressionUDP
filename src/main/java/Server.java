import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.zip.GZIPInputStream;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Server implements Runnable {

    private Integer listenPort;
    private Thread t;

    Server(Integer listenPort) throws IOException {
        this.listenPort = listenPort;
        System.out.printf("Server: Listening on port %d.\n", listenPort);
        start();
    }

    public void start() {
        if (t == null) {
            t = new Thread (this, "ServerThread");
            t.start();
        }
    }

    @Override
    public void run() {
        try {

            // Set up the socket to receive data
            DatagramSocket serverSocket = null;
            serverSocket = new DatagramSocket(listenPort);

            // We don't know what the size of the incoming packet will be, so we allocate a bigger chunk
            // A Datagram can be 65,507 bytes, max. The lorem ipsum text in main() should be about 795
            // uncompressed bytes, so we'll set it a little higher for that, and truncate the null chars
            // at the end
            byte[] receiveData = new byte[800];
            ByteArrayInputStream inputStream = new ByteArrayInputStream(receiveData);

            // Receive a packet
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            // The packet is compressed, so it will need to be decompressed
            System.out.printf("Server: Decompressing message.\n");
            GZIPInputStream decompressed = new GZIPInputStream(inputStream);
            decompressed.read(receiveData, 0, receiveData.length);
            decompressed.close();

            // Get the uncompressed data, and deserialize it
            String data = new String( receivePacket.getData() );
            ObjectMapper mapper = new ObjectMapper();
            Message message = mapper.readValue(data.trim(), Message.class);

            // Print the received thing to console
            System.out.printf("Message received:\n\n%s", message.toString());
            System.out.printf("Received %d uncompressed bytes.\n", data.trim().getBytes().length);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
