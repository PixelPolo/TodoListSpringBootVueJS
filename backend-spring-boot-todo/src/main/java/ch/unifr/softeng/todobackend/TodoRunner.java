package ch.unifr.softeng.todobackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// @Component tells that this class must be automatically detected and instanced.
// CommandLineRunner is an interface from SpringBoot to execute some code at application start.
// @Autowired is used to inject the dependency TagRepository instance.

@Component
public class TodoRunner implements CommandLineRunner {

    @Autowired
    private TagRepository tagRepository;

    // Create some tags at application start...
    @Override
    public void run(String... args) throws Exception {
        Tag t1 = new Tag("Work");
        tagRepository.save(t1);
        Tag t2 = new Tag("Social");
        tagRepository.save(t2);
        Tag t3 = new Tag("Miscellaneous");
        tagRepository.save(t3);
    }

}