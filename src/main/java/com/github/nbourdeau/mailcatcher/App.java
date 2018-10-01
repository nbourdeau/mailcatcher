package com.github.nbourdeau.mailcatcher;

import com.dumbster.smtp.SimpleSmtpServer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App  implements ApplicationRunner, DisposableBean {

    @Value("${smtp.port}")
    private Integer smtpPort;
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
        smtpServer = SimpleSmtpServer.start(smtpPort, messageService);
    }

    @Override
    public void destroy() throws Exception {
        if (smtpServer != null) {
            smtpServer.close();
        }
    }
}
