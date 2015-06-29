Conway's Game of Life
---------------------

This application is a small implementation of [Conway's Game of Life](https://en.wikipedia.org/wiki/Conway's_Game_of_Life).
Given a grid of both living and dead cells, cells will die and come to life as the game progresses through generations
based on some basic rules:

1. Any live cell with fewer than two live neighbours dies, as if caused by under-population.
2. Any live cell with two or three live neighbours lives on to the next generation.
3. Any live cell with more than three live neighbours dies, as if by overcrowding.
4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

The game can be seeded with randomly generated cells of a given size, or load an initial cell from any text file.

Cell grid example:

```
......O.
OOO...O.
......O.
........
...OO...
...OO...
```


The nature of these rules creates scenarios where all cells may die (extinction) and where no cells can change their
state (utopia).  In either of these scenarios, the game will stop.


### Running the tests

Gradle is the build tool used for this project.  Tests are written as [Spock Specifications](http://spockframework.org),
and are executed using the `test` task.

```
./gradlew test
```


### Building and Running the Application

The application is packaged into a single executable jar file using the gradle `package` task.

```
./gradlew package
```

Once packaged, a single executable jar will be generated in `./build/libs/`.  This jar can be executed with any Java 7+ runtime.

```
java -jar build/libs/game-of-life-0.0.0-SNAPSHOT-all.jar --file example_grid.txt
```

There are a few arguments that can be specified:

* **--randomize <size>** : Seed with a randomized cell grid of the specified size.  To generate a 10 by 10 cell, use the value `--randomize 10x10`
* **--file <filename>** : Read the grid definition from the specified file.  You must supply either --file or --randomize.
* **--generations <num>** : The number of generations to cycle through.  Defaults to 1.  Note that it may not be possible to reach the number of generations specified due to either extinction or utopia.

To cycle through 100 generations of a randomly seeded 50x50 grid, use the following command:

```
java -jar build/libs/game-of-life-0.0.0-SNAPSHOT-all.jar --randomize 50x50 --generations 100
```

