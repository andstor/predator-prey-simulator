package no.ntnu.predpreysim;

import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of plants.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public abstract class Plant implements Actor
{
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The allowed layer location.
    private int layer = 1;

    // Whether the plant is alive or not.
    private boolean alive;
    // The plant's field.
    private Field field;
    // The plant's position in the field.
    private Location location;
    // The plant's age.
    private int age;

    /**
     * Create a new plant at location in field.
     * An plant can have an initial age of zero or a
     * random age.
     *
     * @param randomAge If true, the plant will have random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Plant(boolean randomAge, Field field, Location location)
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
     * Make this plant act - that is: make it do
     * whatever it wants/needs to do.
     * @param newPlants A list to receive newly born plants.
     */
    abstract public void act(List<Actor> newPlants);

    /**
     * Return the maximum age for a specific type of plant.
     * @return The plant's maximum age.
     */
    abstract public int getMaxAge();

    /**
     * Return the breeding age for a specific type of plant.
     * @return The plant's breeding age.
     */
    abstract public int getBreedingAge();

    /**
     * Return the breeding probability for a specific type of plant.
     * @return The plant's probability age.
     */
    abstract public double getBreedingProbability();

    /**
     * Return the maximum litter size for a specific type of plant.
     * @return The plant's maximum litter size.
     */
    abstract public int getMaxLitterSize();

    /**
     * Return the plant's age.
     * @return The plant's age.
     */
    public int getAge()
    {
        return age;
    }

    /**
     * Create a new plant. An plant may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the plant will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    abstract protected Plant createPlant(boolean randomAge,
                                           Field field, Location location);

    /**
     * Is the actor still active? This will be the case if the
     * plant is still alive.
     * @return true if the actor is active, false otherwise.
     */
    public boolean isActive()
    {
        return isAlive();
    }

    /**
     * Check whether the plant is alive or not.
     * @return true if the plant is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Increase the age. This could result in the plant's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Indicate that the plant is no longer alive.
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
     * Check whether or not this plant is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newborn A list to add newly born plants to.
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
            newborn.add(createPlant(false, field, loc));
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
     * Return the plant's location.
     * @return The plant's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the plant at the new location in the given field.
     * @param newLocation The plant's new location.
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
     * Return the plant's field.
     * @return The plant's field.
     */
    protected Field getField()
    {
        return field;
    }

    @Override
    public int getLayerValue() {
        return this.layer;
    }
}
