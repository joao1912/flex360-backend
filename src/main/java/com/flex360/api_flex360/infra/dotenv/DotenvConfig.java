package com.flex360.api_flex360.infra.dotenv;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

@Configuration
public class DotenvConfig implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private Dotenv dotenv;

    @Override
    public void onApplicationEvent(@NonNull ApplicationEnvironmentPreparedEvent event) {

        dotenv = Dotenv.configure().ignoreIfMissing().load();

    }

    @Bean
    public Dotenv dotenv() {
        if (dotenv == null) {
            dotenv = Dotenv.configure().ignoreIfMissing().load();
        }
        return dotenv;
    }
}
