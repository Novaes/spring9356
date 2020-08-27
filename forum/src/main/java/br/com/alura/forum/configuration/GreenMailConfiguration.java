package br.com.alura.forum.configuration;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.internet.MimeMessage;

@Profile("dev")
@Configuration
public class GreenMailConfiguration {

    private GreenMail greenMail;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.host}")
    private String localAddress;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.port}")
    private Integer port;



    @PostConstruct
    public void setup() {
        ServerSetup serverSetup = new ServerSetup(port, host, "smtp");
        this.greenMail = new GreenMail(serverSetup);
        this.greenMail.setUser(username, username, password);
        this.greenMail.start();
    }

    @PreDestroy
    public void destroy() {
        this.greenMail.stop();
    }
}
