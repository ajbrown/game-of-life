package org.ajbrown.conway

import org.ajbrown.conway.core.CellGrid
import org.ajbrown.conway.core.Cytometer
import org.ajbrown.conway.core.GridFactory
import spock.lang.Specification

/**
 * A unit specification for {@LifeMachine}
 *
 * @author A.J. Brown <aj@ajbrown.org>
 */
class LifeMachineSpec extends Specification {

    def factory     = new GridFactory()
    def cytometer   = Mock(Cytometer)

    def lifeMachine = new LifeMachine( cytometer )

    def "when initialized, no history exists." () {

        def grid = new CellGrid(10, 10)

        when:
        lifeMachine.initialize( grid )

        then:
        lifeMachine.generation == 0L
        lifeMachine.history.size() == 1
    }

    def "LifeMachine must be initialized before transitions can occur" () {

        def grid = new CellGrid( 10, 10 )

        when:
        lifeMachine.forward()

        then:
        thrown(IllegalStateException)

        when:
        lifeMachine.initialize( grid )
        lifeMachine.forward()

        then:
        notThrown(IllegalStateException)
    }

    def "The Cytometer is used to generate the next state of each cell when transitioning forward" () {
        def grid = factory.randomizedGrid( 10, 10 )
        lifeMachine.initialize( grid )

        when:

        def result = lifeMachine.forward()

        then:
        100 * cytometer.nextState( grid, _, _ ) >> true
        result.values().count{ it } == 100
        lifeMachine.history.size() == 2
    }

    def "Previous states can be returned using the back() method"() {
        def grid = factory.randomizedGrid( 10, 10 )
        lifeMachine.initialize( grid )

        when:

        def result1 = lifeMachine.forward()
        lifeMachine.forward( 2 )
        def result2 =lifeMachine.back( 2 )

        then:
        lifeMachine.history.size() == 2
        result1 == result2
    }

    def "LifeMachine produces complete cell states based on The Game of Life rules"() {

        def lifeMachine = new LifeMachine( new Cytometer() )
        def grid = factory.stringInitializedGrid( input )
        def expected = factory.stringInitializedGrid( output )

        when:

        lifeMachine.initialize( grid )

        then:

        lifeMachine.forward() == expected

        where:

        input = "......O.\n" +
                "OOO...O.\n" +
                "......O.\n" +
                "........\n" +
                "...OO...\n" +
                "...OO..."

        output = ".O......\n" +
                ".O...OOO\n" +
                ".O......\n" +
                "........\n" +
                "...OO...\n" +
                "...OO..."
    }
}

