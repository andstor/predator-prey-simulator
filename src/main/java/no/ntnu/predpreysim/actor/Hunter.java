package no.ntnu.predpreysim.actor;

import no.ntnu.predpreysim.Field;
import no.ntnu.predpreysim.Location;

import java.util.Iterator;
import java.util.List;


/**
 * Hunters kill animals.
 *
 * @author David J. Barnes and Michael Kölling and Poul Henricksen
 * @version 2016.02.29
 */

public class Hunter extends Human {
    // The maximum number of animals to be killed at any one step.
    private static final int MAX_KILLS = 2;
    /**
     * Constructor for objects of class Hunter
     */

    public Hunter(Field field, Location location) {
        super(false, field, location);
    }

    /**
     * A hunter is always active.
     *
     * @return true
     */

    public boolean isActive() {
        return true;
    }



    /**
     * Perform the hunter’s regular behavior.
     * Hunters kill a random number of animals in adjacent locations.
     *
     * @param newHunters A list for storing newly created actors.
     */

    public void act(List<Actor> newHunters) {
        int kills = 0;
        List<Location> adjacent = getField().adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();

        while (it.hasNext() && kills < MAX_KILLS) {
            Location where = it.next();
            Object actor = getField().getObjectAt(where);
            if (actor instanceof Rabbit || actor instanceof Fox) {
                Organism organism = (Organism) actor;
                organism.setDead();
                kills++;
            }
        }
        // Try to move to a free adjacent location.
        Location newLocation = getField().freeAdjacentLocationOnLayer(getLocation());
        if (newLocation != null) {
            setLocation(newLocation);
        }
    }
}
