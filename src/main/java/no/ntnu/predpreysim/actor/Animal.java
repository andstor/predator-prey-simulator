package no.ntnu.predpreysim.actor;

import no.ntnu.predpreysim.Field;
import no.ntnu.predpreysim.Location;
import no.ntnu.predpreysim.Randomizer;

import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public abstract class Animal extends Organism implements Edible
{
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    private int layer = 1;

    private int foodLevel;

    /**
     * Create a new animal at location in field.
     * An animal can have an initial age of zero or a
     * random age.
     * 
     * @param randomAge If true, the animal will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
        if (randomAge) {
            setFoodLevel(rand.nextInt(this.getMaxFoodValue()));
        } else {
            setFoodLevel(this.getMaxFoodValue());
        }
    }

    /**
     * Return the maximum food value for a specific type of animal.
     * This is synonymous with how much it can eat.
     * @return The animal's maximum food value.
     */
    abstract public int getMaxFoodValue();

    abstract public int getFoodFactor();

    protected int getFoodLevel() {
        return this.foodLevel;
    }

    protected void setFoodLevel(int foodLevel) {
        if (foodLevel > this.getMaxFoodValue()) {
            this.foodLevel = this.getMaxFoodValue();
        } else {
            this.foodLevel = foodLevel;
        }
    }

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

    @Override
    public int getLayerValue() {
        return this.layer;
    }

    /**
     * Make this animal more hungry. This could result in the animal's death.
     */
    protected void incrementHunger() {
        setFoodLevel(getFoodLevel()-1);
        if (getFoodLevel() <= 0) {
            this.setDead();
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
        List<Location> free = field.getFreeAdjacentLocationsOnLayer(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            newborn.add(createOrganism(false, field, loc));
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


    protected void eat(Edible edible) {
        int foodValue = this.getFoodFactor() * edible.getFoodValue();
        int newFoodLevel = this.getFoodLevel() + foodValue;
        this.setFoodLevel(newFoodLevel);

        edible.getEaten();
    }



}
