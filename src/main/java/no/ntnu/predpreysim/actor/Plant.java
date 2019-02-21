package no.ntnu.predpreysim.actor;

import no.ntnu.predpreysim.ExponentialGenerator;
import no.ntnu.predpreysim.Field;
import no.ntnu.predpreysim.Location;
import no.ntnu.predpreysim.Randomizer;

import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of plants.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public abstract class Plant extends Organism implements Growable{
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    private static final ExponentialGenerator expRand = new ExponentialGenerator(0.4, rand);

    // The allowed layer location.
    private int layer = 0;

    private int MAX_SIZE = 5;
    // The plant's size
    private int size = 1;

    /**
     * Create a new plant at location in field.
     * An plant can have an initial age of zero or a
     * random age.
     *
     * @param randomAge If true, the plant will have random age.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Plant(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
        if (randomAge) {
            setSize(rand.nextInt(this.getMaxSize()));
        } else {
            setSize(this.getMaxSize());
        }
    }

    /**
     * Make this plant act - that is: make it do
     * whatever it wants/needs to do.
     *
     * @param newPlants A list to receive newly born plants.
     */
    abstract public void act(List<Actor> newPlants);


    /**
     * Return the breeding age for a specific type of plant.
     *
     * @return The plant's breeding age.
     */
    abstract public int getBreedingAge();

    /**
     * Return the breeding probability for a specific type of plant.
     *
     * @return The plant's probability age.
     */
    abstract public double getBreedingProbability();

    /**
     * Return the maximum litter size for a specific type of plant.
     *
     * @return The plant's maximum litter size.
     */
    abstract public int getMaxLitterSize();


    /**
     * Create a new plant. An plant may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the plant will have a random age.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    abstract protected Organism createOrganism(boolean randomAge,
                                               Field field, Location location);


    /**
     * Check whether or not this plant is to give birth at this step.
     * New births will be made into free adjacent locations.
     *
     * @param newborn A list to add newly born plants to.
     */
    protected void giveBirth(List<Actor> newborn) {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();

        int distance = expRand.nextInt();
        List<Location> free = field.getFreeAdjacentLocationsOnLayerWithDistance(getLocation(), distance);

        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            newborn.add(createOrganism(false, field, loc));
        }

//        int x = field.getHeight() - 1;
//        int y = field.getWidth() - 1;
//        int z = this.layer;
//        Location loc2 = new Location(x, y, z);
//        if (field.getObjectAt(loc2) == null) {
//            newborn.add(createPlant(false, field, loc2));
//        }

    }

    @Override
    public int getSize() {
        return this.size;
    }

    protected void setSize(int size) {
        if (this.getSize() > this.getMaxSize()) {
            this.size = this.getMaxSize();
        } else {
            this.size = size;
        }
    }

    @Override
    public void grow() {
        this.setSize(this.getSize() + 1);
    }

    protected int getMaxSize() {
        return MAX_SIZE;
    }


    /**
     * Generate a number representing the number of births,
     * if it can breed.
     *
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if (canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     *
     * @return true if the rabbit can breed, false otherwise.
     */
    protected boolean canBreed() {
        return getAge() >= getBreedingAge();
    }

    @Override
    public int getLayerValue() {
        return this.layer;
    }
}
