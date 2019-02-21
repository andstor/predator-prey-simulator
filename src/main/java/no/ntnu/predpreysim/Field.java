package no.ntnu.predpreysim;

import no.ntnu.predpreysim.actor.Actor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represent a rectangular grid of field positions.
 * Each position is able to store a single actor.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class Field {
    // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();

    // The height and width of the field.
    private int height, width, depth;
    // Storage for the actors.
    private Object[][][] field;

    /**
     * Represent a field of the given dimensions.
     *
     * @param height The height of the field.
     * @param width  The width of the field.
     */
    public Field(int height, int width, int depth) {
        this.height = height;
        this.width = width;
        this.depth = depth;
        field = new Object[height][width][depth];
    }

    /**
     * Empty the field.
     */
    public void clear() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                for (int zindex = 0; zindex < depth; zindex++) {
                    field[row][col][zindex] = null;
                }
            }
        }
    }

    /**
     * Clear the given location.
     *
     * @param location The location to clear.
     */
    public void clear(Location location) {
        field[location.getRow()][location.getCol()][location.getZindex()] = null;
    }

    /**
     * Place an actor at the given location.
     * If there is already an actor at the location it will
     * be lost.
     *
     * @param actor The actor to be placed.
     * @param row   Row coordinate of the location.
     * @param col   Column coordinate of the location.
     */
    public void place(Actor actor, int row, int col, int zindex) {
        place(actor, new Location(row, col, zindex));
    }

    /**
     * Place an actor at the given location.
     * If there is already an actor at the location it will
     * be lost.
     *
     * @param actor    The actor to be placed.
     * @param location Where to place the actor.
     */
    public void place(Actor actor, Location location) {
//        field[location.getRow()][location.getCol()][actor.getLayerValue()] = (Object) actor;
        field[location.getRow()][location.getCol()][location.getZindex()] = (Object) actor; //TODO this is a problem
    }

    /**
     * Return the actor at the given location, if any.
     *
     * @param location Where in the field.
     * @return The actor at the given location, or null if there is none.
     */
    public Object getObjectAt(Location location) {
        return getObjectAt(location.getRow(), location.getCol(), location.getZindex());
    }

    /**
     * Return the actor at the given location, if any.
     *
     * @param row The desired row.
     * @param col The desired column.
     * @return The actor at the given location, or null if there is none.
     */
    public Object getObjectAt(int row, int col, int zindex) {
        return field[row][col][zindex];
    }

    /**
     * Generate a random location that is adjacent to the
     * given location, or is the same location.
     * The returned location will be within the valid bounds
     * of the field.
     *
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location randomAdjacentLocation(Location location) {
        List<Location> adjacent = adjacentLocations(location);
        return adjacent.get(0);
    }

    /**
     * Get a shuffled list of the free adjacent locations.
     *
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location) {
        List<Location> free = new LinkedList<>();
        List<Location> adjacent = adjacentLocations(location);
        for (Location next : adjacent) {
            if (getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }

    /**
     * Get a shuffled list of the free adjacent locations, on the existing layer (zindex).
     *
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocationsOnLayer(Location location) {
        List<Location> free = new LinkedList<>();
        List<Location> adjacent = adjacentLocationsOnLayer(location);
        for (Location next : adjacent) {
            if (getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }

    /**
     * Get a shuffled list of the free adjacent locations, on the existing layer (zindex).
     *
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocationsOnLayerWithDistance(Location location, int distance) {
        List<Location> free = new LinkedList<>();
        List<Location> adjacent = adjacentLocationsOnLayerByDistance(location, distance);
        for (Location next : adjacent) {
            if (getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }

    /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the field.
     *
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocation(Location location) {
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location);
        if (free.size() > 0) {
            return free.get(0);
        } else {
            return null;
        }
    }

    /**
     * Try to find a free location that is adjacent to the
     * given location, on the existing layer (zindex). If there is none, return null.
     * The returned location will be within the valid bounds
     * of the field.
     *
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocationOnLayer(Location location) {
        // The available free ones.
        List<Location> free = getFreeAdjacentLocationsOnLayer(location);
        if (free.size() > 0) {
            return free.get(0);
        } else {
            return null;
        }
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     *
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location) {
        return adjacentLocationsByDistance(location, 1);
    }

    /**
     * Return a shuffled list of locations on the existing layer (zindex), adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     *
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocationsOnLayer(Location location) {
        return adjacentLocationsOnLayerByDistance(location, 1);
    }

    /**
     * Return a shuffled list of locations on the existing layer (zindex), adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     *
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocationsOnLayerByDistance(Location location, int distance) {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        if (location != null) {
            int row = location.getRow();
            int col = location.getCol();
            int zindex = location.getZindex();

            for (int roffset = (-distance); roffset <= (distance); roffset++) {
                int nextRow = row + roffset;
                if (nextRow >= 0 && nextRow < height) {


                    for (int coffset = (-distance); coffset <= (distance); coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if (nextCol >= 0 && nextCol < width
                                && (!(roffset > -distance && roffset < distance) || (!(coffset > -distance && coffset < distance)))) {

                            locations.add(new Location(nextRow, nextCol, zindex));
                        }
                    }
                }
            }

            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }
    /**
     * Return a shuffled list of locations on the existing layer (zindex), adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     *
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocationsOnLayerInRadius(Location location, int radius) {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        if (location != null) {
            int row = location.getRow();
            int col = location.getCol();
            int zindex = location.getZindex();

            for (int roffset = (-radius); roffset <= (radius); roffset++) {
                int nextRow = row + roffset;
                if (nextRow >= 0 && nextRow < height) {


                    for (int coffset = (-radius); coffset <= (radius); coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if (nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {

                            locations.add(new Location(nextRow, nextCol, zindex));
                        }
                    }
                }
            }

            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }


    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     *
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocationsByDistance(Location location, int distance) {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        if (location != null) {
            int row = location.getRow();
            int col = location.getCol();
            int zindex = location.getZindex();

            for (int roffset = -distance; roffset <= distance; roffset++) {
                int nextRow = row + roffset;
                if (nextRow >= 0 && nextRow < height) {

                    for (int coffset = -distance; coffset <= distance; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if (nextCol >= 0 && nextCol < width) {

                            for (int zoffset = -zindex; zoffset <= (this.depth - zoffset); zoffset++) {
                                int nextZindex = zindex + zoffset;
                                if (nextZindex >= 0 && nextZindex < depth
                                        && (!(roffset > -distance && roffset < distance) || (!(coffset > -distance && coffset < distance)))
                                        && (zoffset != 0)) {

                                    locations.add(new Location(nextRow, nextCol, nextZindex));
                                }
                            }
                        }
                    }
                }
            }

            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     *
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocationsInRadius(Location location, int radius) {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        if (location != null) {
            int row = location.getRow();
            int col = location.getCol();
            int zindex = location.getZindex();

            for (int roffset = -radius; roffset <= radius; roffset++) {
                int nextRow = row + roffset;
                if (nextRow >= 0 && nextRow < height) {

                    for (int coffset = -radius; coffset <= radius; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if (nextCol >= 0 && nextCol < width) {

                            for (int zoffset = -zindex; zoffset <= (this.depth - zoffset); zoffset++) {
                                int nextZindex = zindex + zoffset;
                                if (nextZindex >= 0 && nextZindex < depth && (roffset != 0 || coffset != 0 || zoffset != 0)) {

                                    locations.add(new Location(nextRow, nextCol, nextZindex));
                                }
                            }
                        }
                    }
                }
            }

            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }


    /**
     * Return the height of the field.
     *
     * @return The heighth of the field.
     */
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Return the width of the field.
     *
     * @return The width of the field.
     */
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Return the depth of the field.
     *
     * @return The depth of the field.
     */
    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
