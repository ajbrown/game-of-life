package org.ajbrown.conway.core

import groovy.transform.CompileStatic

/**
 * Cytometer examines cells in a {@CellGrid} and determines their fate.
 *
 * @author A.J. Brown <aj@ajbrown.org>
 */
@CompileStatic
class Cytometer {

    /**
     * Determine the next state for the given cell coordinates based on The Game of Life's rules:
     *
     * <p>
     *     <ul>
     *         <li>A living cell can have only 2 or 3 neighbors to survive.</li>
     *         <li>A dead cell becomes living if it has <strong>exactly</strong> 3 neighbors</li>
     *     </ul>
     * </p>
     *
     * @param grid
     * @param row
     * @param col
     * @return
     */
    def Boolean nextState( CellGrid grid, int row, int col ) {
        def liveNeighbors = getNeighbors( grid, row, col ).count{ it }
        liveNeighbors == 3 || (liveNeighbors == 2 && grid.at( row, col ))
    }

    /**
     * Get the value of all neighbors to the specified cell.
     * @param grid
     * @param row
     * @param col
     * @return
     */
    def static List<Boolean> getNeighbors( CellGrid grid, int row, int col ) {
        List<Boolean> neighbors = []

        ((row-1)..(row+1)).each{ r ->
            ((col-1)..(col+1)).each { c ->

                //The cell must exist and not be itself.
                if( (r != row || c != col) && grid.exists( r, c ) ) {
                    neighbors << grid.at( r, c )
                }
            }
        }

        //Null values are cells that didn't exist.
        neighbors
    }
}
