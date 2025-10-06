package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {

    @Autowired
    private AddressBookRepository addressBookRepo;

    @GetMapping("/")
    public String redirectToDefault() {
        var books = addressBookRepo.findAll();
        if (books.iterator().hasNext()) {
            var first = books.iterator().next();
            return "redirect:/addressbook/2/view";
        }
        return "redirect:/error";
    }

    @GetMapping("/addressbook/{id}/view")
    public String viewAddressBook(@PathVariable Integer id, Model model) {
        AddressBook ab = addressBookRepo.findById(id).orElseThrow();
        model.addAttribute("buddies", ab.getBuddies());
        return "addressbook"; // maps to src/main/resources/templates/addressbook.html
    }
}
