package com.github.nbourdeau.mailcatcher;

import com.dumbster.smtp.SimpleSmtpServer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App  implements ApplicationRunner, DisposableBean {

    private SimpleSmtpServer smtpServer;
    private final MessageService messageService;

    @Autowired
    public App(MessageService messageService) {
        this.messageService = messageService;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption("help")) {
            System.out.println("Usage: java -jar mailcatcher.jar [--port=<int port>]\n");
            System.out.println("Default port is 25");
            return;
        }
        int port = SimpleSmtpServer.DEFAULT_SMTP_PORT;
        if (args.containsOption("port"))
            port = Integer.parseInt(args.getOptionValues("port").get(0));
        smtpServer = SimpleSmtpServer.start(port, messageService);
    }

    @Override
    public void destroy() throws Exception {
        if (smtpServer != null) {
            smtpServer.close();
        }
    }
}
