package org.ajbrown.conway.core

import org.ajbrown.conway.core.GridFactory
import spock.lang.Specification

/**
 * A unit specification for {@GridFactory}
 * @author A.J. Brown <aj@ajbrown.org>
 */
class GridFactorySpec extends Specification {

    def random  = Mock(Random)
    def factory = new GridFactory( random )

    def "the assigned random is used if specified in the constructor" () {
        expect:
        new GridFactory( random ).rand == random
    }

    def "a new random will be assigned if not specified in the constructor"() {
        expect:
        new GridFactory().rand instanceof Random
    }

    def "a cell grid can be initialized from string representation" () {

        when:

        def grid = factory.stringInitializedGrid( stringValue )

        then:

        grid.rows == expectedValues.size()
        grid.cols == expectedValues.first().size()

        expectedValues.eachWithIndex{ row, rowNum ->
            row.eachWithIndex{ value, colNum ->
               assert grid.at( rowNum, colNum ) == value
            }
        }

        where:

        stringValue                 | expectedValues
        "..O.\nOOOO\n...."          | [[false,false,true,false],[true,true,true,true]  ,[false,false,false,false]]
        "..O.\nOO\n...."            | [[false,false,true,false],[true,true,false,false],[false,false,false,false]]
        ".OO.\nOO..\nO.O.\nOOOO"    | [[false,true,true,false] ,[true,true,false,false],[true,false,true,false],[true,true,true,true]]
    }

    def "Random is used to select cell states when using the randomizedGrid method"() {

        when:
        def result = factory.randomizedGrid( 3, 3 )

        then:
        9 * random.nextBoolean() >> true
        result.values().count{ it } == 9

    }
}
