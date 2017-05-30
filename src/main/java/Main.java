import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        // Default to a port that's easy to remember
        Integer commsPort = 1234;

        // This is the object we're going to serialize, compress and send over UDP
        Message message = new Message(
          "Alice",
          "Bob",
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum dolor odio, sed sollicitudin sapien efficitur ut. Vestibulum gravida risus eu tellus aliquet commodo. Pellentesque sollicitudin orci et risus lobortis, quis elementum est bibendum. Suspendisse molestie cursus facilisis. Integer nec tellus pulvinar velit feugiat fermentum. Sed non risus sem. Pellentesque enim dui, interdum in pharetra id, bibendum eu ex. Praesent lacinia ante et dolor faucibus, quis dignissim eros pellentesque. Curabitur hendrerit imperdiet condimentum. Donec mi quam, elementum vel consectetur ac, posuere blandit ex. Sed vitae velit eu lectus rutrum condimentum. Donec vitae odio et mi vehicula euismod aliquam quis sem. Nulla aliquam convallis felis at tristique.");

        // Instantiate a new Server. This will spin up a new thread that listens
        // on the default port for incoming data
        Server server = new Server(commsPort);

        // Instantiate a new Client. This will spin up a new thread that sends
        // the Message object over the network
        Client client = new Client("127.0.0.1", commsPort, message);

    }
}
