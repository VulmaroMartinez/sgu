package mx.edu.utez.sgubackend.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DBConnection {

    @Value("${db.host}")
    private String host;
    @Value("${db.port}")
    private String port;
    @Value("${db.name}")
    private String name;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;

    @Bean
    public DataSource getDBConneciton() {
        DriverManagerDataSource src = new DriverManagerDataSource();
        src.setDriverClassName("com.mysql.cj.jdbc.Driver");
        src.setUrl("jdbc:mysql://" + host + ":" + port + "/" + name);
        src.setUsername(user);
        src.setPassword(password);
        return src;
    }
}