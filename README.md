# JavaCompressionUDP

A simple demonstration of how to set up a network protocol using compression (gzip) and UDP in Java.

## Build Instructions

Make sure you have gradle installed. Then clone the repo and run `gradle fatty`, which will produce a fat jar with all the necessary dependencies. This can then be run from the command line.

Example:

```bash
$ git clone https://github.com/robsears/JavaCompressionUDP
$ cd JavaCompressionUDP
$ gradle fatty
$ java -jar JavaCompressionUDP-all-0.1.jar
Server: Listening on port 1234.
Client: Preparing to broadcast to 127.0.0.1:1234.
Client: Compressing test message.
Client: Sending compressed message. 443 compressed bytes, 795 uncompressed.
Server: Decompressing message.
Message received:

To: Alice
From: Bob
Body: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vestibulum dolor odio, sed sollicitudin sapien efficitur ut. Vestibulum gravida risus eu tellus aliquet commodo. Pellentesque sollicitudin orci et risus lobortis, quis elementum est bibendum. Suspendisse molestie cursus facilisis. Integer nec tellus pulvinar velit feugiat fermentum. Sed non risus sem. Pellentesque enim dui, interdum in pharetra id, bibendum eu ex. Praesent lacinia ante et dolor faucibus, quis dignissim eros pellentesque. Curabitur hendrerit imperdiet condimentum. Donec mi quam, elementum vel consectetur ac, posuere blandit ex. Sed vitae velit eu lectus rutrum condimentum. Donec vitae odio et mi vehicula euismod aliquam quis sem. Nulla aliquam convallis felis at tristique.
Received 795 uncompressed bytes.
```

## Brief Description

This demos a basic client-server model. The client takes a `Message` instance, serializes it into a JSON string, compresses it with gzip, then fires it over UDP to a waiting server. The server receives the compressed data, decompresses it, then deserializes the JSON into a new `Message` instance. It then displays the message and some data about it.

For brevity, this demonstration is taking place over an abstract network. Basically, the computer is sending itself a message. So rather than having two different java programs running, one client and one server, threading is used to maintain them in separate processes, one that listens and one that speaks. One could easily adapt this code to something that transmits over a real network.

More on the subject at this blog entry: https://robsears.com/java-networking-with-compression-and-udp/
