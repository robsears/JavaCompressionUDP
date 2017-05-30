public class Message {

    // These all need to be public for Jackson to work with this class
    public String to;
    public String from;
    public String body;

    // This is the signature used to create the initial message
    Message(String to, String from, String body) {
        this.to = to;
        this.from = from;
        this.body = body;
    }

    // This is needed so that Jackson can create a Message
    // instance from deserialized JSON
    public Message() {
        super();
    }

    @Override
    public String toString() {
        return String.format("To: %s\nFrom: %s\nBody: %s\n", this.to, this.from, this.body);
    }
}
