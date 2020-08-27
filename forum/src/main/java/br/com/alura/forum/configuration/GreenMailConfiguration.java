package br.com.alura.forum.configuration;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
@Profile("dev")
public class GreenMailConfiguration {

    @Autowired
    private GreenMail smtpServer;

    @Value("${spring.mail.port}")
    private Integer port;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.protocol}")
    private String protocol;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    @PostConstruct
    public void setup(){
        ServerSetup serverSetup = new ServerSetup(this.port, this.host, this.protocol);
        this.smtpServer = new GreenMail(serverSetup);
        this.smtpServer.setUser(username, username, password);
        this.smtpServer.start();
    }

    @PreDestroy
    public void destroy(){
        this.smtpServer.stop();
    }

}
