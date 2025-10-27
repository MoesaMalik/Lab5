package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    @Autowired
    private AddressBookRepository addressBookRepo;

    @Autowired
    private BuddyInfoRepository buddyRepo;

    //Get all address books
    @GetMapping
    public List<AddressBook> getAllAddressBooks() {
        return (List<AddressBook>) addressBookRepo.findAll();
    }

    //Get a specific address book
    @GetMapping("/{id}/buddies")
    public AddressBook getAddressBook(@PathVariable Integer id) {
        return addressBookRepo.findById(id).orElseThrow();
    }

    //Create new address book
    @PostMapping
    public AddressBook createAddressBook() {
        AddressBook ab = new AddressBook();
        return addressBookRepo.save(ab);
    }

    @PostMapping("/{id}/buddies")
    public AddressBook addBuddy(@PathVariable Integer id, @RequestBody BuddyInfo buddy) {
        AddressBook ab = addressBookRepo.findById(id).orElseThrow();
        ab.addBuddy(buddy);
        return addressBookRepo.save(ab);   // cascade saves the buddy too
    }

    //Remove buddy from address book
    @DeleteMapping("/{id}/buddies/{buddyId}")
    public AddressBook removeBuddy(@PathVariable Integer id, @PathVariable Integer buddyId) {
        AddressBook ab = addressBookRepo.findById(id).orElseThrow();
        BuddyInfo buddy = buddyRepo.findById(buddyId).orElseThrow();
        ab.removeBuddy(buddy);
        buddyRepo.delete(buddy);
        return addressBookRepo.save(ab);
    }
}