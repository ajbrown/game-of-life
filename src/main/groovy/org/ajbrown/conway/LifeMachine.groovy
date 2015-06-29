package org.ajbrown.conway

import groovy.transform.CompileStatic
import org.ajbrown.conway.core.CellGrid
import org.ajbrown.conway.core.Cytometer

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkNotNull

/**
 * LifeMachine handles progressing an initial CellGrid through each of it's states based on The Game of Life. History
 * is maintained for each new generation of cells, making it possible to go backwards in time as well as forward.
 *
 * TODO - maximum history size to reduce memory footprint.
 *
 * @see https://en.wikipedia.org/wiki/Conway's_Game_of_Life
 * @author A.J. Brown <aj@ajbrown.org>
 */
@CompileStatic
@SuppressWarnings("GroovyAssignabilityCheck")
class LifeMachine {

    protected Long generation = 0
    protected Cytometer cytometer
    protected LinkedList<CellGrid> history

    protected CellGrid initialState

    LifeMachine( Cytometer cytometer ) {
        this.cytometer  = checkNotNull( cytometer )
        this.history = new LinkedList<CellGrid>()
    }

    /**
     * Initialize the LifeMachine with the provide grid.
     *
     * @param cellGrid
     * @return
     */
    def void initialize( CellGrid cellGrid ) {
        initialState = cellGrid
        reset()
    }

    def void reset() {
        history.clear()
        generation = 0
        history.add initialState
    }

    /**
     * Returns a {@CellGrid} by transitioning forward `num` states.  All states generated to produce the result will be
     * retained in the history.
     *
     * @param num
     * @return
     */
    def CellGrid forward( Integer steps = 1 ) {
        checkArgument steps > 0, 'steps must be greater than zero'

        if( !initialState ) {
            //TODO IllegalStateException is not the best exception to be throwing
            throw new IllegalStateException( 'LifeMachine must be initialized first' )
        }

        for( int i = 0; i < steps; i++ ) {
            generation++
            history.add transition( history.last() )
        }

        history.last()
    }

    /**
     * Returns the {@CellGrid} from `steps` in the history.
     * @param steps
     * @return
     */
    def CellGrid back( Integer steps = 1 ) {
        checkArgument steps > 0, 'steps given cannot be less than zero'
        checkArgument steps <= history.size(), 'cannot go back more than %d steps', history.size()

        for( int i = 0; i < steps; i++ ) {
            generation--;
            history.removeLast()
        }

        history.last()
    }

    /**
     * Transitions the given {@CellGrid} to it's next state using the {@Cytometer}.
     * @param grid
     * @return
     */
    protected CellGrid transition( CellGrid grid ) {
        def next = new CellGrid( grid.rows, grid.cols )

        for( int row = 0; row < grid.rows; row++ ) {
            for( int col = 0; col < grid.cols; col++ ) {
                next.put( row, col, cytometer.nextState( grid, row, col ) )
            }
        }

        next
    }
}
