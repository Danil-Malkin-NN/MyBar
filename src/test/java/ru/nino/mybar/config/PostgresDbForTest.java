package ru.nino.mybar.config;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public abstract class PostgresDbForTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:15-alpine"));

    static {
        postgres.withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(55432), new ExposedPort(5432)))
        ));
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        log.info("JDBC url = {}, user = {}, password = {}", postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
    }

}