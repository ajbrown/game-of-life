package org.ajbrown.conway.core

import org.ajbrown.conway.core.CellGrid
import spock.lang.Specification

/**
 * A unit specification for {@CellGrid}
 * @author A.J. Brown <aj@ajbrown.org>
 */
class CellGridSpec extends Specification {


    def "a CellGrid's state is initialized with the correct number of rows and cols"() {

        when:
        def grid = new CellGrid( rows, cols )

        then:

        grid.cols == cols
        grid.rows == rows

        grid.rowKeyList().size() == rows
        grid.columnKeyList().size() == cols

        where:

        rows    | cols
        1       | 1
        10      | 10
        25      | 17
        6       | 8

    }

}
