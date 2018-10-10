# MultiThread_ChatServer

A Java Multi Threaded Chat Server with own Garbage Collector. Every 10s it removes all the messages in the buffer that have been read by all the users.

The messages are serialized using [protobuf](https://github.com/protocolbuffers/protobuf)

## How to use

1. ```cd MultiThread_ChatServer/```

2. ```sh run.sh server```

3. ```sh run.sh client```

## Client 

After you have registered, you only get to see messages from that time forward. 

If you are logging in, you can check all the messages that were sent while you were away.

In ```config.properties``` insert the IP and Port for the client to connect to the server.


## Some things need yet to be improved/corrected.