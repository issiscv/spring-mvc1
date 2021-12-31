package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LogTestController {

    @GetMapping("/log-test")
    public String log() {
        String name = "Spring";

        System.out.println("name = " + name);
        log.trace("name : {}", name);
        log.debug("name : {}", name);
        log.info("name : {}", name);
        log.warn("name : {}", name);
        log.error("name : {}", name);

        return "name";
    }
}
