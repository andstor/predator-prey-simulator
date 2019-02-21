package no.ntnu.predpreysim;

import java.util.List;

/**
 * @author asty
 */
public class PredPreySim {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Simulator sim = new Simulator();
        sim.runLongSimulation();
//        System.exit(0);

//        Field field = new Field(20, 20, 2);
//        List<Location> location = field.adjacentLocationsInRadius(new Location(10, 10, 1), 2);
//        System.out.println(location);
    }

}
