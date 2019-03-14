package no.ntnu.predpreysim.actor;

import no.ntnu.predpreysim.Field;
import no.ntnu.predpreysim.Location;

import java.util.List;

public class Grass extends Plant implements Edible{

    // Characteristics shared by all grasss (class variables).

    // The age at which a grass can start to breed.
    private static final int BREEDING_AGE = 3;
    // The age to which a grass can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a grass breeding.
    private static final double BREEDING_PROBABILITY = 0.02;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    private static final int FOOD_VALUE = 2;


    // Individual characteristics (instance fields).


    /**
     * Create a new grass patch at location in field.
     * An plant can have an initial age of zero or a
     * random age.
     *
     * @param randomAge If true, the plant will have random age.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public Grass(boolean randomAge, Field field, Location location) {
        super(randomAge, field, location);
    }

    @Override
    /**
     * This is what the grass does most of the time - it spreads.
     * Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born grass.
     */
    public void act(List<Actor> newGrass)
    {
        incrementAge();
        grow();
        if(isAlive()) {
            giveBirth(newGrass);
        }
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    @Override
    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    @Override
    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    @Override
    protected Organism createOrganism(boolean randomAge, Field field, Location location) {
        return new Grass(randomAge, field, location);
    }

    @Override
    public int getFoodValue() {
        return FOOD_VALUE;
    }

    @Override
    public void getEaten() {
        this.setSize(getSize() - 1);
        if (this.getSize() == 0) {
            this.setDead();
        }
    }
}
