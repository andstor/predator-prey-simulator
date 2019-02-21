package no.ntnu.predpreysim;

import java.util.Random;

public class ExponentialGenerator {

    private final Random rand;
    private final double lambda;

    public ExponentialGenerator(double rate, Random rand) {
        this.rand = rand;
        this.lambda = rate;
    }

    public int nextInt() {
        return Math.round((int) this.nextDouble());
    }

    public double nextDouble() {
        return  Math.log(1-rand.nextDouble())/(-lambda);
    }
}
