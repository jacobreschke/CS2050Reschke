package CS2050Reschke.M03.ClassLabs; /**
 * Update code and add comments explaining concepts
 */

import java.util.ArrayList;

public class PetAdoptionCenter {
    public static void main(String[] args) {
        // ============================================
        // Part 1: Setup an ArrayList of interface type
        // ============================================
        ArrayList<Pet> pets = new ArrayList<>();

        // Add some example pets
        pets.add(new Bulldog("Bear"));
        pets.add(new Cat("Mittens"));


        // Task 1:
        // Add at least one more Pet type class (example: Parrot)
        // Then add at least one object of that type to the list.
        // pets.add(new Parrot("Rio"));
        pets.add(new Monkey("Curious George"));
        pets.add(new Orangutan("Dr. Zaius"));
        // ============================================
        // Part 2: Explain Polymorphic behavior with the interface
        // ============================================
        System.out.println("--- Meet Our Pets ---");
        for (int i = 0; i < pets.size(); i++) {
            Pet currentPet = pets.get(i);
            currentPet.beFriendly();
            currentPet.play();
        }

        // ============================================
        // Part 3: Shared behavior through the interface
        // ============================================
        System.out.println("\n--- Snack Time ---");
        feedAll(pets);

        // ============================================
        // Part 4: ArrayList operations
        // ============================================
        System.out.println("\n--- Adoption Updates ---");
        System.out.println("Total pets before adoption: " + pets.size());

        if (pets.size() > 0) {
            Pet adopted = pets.remove(0);
            System.out.println("Adopted out: " + adopted);
        }

        System.out.println("Total pets after adoption: " + pets.size());

        // ============================================
        // Part 5: Search
        // ============================================
        // Call findByName and print the result
        System.out.println(findByName(pets, "Curious George"));

        // ============================================
        // Part 6: Remove by name
        // ============================================
        // Call removeByName and print the result
        removeByName(pets, "Curious George");
        // ============================================
        // Part 7: Favorites list
        // ============================================
        // Build a list of pets whose names start with 'M'
        // Print the favorites list
        char favoriteLetter = 'D';

        ArrayList<Pet> favoritePets = buildFavoritesStartingWith(pets, favoriteLetter);

        System.out.println(favoritePets.size());

        for (Pet pet : favoritePets) {
            System.out.println(pet.getName());
        }
    }

    private static void feedAll(ArrayList<Pet> pets) {
        for (int i = 0; i < pets.size(); i++) {
            pets.get(i).eat();
        }
    }

    public static Pet findByName(ArrayList<Pet> pets, String nameToFind) {
        // TODO:
        // Return the first pet whose name matches nameToFind
        // Comparison should be case-insensitive
        // Return null if no match is found
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(nameToFind)) {
                return pet;
            }
        }
        return null;
    }


    public static boolean removeByName(ArrayList<Pet> pets, String nameToRemove) {
        // TODO:
        // Remove the first matching pet
        // Use an index-based loop
        // Return true if removed, otherwise false
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getName().equalsIgnoreCase(nameToRemove)) {
                pets.remove(i);
                System.out.println("Removed");
                return true;
            }
        }
        return false;
    }


    public static ArrayList<Pet> buildFavoritesStartingWith(ArrayList<Pet> pets, char letter) {
        // TODO:
        // Create a new ArrayList<Pet>
        // Add pets whose names start with the given letter
        // Comparison should be case-insensitive
        ArrayList<Pet> returnArray = new ArrayList<Pet>();
        String searchLetter = String.valueOf(letter).toUpperCase();

        for (Pet pet : pets) {
            //if (pet.getName().toUpperCase().startsWith(searchLetter)) {
            if (pet.getName().toUpperCase().startsWith(searchLetter)) {
                returnArray.add(pet);
            }
        }
        return returnArray;
    }
}

// ============================================
// Abstract superclass
// ============================================
abstract class Animal {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// ============================================
// Interface
// ============================================
interface Pet {
    void beFriendly();

    void play();

    void eat();

    String getName();
}

// ============================================
// Concrete classes
// ============================================
class Bulldog extends Animal implements Pet {
    public Bulldog(String name) {
        super(name);
    }

    @Override
    public void beFriendly() {
        System.out.println(getName() + " wags tail and leans on your leg.");
    }

    @Override
    public void play() {
        System.out.println(getName() + " plays tug-of-war.");
    }

    @Override
    public void eat() {
        System.out.println(getName() + " munches crunchy kibble.");
    }

    @Override
    public String toString() {
        return "Bulldog(" + getName() + ")";
    }
}

class Cat extends Animal implements Pet {
    public Cat(String name) {
        super(name);
    }

    @Override
    public void beFriendly() {
        System.out.println(getName() + " purrs and headbutts your hand.");
    }

    @Override
    public void play() {
        System.out.println(getName() + " chases a laser pointer.");
    }

    @Override
    public void eat() {
        System.out.println(getName() + " nibbles salmon pate.");
    }

    @Override
    public String toString() {
        return "Cat(" + getName() + ")";
    }
}

// Task:
// Add one more class such as Parrot that extends Animal and implements Pet.

class Monkey extends Animal implements Pet {

    public Monkey(String name) {
        super(name);
    }

    @Override
    public void beFriendly() {
        System.out.println(getName() + " Ooks.");
    }

    @Override
    public void play() {
        System.out.println(getName() + " swings on a branch.");
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats a banana.");
    }

    @Override
    public String toString() {
        return "Monkey(" + getName() + ")";
    }
}


class Orangutan extends Monkey {

    public Orangutan(String name) {
        super(name);
    }

    @Override
    public void beFriendly() {
        System.out.println(getName() + " Grumbles.");
    }

    @Override
    public String toString() {
        return "Orangutan(" + getName() + ")";
    }

}

