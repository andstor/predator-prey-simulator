package no.ntnu.predpreysim;

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

    }

    public static float invSqrt(float x) {
        float xhalf = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat(i);
        x *= (1.5f - xhalf * x * x);
        return x;
    }
}
