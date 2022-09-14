<h1 align=center>This Project has been renamed and moved to another repo (it's not dead)</h1>


<h2 align=center>Whörld Wide Messenger</h2>

Welcome to our highly secure community-driven messenger service!

### 💬 About the project

Whörld Wide Messenger is a community-driven messenger service with latest security standards in mind, written 100% in ☕ Java / Kotlin. It uses decentralized, optionally self-hosted servers for your friends to join and hang out together. More information can be found on our website https://www.wwm.dev

### 🎯 Our mission 

We want to have the benefits of quickly writing a message to a relative, but also having a fun place to hang out with your gaming-friends. Whörld Wide Messenger is not built to focus on one specific group of people, but rather being a place for everyone to connect with each other. Because of that, high security is very important to us hence we use latest technology to keep your account and messages safe.

Whörld Wide Messenger is available on many social media platforms such as Reddit, Twitter or even straight inside our platform for you to have a way to directly communicate with us. We are always open for changes and improvements, and if enough people vote for something they want to get changed, we will do our best to fulfill these wishes.

Aside from that, we all know how staring at the same user interface everday can get tired really fast. We want to circumvent this issue by providing great customizability inside our messaging client. You can change the color of nearly all visual elements of the app and even choose a custom background image for when you're chatting. (planned)

### 🤝 Why should I trust WWM? 

Whörld Wide Messenger is 100% open source and we don't hide a single thing - everything can be looked up in the repository. Your messages, files and images are safely stored in our secure database and cannot be inspected by any of our developers, because they are end-to-end encrypted. The same applies to your account; Your password and private decryption key are safely stored hashed + encrypted in our database and cannot be used to log into the account, even if we get compromised.

### ☕ Why Java? 

Java is slow, isn't it? Short answer: No, definitely not. We don't think any of these rumors are true. It's what we are familiar with and we know that Java can be insanely fast if used correctly. Java also provides a good way for cross platform deployment, we don't want that "Windows only, etc..." stuff - Whörld Wide Messenger is available on all platforms that are capable of running Java. This does indeed make development for a native look and feel very difficult for some platforms (we are looking at you, iOS), but either we will get it done somehow the hacky way, or switch to their native development language (Swift and Objective-C in that case).

For GUI development we chose to use Kotlin as our primary language because it provides many useful features that make building large object oriented structures a breeze. 

### 🔨 How it works 

The project heavily depends on the Netty Java networking stack. From account creation to sending text messages and pictures to your friends, Netty will be the one handling all the different packets. To ensure the data is safely transferred from one side to the other, we carefully ecrypt each packet's content with a cryptographic algorithm known as Rivest–Shamir–Adleman (RSA) algorithm.

Graphics wise we chose to use JavaFX together with Kotlin for a simple way to get a clean looking user interface with slick animations and great customizability.

An in-depth explanation on how Whörld Wide Messenger works internally and how it can be expanded can be found at the very bottom of this page.

### 📡 Self hosted severs

While we as the official developers of Whörld Wide Messenger do provide high performance servers for free or rented, it is also possible for you to create and host your very own servers with a lot more 

configuration and fine tweaking. To get started, simply download the server install wizard found at the release page of this repository and follow the instructions of the terminal executable. Because each server runs on a specific IP-address, you can also provide your server with your very own custom domain.

### 🚀 Roadmap

Whörld Wide Messenger has still lots of thing to come in the near future and will always be developed to either introduce new features or just improve the overall experience of the platform. The items in the list below are sorted by priority.

* 💬 Basic messenger features (accounts, friends, direct messaging, servers, etc...)
* ⚖ Server permission System with roles for different groups of people
* 🚪 Client sided gateway server customization
* 🔥 Server bound application interface (api) for getting server members and statistics; can be changed by preference.
* 📢 Voice channels (with noise filtering algorithms, etc...)
* 🖌 Better interface customization (either through the settings panel or configuration files)
* 🖥 Server sided user interface for customization and monitoring.
* 🌐 Server sided local web socket for third party monitoring

<h1 align=center>Insider - An in-depth look</h1>

This guide will cover everything of the internal workings from client user interface to packet handling and encryption from a very technical perspective. The guide is written in chronological order and provides small code samples on the way forward.

### 0.0 Topology
<hr>
The messenger is split into several parts that communicate with each other. The following graph should visualize the connections between these parts:

🌐 Decentralized Server (either self hosted or provided)<br>
    ┃   ┃<br>
    ┃   ┣━━ 🗄 MySQL Database (stores server members, roles, permissions, ...)<br>
    ┃   ┗━━ ✉ Email Server (either self hosted or through third party services, the hostee can decide)<br>
    ┃<br>
    ┃ (Connect through server browser, direct connection, domain, ...)<br>
    ┃<br>
📍 Client ━━🚪Gateway Server (always there and invisible to the user themselves most of the time, but configurable)<br>
    ┃<br>
    ┃ (Always the first connection on application startup)<br>
    ┃<br>
    ┃   ┏━━ 🗄 MySQL Database (stores all registered users with passwords)<br>
    ┃   ┣━━ ✉ Email Server (either we host it or use third party services)<br>
    ┃   ┃<br>
💬 Whörld Wide Messenger (the authentication server)

### 0.1 Client
<hr>
A client represents a single instance of the Whörld Wide Messenger chat program. As soon as the app starts, the client connects to the official authentication server and waits for the user to perform an action. Other from basic client configuration, you are forced to either log into the service or use a guest account with limited access to the app. When logged in, the user is finally able to connect to any community server they want using the server's IP-address.

### 0.2 Gateway server
<hr>
Each client has their very own gateway server that other people can write messages to. Continuation follows when Müd explains me the system.

### 0.3 Custom, decentralized servers
<hr>
Everyone can host an instance of their own Whörld Wide Messenger server. These servers can be provided a custom domain and allows other people to connect to it. Unlike an official server hosted by the developers, a custom server allows for much greater customization as for member management, packet handling and setting up restrictions and rules.

### 0.3.1 Email server
<hr>
Custom servers allow you to change the behavior of sending emails to server members by providing them an email server. By default, the official Whörld Wide Messenger email server is used, but it can be changed to any third party provider you like, or even set it to none at all.

### 0.3.2 MySQL database
<hr>
Just like the authentication server, each custom server is equipped with its own MySQL database instance. There, all members of the server are stored with their respective data (permissions, nickname, auth-key, etc...). The database is forced to be used and has to follow a strict pattern, otherwise the server becomes unusable.

### 0.4 Authentication server
<hr>
This server is responsible for managing all the user accounts registered on the platform. When connected to this server, clients have numerous options on what to do next. They can either create a new account, log into an existing account, or reset their password.

### 1.0 Packet protocol concept
<hr>
To understand how messages are transmitted from the client to the server and vice versa, it's important to first get a rough overview on how Whörld Wide Messenger implements the Java Netty networking stack. Of course, this also requires basic knowledge of Netty's concept itself - you can read more on it on their webpage. Let's get started with the server side.

### 1.1 Server side implementation
<hr>
When the server starts, several things get initialized, including a basic Netty ServerBootstrap. Most of the stuff there is just boilerplate code except for the child handler. As seen in the code snippet below, a new client connection is created for each user connecting to the server. This object serves as a "shared communication layer" between the client and the server. It is added to the pipeline. Don't mind the packet encoder and decoder, these will get important later.
