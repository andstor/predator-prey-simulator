/*
package no.ntnu.predpreysim;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

*/
/**
 * Hunters kill animals.
 * 
 * @author David J. Barnes and Michael Kölling and Poul Henricksen
 * @version 2016.02.29
 *//*

public class Human extends Animal
{
    // The field the hunter is in.
    private Field field;
    // The location of the hunter.
    private Location location;

    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 9;

    // Individual characteristics (instance fields).
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;

    */
/**
     * Constructor for objects of class Hunter
     *//*

    public Human(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
        if (randomAge) {
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        } else {
            foodLevel = RABBIT_FOOD_VALUE;
        }
    }

    */
/**
     * A hunter is always active.
     * @return true
     *//*

    public boolean isActive()
    {
        return true;
    }
    
    */
/**
     * Perform the hunter’s regular behavior.
     * Hunters kill a random number of animals in adjacent locations.
     * @param newActors A list for storing newly created actors.
     *//*

    public void act(List<Actor> newActors)
    {
        int kills = 0;
        List<Location> adjacent = field.adjacentLocations(location);
        Iterator<Location> it = adjacent.iterator();

        while(it.hasNext() && kills < MAX_KILLS) {
            Location where = it.next();
            Object actor = field.getObjectAt(where);
            if(actor instanceof Animal) {
                Animal animal = (Animal) actor;
                animal.setDead();
                kills++;
            }
        }
        // Try to move to a free adjacent location.
        Location newLocation = field.freeAdjacentLocation(location);
        if(newLocation != null) {
            setLocation(newLocation);
        }
    }


    */
/**
     * Return the maximum age for a fox.
     *
     * @return The animal's maximum age.
     *//*

    public int getMaxAge() {
        return MAX_AGE;
    }

    */
/**
     * Return the breeding age for a fox.
     *
     * @return The fox's breeding age.
     *//*

    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    */
/**
     * Return the breeding probability for a fox.
     *
     * @return The fox's probability age.
     *//*

    public double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    */
/**
     * Return the maximum litter size for a fox.
     *
     * @return The fox's maximum litter size.
     *//*

    public int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }


    @Override
    protected Animal createAnimal(boolean randomAge, Field field, Location location) {
        return null;
    }

    */
/**
     * Place the hunter at the new location in the given field.
     * @param newLocation The hunter's new location.
     *//*

    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
}
*/
