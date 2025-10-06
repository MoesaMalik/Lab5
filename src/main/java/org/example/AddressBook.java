package org.example;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class AddressBook {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "addressbook_id") // foreign key in BuddyInfo table
    private List<BuddyInfo> buddies = new ArrayList<>();

    public AddressBook() {}

    public void addBuddy(BuddyInfo buddy) {
        if (buddy != null) {
            buddies.add(buddy);
        }
    }

    public boolean removeBuddy(BuddyInfo buddy) {
        return buddies.remove(buddy);
    }

    public Integer getId() {
        return id;
    }

    public List<BuddyInfo> getBuddies() {
        return new ArrayList<>(buddies);
    }

    public int size() {
        return buddies.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AddressBook:\n");
        for (BuddyInfo b : buddies) {
            sb.append(" - ").append(b.toString()).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AddressBook ab = new AddressBook();
        ab.addBuddy(new BuddyInfo("Moosey", "613-111-2222", "moosey@example.com"));
        ab.addBuddy(new BuddyInfo("Pops", "613-333-4444", "pops@example.com"));
        System.out.println(ab);
    }
}
