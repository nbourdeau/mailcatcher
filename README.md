# mailcatcher
> The mailcatcher is a very simple fake SMTP server designed for software test environments that
> send email messages but do not want to send it for real. It responds to all standard SMTP commands but does not deliver messages to the user.
> The messages are stored within a databse for later extraction and verification and can be viewed in a web UI.

**This repository is a fork of [https://github.com/kirviq/dumbster]().**

I forked it because I wanted to act as a mailcatcher in a test environment.
For this purpose, I needed it to store messages in a more robust form of storage (i.e.: database) and have a web interface to view messages.

We also needed to be able to deploy it easely to a cloud server, so using Spring Boot to automatically start the SMTP server was used.

Aside from that, the actual smtp logic is completely unchanged.
