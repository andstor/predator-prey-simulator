package no.ntnu.predpreysim;

import no.ntnu.predpreysim.actor.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Logs all actors in the predator pray simulator and writes data to a csv file.
 * The logger logs for each step the number of each actor, the amount of dead species of each actor and the amount of new born.
 *
 * To make the logger work do these bothersome steps:
 * 0. add the classes to be logged to the fillClasses() method
 * 1. create a new Logger()
 * 2. for each step invoke Logger.logg()
 *
 */
public class Logger {
    private StringBuilder builder;
    private ArrayList<Class> classes;
    private HashMap<Class, Integer> lastPopulationCount;
    private FieldStats fs;
    private File file;


    /**
     * Creates a logger
     */
    public Logger() {
        classes = new ArrayList<>();
        fillClasses();

        file = new File("stats.csv");

        lastPopulationCount = new HashMap<>();
        resetLastPopulationCount();


        builder = new StringBuilder();
        initCsvFile();

        fs = new FieldStats();
    }

    /**
     * Adds actors to the classes collection
     */
    private void fillClasses(){
        classes.add(Fox.class);
        classes.add(Rabbit.class);
        classes.add(Grass.class);
        classes.add(Flower.class);
    }

    /**
     * Creates the last population count map and fills it with zeros for each key.
     */
    private void resetLastPopulationCount(){
        for(Class c : classes){
            lastPopulationCount.put(c, 0);
        }
    }

    /**
     * Logs a step
     * @param step the step number
     * @param newBorn collection of new born actors
     * @param field field where living actors is located
     */
    public void log(int step, Collection<Actor> newBorn, Field field){


        fs.reset();
        builder.append("" + step);
        for(Class c  : classes){
            int populationCount = fs.getPopulationCount(field, c);
            builder.append(",").append(populationCount);
            int newBornCount = geNewBornCount(newBorn, c);
            builder.append(",").append(newBornCount);
            int deadCount = getDeadCount(populationCount, newBornCount, c);
            builder.append(",").append(deadCount);
            lastPopulationCount.put(c, populationCount);
        }
        builder.append("\n");
        writeToFile(builder);
    }

    /**
     * Write to file
     */
    private void writeToFile(StringBuilder builder){
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.write(builder.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * Initializes the csv file with a header telling which data that will be stored
     */
    private void initCsvFile(){
        builder.append("Step");
        for(Class c : classes){
            String[] longName = c.getName().split("\\.");
            String name = longName[longName.length - 1];
            builder.append(",").append(name);
            builder.append(",NewBorn").append(name);
            builder.append(",Dead").append(name);
        }
        builder.append("\n");
        writeToFile(builder);
    }

    /**
     * Counts the number of new born actors of a particular class
     * @param nb collection of new born actors
     * @param c the class to find new born actors of
     * @return the number of new born actors
     */
    private int geNewBornCount(Collection<Actor> nb, Class c ){
        int count = 0;
        for(Actor newBorn : nb){
            if (newBorn.getClass() == c){
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the number of dead actors of a particular class
     */
    private int getDeadCount(int allive, int newBorn, Class c){
        int deadCount;
        deadCount = lastPopulationCount.get(c) + newBorn - allive;
        return deadCount;
    }
}

