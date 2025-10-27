package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewController {

    @Autowired
    private AddressBookRepository addressBookRepo;

    @Autowired
    private BuddyInfoRepository buddyRepo;

    // Redirect root to list of address books
    @GetMapping("/")
    public String redirectToList() {
        return "redirect:/addressbooks";
    }

    // Serve the SPA page
    @GetMapping("/spa")
    public String showSPA() {
        return "spa";
    }

    // Show all address books
    @GetMapping("/addressbooks")
    public String listAddressBooks(Model model) {
        model.addAttribute("addressBooks", addressBookRepo.findAll());
        return "addressbook-list";
    }

    // Show form to create new address book
    @GetMapping("/addressbook/new")
    public String showCreateForm(Model model) {
        model.addAttribute("addressBook", new AddressBook());
        return "addressbook-form";
    }

    // Handle form submission to create address book
    @PostMapping("/addressbook/create")
    public String createAddressBook(@ModelAttribute AddressBook addressBook) {
        AddressBook saved = addressBookRepo.save(addressBook);
        return "redirect:/addressbook/" + saved.getId() + "/view";
    }

    // View a specific address book
    @GetMapping("/addressbook/{id}/view")
    public String viewAddressBook(@PathVariable Integer id, Model model) {
        AddressBook ab = addressBookRepo.findById(id).orElseThrow();
        model.addAttribute("addressBook", ab);
        model.addAttribute("buddies", ab.getBuddies());
        return "addressbook"; // Keep old name for backward compatibility
    }

    // Show form to add buddy to address book
    @GetMapping("/addressbook/{id}/buddy/new")
    public String showAddBuddyForm(@PathVariable Integer id, Model model) {
        AddressBook ab = addressBookRepo.findById(id).orElseThrow();
        model.addAttribute("addressBook", ab);
        model.addAttribute("buddy", new BuddyInfo());
        return "buddy-form";
    }

    // Handle form submission to add buddy
    @PostMapping("/addressbook/{id}/buddy/add")
    public String addBuddy(@PathVariable Integer id, @ModelAttribute BuddyInfo buddy) {
        AddressBook ab = addressBookRepo.findById(id).orElseThrow();
        ab.addBuddy(buddy);
        addressBookRepo.save(ab);  // Cascade saves the buddy
        return "redirect:/addressbook/" + id + "/view";
    }

    // Remove a buddy from address book
    @PostMapping("/addressbook/{id}/buddy/{buddyId}/remove")
    public String removeBuddy(@PathVariable Integer id, @PathVariable Integer buddyId) {
        AddressBook ab = addressBookRepo.findById(id).orElseThrow();
        BuddyInfo buddy = buddyRepo.findById(buddyId).orElseThrow();
        ab.removeBuddy(buddy);
        buddyRepo.delete(buddy);
        addressBookRepo.save(ab);
        return "redirect:/addressbook/" + id + "/view";
    }
}