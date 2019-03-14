package no.ntnu.predpreysim;

import no.ntnu.predpreysim.actor.Actor;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IndividualLogger {
    private StringBuilder builder;
    private ArrayList<Class> classes;
    private FieldStats fs;

    private HashMap<Class, File> files = new HashMap<>();
    private HashMap<Actor, Integer> dayOfBirth = new HashMap<>();

    public IndividualLogger(){
        builder = new StringBuilder();
    }


    public void addNewActors(List<Actor> newActors, int step){
        for(Actor a : newActors){
            dayOfBirth.put(a, step);
        }
    }

    public void setDead(Actor actor, int death){
        try {
            int birth = dayOfBirth.get(actor);
            dayOfBirth.remove(actor);
            logg(actor.getClass(), birth, death);
        }
        catch(NullPointerException e){}
    }

    private void logg(Class cl, int birth, int death){
        StringBuilder sb = new StringBuilder();
        sb.append(birth).append(", ").append(death).append(", ").append(death - birth).append("\n");
        File file  = files.get(cl);
        if (file == null){
            file = new File(cl.getName()+".csv");
            initCsvFile(file);
            files.put(cl, file);
        }
        writeToFile(sb, file);
    }

    /**
     * Initializes the csv file with a header telling which data that will be stored
     */
    private void initCsvFile(File file){
        StringBuilder sb = new StringBuilder();
        sb.append("birth, death, age\n");
        writeToFile(sb, file);
    }

    /**
     * Write to file
     */
    private void writeToFile(StringBuilder sb, File file){
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file, true));
            pw.write(sb.toString());
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
