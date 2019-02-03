package no.ntnu.predpreysim;

import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 * A class to generate the foxes and rabbits populations of the simulation.
 * 
 * @author David J. Barnes and Michael Kölling and Poul Henricksen
 * @version 2016.02.29
 */
public class PopulationGenerator
{
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.08;
    // The probability that a hunter will be created in any given grid position.
    private static final double HUNTER_CREATION_PROBABILITY = 0.01;
    // The probability that a hunter will be created in any given grid position.
    private static final double GRASS_CREATION_PROBABILITY = 0.9;

    /**
     * Constructor for objects of class PopulationGenerator
     * @param view The visualization.
     */
    public PopulationGenerator(SimulatorView view)
    {
        // Setup associations between the animals and colors
        // for the visualization.
        view.setColor(Rabbit.class, Color.orange);
        view.setColor(Fox.class, Color.blue);
        view.setColor(Hunter.class, Color.red);
        view.setColor(Grass.class, Color.green);
    }

    /**
     * Randomly populate the field with foxes and rabbits.
     * @param field The field to be populated.
     * @param actors A list of all the actors generated.
     */
    public void populate(Field field, List<Actor> actors)
    {
        Random rand = Randomizer.getRandom();
        for(int row = 0; row < field.getHeight(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Actor fox = new Fox(true, field, location);
                    actors.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Actor rabbit = new Rabbit(true, field, location);
                    actors.add(rabbit);
                }
                else if(rand.nextDouble() <= HUNTER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Actor hunter = new Hunter(field, location);
                    actors.add(hunter);
                }
                /*else if(rand.nextDouble() <= GRASS_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Actor grass = new Grass(true, field, location);
                    actors.add(grass);
                }*/
                // else leave the location empty.
            }
        }
    }
}
