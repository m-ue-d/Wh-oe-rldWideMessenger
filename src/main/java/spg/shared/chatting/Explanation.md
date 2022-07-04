# Package spg.shared.chatting
Contains data models for sending and receiving chat messages over the network.


# Class [SentMessage](SentMessage.kt)
The data model for sending a message. It includes the message together with a list of recipients. This packet is sent to the server whenever you write something in the chat window. You as the sender will automatically receive a response from the server, otherwise you will not see your own message.
    
# Class [ReceivedMessage](ReceivedMessage.kt)
The data model for receiving a message. It includes the message and the sender. This packet is sent to you whenever someone includes you in their recipients list.