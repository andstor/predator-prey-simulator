package no.ntnu.predpreysim;

import java.util.List;

public class Grass extends Plant {

    // Characteristics shared by all grasss (class variables).

    // The age at which a grass can start to breed.
    private static final int BREEDING_AGE = 2;
    // The age to which a grass can live.
    private static final int MAX_AGE = 100;
    // The likelihood of a grass breeding.
    private static final double BREEDING_PROBABILITY = 0.9;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 50;

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
     * This is what the rabbit does most of the time - it runs
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Actor> newGrass)
    {
        incrementAge();
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
    protected Plant createPlant(boolean randomAge, Field field, Location location) {
        return new Grass(randomAge, field, location);
    }
}
