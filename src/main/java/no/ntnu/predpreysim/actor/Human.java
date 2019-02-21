package no.ntnu.predpreysim.actor;

import no.ntnu.predpreysim.Field;
import no.ntnu.predpreysim.Location;
import no.ntnu.predpreysim.Randomizer;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Hunters kill animals.
 *
 * @author David J. Barnes and Michael Kölling and Poul Henricksen
 * @version 2016.02.29
 */

public class Human extends Animal {
    // Characteristics shared by all humans (class variables).

    // The age at which a human can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a human can live.
    private static final int MAX_AGE = 1000;
    // The likelihood of a human breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The food value of a single rabbit. In effect, this is the
    // number of steps a 9fox can go before it has to eat again.
    private static final int FOOD_FACTOR = 9;
    private static final int MAX_FOOD_LEVEL = 25;


    // The allowed layer location.
    private int layer = 2;
    // Individual characteristics (instance fields).
    // The fox's food level, which is increased by eating rabbits.


    /**
     * Create a human. A human can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the human will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Human(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);

    }


    /**
     * Perform the human’s regular behavior.
     * Human kill a random number of animals in adjacent locations.
     *
     * @param newHumans A list for storing newly created actors.
     */

    public void act(List<Actor> newHumans) {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            giveBirth(newHumans);
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if (newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocationOnLayer(getLocation());
            }
            // See if it was possible to move.
            if (newLocation != null) {
                newLocation.setZindex(this.getLayerValue());
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Look for human adjacent to the current location.
     * Only the first live rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood() {
        return null;
    }


    /**
     * Return the maximum age for a fox.
     *
     * @return The animal's maximum age.
     */

    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int getMaxFoodValue() {
        return MAX_FOOD_LEVEL;
    }

    @Override
    public int getFoodFactor() {
        return FOOD_FACTOR;
    }


    /**
     * Return the breeding age for a fox.
     *
     * @return The fox's breeding age.
     */

    public int getBreedingAge() {
        return BREEDING_AGE;
    }


    /**
     * Return the breeding probability for a fox.
     *
     * @return The fox's probability age.
     */

    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * Return the maximum litter size for a fox.
     *
     * @return The fox's maximum litter size.
     */
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }


    /**
     * Create a new human. A human may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the human will have a random age.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    protected Organism createOrganism(boolean randomAge, Field field, Location location) {
        return new Human(randomAge, field, location);
    }

    @Override
    public int getLayerValue() {
        return this.layer;
    }

    @Override
    public int getFoodValue() {
        return 0;
    }

    @Override
    public void getEaten() {
        this.setDead();
    }
}

