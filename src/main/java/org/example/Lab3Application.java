package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lab3Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab3Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(AddressBookRepository addressBookRepo) {
        return (args) -> {

            BuddyInfo b1 = new BuddyInfo("Moesa", "123-4567", "moesa@example.com");
            BuddyInfo b2 = new BuddyInfo("Malik", "911", "malik@example.com");

            AddressBook ab = new AddressBook();
            ab.addBuddy(b1);
            ab.addBuddy(b2);

            //save it to db
            addressBookRepo.save(ab);

            System.out.println("AddressBooks in DB:");
            addressBookRepo.findAll().forEach(System.out::println);
        };
    }
}
