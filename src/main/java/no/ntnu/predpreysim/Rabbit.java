package no.ntnu.predpreysim;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class Rabbit extends Animal {
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int GRASS_FOOD_VALUE = 3;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // Individual characteristics (instance fields).
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the rabbit will have a random age.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
        if (randomAge) {
            foodLevel = rand.nextInt(GRASS_FOOD_VALUE);
        } else {
            foodLevel = GRASS_FOOD_VALUE;
        }
    }

    /**
     * This is what the rabbit does most of the time - it runs
     * around. Sometimes it will breed or die of old age.
     *
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Actor> newRabbits) {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            giveBirth(newRabbits);
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if (newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Return the maximum age for a rabbit.
     *
     * @return The animal's maximum age.
     */
    public int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * Return the breeding age for a rabbit.
     *
     * @return The rabbit's breeding age.
     */
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * Return the breeding probability for a rabbit.
     *
     * @return The rabbit's probability age.
     */
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * Return the maximum litter size for a rabbit.
     *
     * @return The rabbit's maximum litter size.
     */
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * Make this rabbit more hungry. This could result in the fox's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood() {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object actor = field.getObjectAt(where);
            if (actor instanceof Grass) {
                Grass grass = (Grass) actor;
                if (grass.isAlive()) {
                    grass.setDead();
                    foodLevel = GRASS_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the rabbit will have a random age.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    protected Animal createAnimal(boolean randomAge, Field field, Location location) {
        return new Rabbit(randomAge, field, location);
    }



}
