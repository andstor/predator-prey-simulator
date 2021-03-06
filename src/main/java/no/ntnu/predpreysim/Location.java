package no.ntnu.predpreysim;

/**
 * Represent a location in a rectangular grid.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class Location {
    // Row and column positions.
    private int row;
    private int col;
    private int zindex;

    /**
     * Represent a row and column.
     *
     * @param row The row.
     * @param col The column.
     */
    public Location(int row, int col, int zindex) {
        this.row = row;
        this.col = col;
        this.zindex = zindex;
    }

    /**
     * Implement content equality.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Location) {
            Location other = (Location) obj;
            return row == other.getRow() && col == other.getCol() && zindex == other.getZindex();
        } else {
            return false;
        }
    }

    /**
     * Return a string of the form row,column,zindex
     *
     * @return A string representation of the location.
     */
    public String toString() {
        return row + "," + col + "," + zindex;
    }

    /**
     * Use the top 16 bits for the row value and the bottom for
     * the column. Except for very big grids, this should give a
     * unique hash code for each (row, col) pair.
     *
     * @return A hashcode for the location.
     */
    public int hashCode() {
        return (row << 16) + (col << 16) + zindex;
    }

    /**
     * @return The row.
     */
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return The column.
     */
    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    /**
     * @return The column.
     */
    public int getZindex() {
        return zindex;
    }

    public void setZindex(int zindex) {
        this.zindex = zindex;
    }
}
