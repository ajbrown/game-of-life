package org.ajbrown.conway

import org.ajbrown.conway.core.CellGrid
import org.ajbrown.conway.core.Cytometer
import org.ajbrown.conway.core.GridFactory
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options

import static java.lang.System.exit

/**
 * The GameOfLife main class.
 *
 *  TODO - abstract printing of each generation
 *
 * @author A.J. Brown <aj@ajbrown.org>
 */
class GameOfLife {

    final static gridFactory = new GridFactory()
    final static cytometer   = new Cytometer()

    public static void main( String[] args ) {
        def opts = new Options()
        opts.addOption( "r", "randomize", true, "Start the game with a randomly generated grid of the specified size.  Example: --randomize 12x12" )
        opts.addOption( "f", "file", true, "Start the game with a cell in the specified file." )
        opts.addOption( "g", "generations", true, "The number of generations to print. Defaults to 1")

        def cmd = new DefaultParser().parse( opts, args )

        CellGrid initialGrid
        Integer genrations = cmd.getOptionValue( "g", "1" ).toInteger()

        if( cmd.hasOption( "r" ) && cmd.getOptionValue("r").contains("x") ) {
            def size = cmd.getOptionValue( "r" ).split("x", 2)
            initialGrid = gridFactory.randomizedGrid( size[0].toInteger(), size[1].toInteger() )

        } else if( cmd.hasOption( "f" ) ) {

            //TOOD handle exceptions if file can't be read
            def string = new File( cmd.getOptionValue( "f" ) )?.text
            initialGrid = gridFactory.stringInitializedGrid( string )

        } else {
            System.err.println( "You must specify either --randomize or --file options" )
            printUsage( opts )
            exit(0)
            return
        }

        def life = new LifeMachine( cytometer )
        life.initialize( initialGrid )

        println "Initial Population"
        println "-----------------------"
        println initialGrid
        println "\n"

        CellGrid current  = null
        CellGrid previous = initialGrid

        for( int i = 0; i < genrations; i++ ) {

            current = life.forward()

            printGeneration( current, life.generation )

            //Extinction occurs when a grid is produce with no life.
            if( !current.values().contains( true ) ) {
                println "\n\nExtinction occurred at generation ${life.generation}. No more life is possible.\n\n"
                break
            }

            //Uptopia occurs when cells align in a way that their state will never change.  This is usually represented
            //by ring formations, where the cell(s) in the center will never have enough neighbors and the ring cells
            //always have two.
            if( current == previous ) {
                println "\n\nUtopia occurred at generation ${life.generation-1}.  All living cells will live forever, and no more new cells can come to life."
                break
            }

            previous = current
        }
    }

    /**
     * Print the cell grid.
     *
     * @param grid
     * @param generation
     * @return
     */
    private static void printGeneration( CellGrid grid, Long generation ) {
        println "Generation ${generation}"
        println "-----------------"
        println grid
        println "\n"
    }

    private static void printUsage( Options opts ) {
        new HelpFormatter().printHelp( "java -jar game-of-life.jar", opts )
    }

}
