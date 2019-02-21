package no.ntnu.predpreysim.actor;

import no.ntnu.predpreysim.ExponentialGenerator;
import no.ntnu.predpreysim.Field;
import no.ntnu.predpreysim.Location;
import no.ntnu.predpreysim.Randomizer;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class Fox extends Animal {
    // Characteristics shared by all foxes (class variables).

    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a fox can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.06;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food factor for a fox.
    private static final int FOOD_FACTOR = 1;
    // The food value of a single fox.
    private static final int FOOD_VALUE = 15;
    // The max amount of food a single fox can have.
    private static final int MAX_FOOD_VALUE = 16;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    private static final ExponentialGenerator expRand = new ExponentialGenerator(0.4, rand);

    // The allowed layer location.
    private int layer = 2;
    // Individual characteristics (instance fields).

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Fox(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
    }

    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     *
     * @param newFoxes A list to return newly born foxes.
     */
    public void act(List<Actor> newFoxes) {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            giveBirth(newFoxes);

            Location foodLocation = findFood();
            Location newLocation;

            if (foodLocation != null) {
                Edible edible = (Edible) getField().getObjectAt(foodLocation);
                eat(edible);

                if (edible instanceof Organism) {
                    Organism organism = (Organism) edible;
                    organism.setDead();
                }
                newLocation = foodLocation;

            } else {

                // Search for direction of prey -->
//                int distance = expRand.nextInt();
//                List<Location> adjacent2 = field.adjacentLocationsByDistance(getLocation(), distance);


                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocationOnLayer(getLocation());
            }

            // Move towards a source of food if found.
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
     * Return the maximum age for a fox.
     *
     * @return The animal's maximum age.
     */
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int getMaxFoodValue() {
        return MAX_FOOD_VALUE;
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

    public int getFoodFactor() {
        return FOOD_FACTOR;
    }


    public int getFoodValue() {
        return FOOD_VALUE;
    }

    @Override
    public void getEaten() {
        this.setDead();
    }

    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood() {
        Field field = getField();

        boolean foundPrey = false;
        Location preyLocation = null;

        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext() && !foundPrey) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    foundPrey = true;
                    preyLocation = where;
                }
            }
        }

        return preyLocation;
    }

    /**
     * Create a new fox. A fox may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the fox will have a random age.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    @Override
    protected Animal createOrganism(boolean randomAge, Field field, Location location) {
        return new Fox(randomAge, field, location);
    }

    @Override
    public int getLayerValue() {
        return this.layer;
    }

}
