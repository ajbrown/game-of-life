package org.ajbrown.conway.core

import spock.lang.Specification

/**
 * A unit specification for {@link Cytometer}.
 *
 * @author A.J. Brown <aj@ajbrown.org>
 */
class CytometerSpec extends Specification {

    def cytometer = new Cytometer()

    def "A living cell with 2 or 3 neighbors will survive" () {

        def grid = new GridFactory().stringInitializedGrid( grids )

        expect:

        cytometer.nextState( grid, 2, 2 )

        where:

        grids << [
                 ".....\n"
               + "..O..\n"
               + "..OO.\n"
               + "..O..\n"
               + ".....\n",

                 ".....\n"
               + "..O..\n"
               + "..O..\n"
               + "..O..\n"
               + ".....\n",

                 ".....\n"
               + "..O..\n"
               + "..OO.\n"
               + "..O..\n"
               + ".....\n",

                 ".....\n"
               + ".....\n"
               + "..OO.\n"
               + "..O..\n"
               + ".....\n",

                 ".....\n"
               + ".O...\n"
               + "..OO.\n"
               + "..O..\n"
               + ".....\n",

                 ".....\n"
               + ".....\n"
               + "..O..\n"
               + ".OO..\n"
               + ".....\n",

                 ".....\n"
               + ".....\n"
               + "..O..\n"
               + ".OOO.\n"
               + ".....\n",

                 "...\n"
               + ".O.\n"
               + "..O\n"
               + ".O.\n"
               + "...\n"
        ]
    }

    def "A living cell with less than 2 neighbors will die" () {

        def grid = new GridFactory().stringInitializedGrid( grids )

        expect:

        !cytometer.nextState( grid, 2, 2 )

        where:

        grids << [
                 ".....\n"
               + ".....\n"
               + "..O..\n"
               + "..O..\n"
               + ".....\n",

                 ".....\n"
               + "..O..\n"
               + "..O..\n"
               + ".....\n"
               + ".....\n",

                 ".....\n"
               + ".....\n"
               + "..OO.\n"
               + ".....\n"
               + ".....\n",

                 ".....\n"
               + ".....\n"
               + "..O..\n"
               + ".....\n"
               + ".....\n",

                 ".....\n"
               + ".....\n"
               + "..O..\n"
               + "..O..\n"
               + ".....\n",

                 ".....\n"
               + ".....\n"
               + "..O..\n"
               + ".O...\n"
               + ".....\n",

                 ".....\n"
               + ".....\n"
               + "..O..\n"
               + "...O.\n"
               + ".....\n",

                 "...\n"
               + "...\n"
               + "..O\n"
               + "...\n"
               + "...\n"
        ]
    }

    def "A living cell with more than 3 neighbors will die" () {

        def grid = new GridFactory().stringInitializedGrid( grids )

        expect:

        !cytometer.nextState( grid, 2, 2 )

        where:

        grids << [
                 ".....\n"
               + ".OOO.\n"
               + ".OOO.\n"
               + ".OOO.\n"
               + ".....\n",

                 ".....\n"
               + "..OO.\n"
               + "..OO.\n"
               + "...O.\n"
               + ".....\n",

                 ".....\n"
               + "..O..\n"
               + ".OOO.\n"
               + "..O..\n"
               + ".....\n",

                 ".....\n"
               + ".O.O.\n"
               + "..O..\n"
               + ".O.O.\n"
               + ".....\n",

                 ".....\n"
               + ".O...\n"
               + ".OO..\n"
               + ".OO..\n"
               + ".....\n",

                 ".....\n"
               + ".OOO.\n"
               + "..O..\n"
               + ".O...\n"
               + ".....\n",

                 ".....\n"
               + "..O..\n"
               + "..O..\n"
               + ".OOO.\n"
               + ".....\n",

                 "...\n"
               + ".OO\n"
               + ".OO\n"
               + "..O\n"
               + "...\n"
        ]
    }


    def "A dead cell comes alive if it has exactly 3 neighbors"() {

        def grid = new GridFactory().stringInitializedGrid( grids )

        expect:

        cytometer.nextState( grid, 2, 2 )

        where:

        grids << [
                 ".....\n"
               + ".OOO.\n"
               + ".....\n"
               + ".....\n"
               + ".....\n",

                 ".....\n"
               + "...O.\n"
               + "...O.\n"
               + "...O.\n"
               + ".....\n",

                 ".....\n"
               + "..O..\n"
               + ".O.O.\n"
               + ".....\n"
               + ".....\n",

                 ".....\n"
               + ".O...\n"
               + ".....\n"
               + ".O.O.\n"
               + ".....\n",

                 ".....\n"
               + ".O...\n"
               + ".O...\n"
               + ".O...\n"
               + ".....\n",

                 ".....\n"
               + ".OOO.\n"
               + ".....\n"
               + ".....\n"
               + ".....\n",

                 ".....\n"
               + ".....\n"
               + ".....\n"
               + ".OOO.\n"
               + ".....\n",

                 "...\n"
               + ".OO\n"
               + "..O\n"
               + "...\n"
               + "...\n"
        ]
    }

    def "A dead cell will remain dead if it does not have exactly 3 neighbors"() {

        def grid = new GridFactory().stringInitializedGrid( grids )

        expect:

        !cytometer.nextState( grid, 2, 2 )

        where:

        grids << [
                 ".....\n"
               + ".OOO.\n"
               + ".O.O.\n"
               + ".OOO.\n"
               + ".....\n",

                 ".....\n"
               + ".....\n"
               + ".....\n"
               + ".....\n"
               + ".....\n",

                 ".....\n"
               + "..OO.\n"
               + "...O.\n"
               + "...O.\n"
               + ".....\n",

                 ".....\n"
               + "..O..\n"
               + ".O.O.\n"
               + "..O..\n"
               + ".....\n",

                 ".....\n"
               + ".O.O.\n"
               + ".....\n"
               + ".O.O.\n"
               + ".....\n",

                 ".....\n"
               + ".O...\n"
               + ".O.O.\n"
               + ".O...\n"
               + ".....\n",

                 ".....\n"
               + "..O..\n"
               + ".....\n"
               + ".....\n"
               + ".....\n",

                 ".....\n"
               + "..O..\n"
               + ".....\n"
               + ".OOO.\n"
               + ".....\n",

                 "...\n"
               + "...\n"
               + "..O\n"
               + "...\n"
               + "...\n"
        ]
    }
}
