package org.ajbrown.conway.core

import groovy.transform.CompileStatic

import static com.google.common.base.Preconditions.checkNotNull

/**
 * A factory class for generating {@link CellGrid} based on various input.
 *
 * @author A.J. Brown <aj@ajbrown.org>
 */
@CompileStatic
class GridFactory {

    final Random rand

    GridFactory( Random random = null ) {
        rand = random ?: new Random()
    }

    /**
     * Generate a grid from it's string representation.  A line-feed delimited list of "O" and "." is expected.
     *
     * The grid will be based on the largest number of columns specified in any row.  If a row contains less values than
     * columns in the grid, the remaining values will be considered dead cells.
     *
     * <p>
     *   <pre>
     *   ......O.
     *   OOO...O.
     *   ......O.
     *   ........
     *   ...OO...
     *   ...OO...
     *   </pre>
     * </p>
     *
     * @param grid
     * @return
     */
    def CellGrid stringInitializedGrid( String gridString ) {
        checkNotNull( gridString )

        def input = gridString.split( "\n" ).collect{ String it -> it.trim() }
        def rows = input.size()

        //The number of columns is derived from the row with the most characters
        def cols = input*.size().max()

        def grid = new CellGrid( rows, cols )
        input.eachWithIndex{ String row, Integer rowNum ->
            row.padRight( cols, "." ).toList().eachWithIndex{ String ch, Integer colNum ->
                grid.put( rowNum, colNum, CellGrid.ALIVE_CHAR == ch )
            }
        }

        grid
    }

    /**
     * Generates a grid of the specified size and initializes it with random values.
     * @param rows
     * @param columns
     * @return
     */
    def CellGrid randomizedGrid( Integer rows, Integer columns ) {
        checkNotNull( rows )
        checkNotNull( columns )

        def grid = new CellGrid( rows, columns )

        for( int row = 0; row < rows; row++ ) {
            for( int col = 0; col < columns; col++ ) {
                grid.put( row, col, rand.nextBoolean() )
            }
        }

        grid
    }

}
