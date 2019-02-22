package no.ntnu.predpreysim;

import no.ntnu.predpreysim.actor.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class Simulator {
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default height of the grid.
    private static final int DEFAULT_HEIGHT = 80;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 3;

    // List of actors in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private List<SimulatorView> views;

    private Logger logger;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_HEIGHT, DEFAULT_WIDTH, DEFAULT_DEPTH);
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param height Height of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int height, int width, int depth) {
        if (height <= 0 || width <= 0|| depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            height = DEFAULT_HEIGHT;
            width = DEFAULT_WIDTH;
            depth = DEFAULT_DEPTH;
        }

        actors = new ArrayList<>();
        field = new Field(height, width, depth);

        views = new ArrayList<>();

        //TODO Add to java fx

        // The grid view.
        SimulatorView view = new GridView(height, width);
        views.add(view);

        // The graph view.
        /*view = new GraphView(500, 150, 500);
        view.setColor(Rabbit.class, Color.BLACK);
        view.setColor(Fox.class, Color.RED);
        views.add(view);*/

        // The text view.
//        views.add(new TextView());

        // Setup a valid starting point.
        reset();

        //Create a logger
        logger = new Logger();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation() {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     *
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps) {
        for (int step = 1; step <= numSteps && views.get(0).isViable(field); step++) {
            simulateOneStep();
            // Uncomment to run slow simulation.
            //wait(100);
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep() {
        step++;

        // Provide space for newborn actors.
        List<Actor> newActor = new ArrayList<>();
        // Let all actors act.
        for (Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            actor.act(newActor);
            if (!actor.isActive()) {
                it.remove();
            }
        }

        // Add the newly born foxes and rabbits to the main lists.
        actors.addAll(newActor);

        updateViews();
        logger.log(step, field);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        actors.clear();
        for (SimulatorView view : views) {
            view.reset();
        }

        populate();
        updateViews();
    }

    /**
     * Update all existing views.
     */
    private void updateViews() {
        for (SimulatorView view : views) {
            view.showStatus(step, field);

        }
    }

    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate() {
        PopulationGenerator populationGenerator = new PopulationGenerator(views.get(0));
        populationGenerator.populate(field, actors);
    }

    public JComponent getSwingViews() {
        return views.get(0).getJComponentView();
    }

    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to cause a small delay.
     * @param milliseconds The number of milliseconds to wait.
     */
    private void wait(int milliseconds)
    {
        try
        {
            Thread.sleep(milliseconds);
        }
        catch (InterruptedException e)
        {
            // ignore the exception
        }
    }
}
