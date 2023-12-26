package ai.planit.pev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class PevApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PevApplication.class);
        app.addListeners(new ApplicationPidFileWriter());
        app.run(args);
    }
}
