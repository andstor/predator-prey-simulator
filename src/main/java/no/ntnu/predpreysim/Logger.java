package no.ntnu.predpreysim;

import no.ntnu.predpreysim.actor.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Logs all actors in the predator pray simulator and writes data to a csv file.
 * The logger logs for each step the number of each actor, the amount of dead species of each actor and the amount of new born.
 *
 */
public class Logger {
    private StringBuilder builder;
    private ArrayList<Class> classes;
    private FieldStats fs;
    private File file;


    /**
     * Creates a logger
     */
    public Logger() {
        classes = new ArrayList<>();
        fillClasses();

        file = new File("stats.csv");

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
     * Logs a step
     * @param step the step number
     * @param field field where living actors is located
     */
    public void log(int step, Field field){


        fs.reset();
        builder.append("" + step);
        for(Class c  : classes){
            int populationCount = fs.getPopulationCount(field, c);
            builder.append(",").append(populationCount);
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
        }
        builder.append("\n");
        writeToFile(builder);
    }
}

