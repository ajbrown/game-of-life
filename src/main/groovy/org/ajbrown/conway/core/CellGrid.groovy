package org.ajbrown.conway.core

import com.google.common.base.Preconditions
import com.google.common.collect.ArrayTable
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode

/**
 * CellGrid represents a grid of cells for Conways game.  The "alive" state for each cell is stored as a boolean within
 * a table of values.
 *
 * @author A.J. Brown <aj@ajbrown.org>
 */
@CompileStatic
@EqualsAndHashCode
class CellGrid {
    static final String ALIVE_CHAR     = "O"
    static final String NOT_ALIVE_CHAR = "."

    final Integer rows
    final Integer cols
    final @Delegate ArrayTable<Integer, Integer, Boolean> state

    @SuppressWarnings("GroovyAssignabilityCheck")
    CellGrid( Integer rows, Integer columns ) {
        Preconditions.checkArgument rows > 0, 'Rows must be greater than zero.'
        Preconditions.checkArgument columns > 0, 'Columns must be greater than zero.'

        this.rows = rows
        this.cols = columns

        state = ArrayTable.create( (0..(rows-1)), (0..(columns-1)) )
    }

    /**
     * Determines if the specified cell coordinates exist in this grid.
     * @param row
     * @param col
     * @return
     */
    def Boolean exists( int row, int col ) {
        row >= 0 && row < rows && col >= 0 && col < cols
    }

    @Override
    public String toString() {
        state.rowMap().collect{ row ->
            row.value.collect{ col -> col.value ? ALIVE_CHAR : NOT_ALIVE_CHAR }.join("")
        }.join( "\n" )
    }
}
