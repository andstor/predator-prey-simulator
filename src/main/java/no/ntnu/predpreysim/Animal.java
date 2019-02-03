package no.ntnu.predpreysim;

import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public abstract class Animal implements Actor
{
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's age.
    private int age;
    
    /**
     * Create a new animal at location in field.
     * An animal can have an initial age of zero or a
     * random age.
     * 
     * @param randomAge If true, the animal will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(boolean randomAge, Field field, Location location)
    {
        if(randomAge) {
            age = rand.nextInt(getMaxAge());
        }
        else {
            age = 0;
        }
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Actor> newAnimals);
    
    /**
     * Return the maximum age for a specific type of animal.
     * @return The animal's maximum age.
     */
    abstract public int getMaxAge();

    /**
     * Return the breeding age for a specific type of animal.
     * @return The animal's breeding age.
     */
    abstract public int getBreedingAge();

    /**
     * Return the breeding probability for a specific type of animal.
     * @return The animal's probability age.
     */
    abstract public double getBreedingProbability();

    /**
     * Return the maximum litter size for a specific type of animal.
     * @return The animal's maximum litter size.
     */
    abstract public int getMaxLitterSize();

    /**
     * Return the animal's age.
     * @return The animal's age.
     */
    public int getAge()
    {
        return age;
    }
    
    /**
     * Create a new animal. An animal may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the animal will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    abstract protected Animal createAnimal(boolean randomAge,
                                           Field field, Location location);

    /**
     * Is the actor still active? This will be the case if the
     * animal is still alive.
     * @return true if the actor is active, false otherwise.
     */
    public boolean isActive()
    {
        return isAlive();
    }
    
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Increase the age. This could result in the animal's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }
    
    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newborn A list to add newly born animals to.
     */
    protected void giveBirth(List<Actor> newborn)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            newborn.add(createAnimal(false, field, loc));
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return getAge() >= getBreedingAge();
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
}
