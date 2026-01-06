package ai.planit.pev;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
public class PevApplication {

    private static final Logger log = LoggerFactory.getLogger(PevApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PevApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = env.getProperty("server.ssl.key-store") != null ? "https" : "http";
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (contextPath == null || contextPath.isBlank()) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using 'localhost' as fallback");
        }

        String profiles = Arrays.toString(env.getActiveProfiles());
        String applicationName = env.getProperty("spring.application.name");

        log.info("\n----------------------------------------------------------\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local:      {}://localhost:{}{}\n\t" +
                "External:   {}://{}:{}{}\n\t" +
                "Profile(s): {}\n" +
                "----------------------------------------------------------",
                applicationName,
                protocol, serverPort, contextPath,
                protocol, hostAddress, serverPort, contextPath,
                profiles);
    }
}
