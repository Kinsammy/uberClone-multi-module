package io.samtech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication(scanBasePackages = {"io.samtech.**"})
@EntityScan(basePackages = {"io.samtech.**"})
@EnableMongoRepositories(basePackages = {"io.samtech.**"})
@EnableAsync
public class App extends AsyncConfigurerSupport {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("UberCloneLookup-");
        executor.initialize();
        return executor;
    }
}
