package games

import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import java.util.Random

/* This is the parent class which holds the game board (grid).
 * It contains all the functionality of the game except the rules (6 or 12 neighbors).
 * Two grids are maintained and we keep updating grid to nextGengrid each generation.
 * We swap those grid names before working on yet another generation.
 */
abstract class GameOfLife {
	var tweleveNeighbors = false
	var size:Integer = null
	var fileName:String = null
	var generations:Int = 10
	var printGen = 1
	var initLifeProb = 0.5
	var grid = Array.ofDim[Boolean](100,100)
	var nextGenGrid = Array.ofDim[Boolean](100,100)

	//Abstract method play, implemented in concrete classes SixNeighbors and TwelveNeighbors both
	def play():Unit  
	
	/* initializeFromFile() covers all the prescribed requirements and therefore looks kind of bulgy!
	 * First it sorts out the correct size field. If a user has provided a size which is greater than the 
	 * width or length of the file, it adjusts 'size' to be smaller value of them.
	 */
	def initializeFromFile() {
			val gridAsList = new ArrayBuffer[ArrayBuffer[Boolean]]
			val lines = Source.fromFile(fileName).getLines().toList
			val smallestLine = lines.reduceLeft((a, b) => if (a.length < b.length) a else b)			
      val smallerDim = if(lines.length < smallestLine.length) lines.length else smallestLine.length 
			if(size == null)  //size option NOT provided as a command line option
			  size = smallerDim
			else   //size option IS provided as a command line option
			  if(size > smallerDim) {
			    size = smallerDim
			    println("WARNING: The size of grid in the input file is smaller than the \"size\" option provided")
			  }
		  val gridAsBuffer = new ArrayBuffer[Array[Boolean]]
		  val nextGenGridAsBuffer = new ArrayBuffer[Array[Boolean]]
		  for(line <- lines) {
		    val row = new ArrayBuffer[Boolean]
		    for(c <- line)
		      row += (if(c=='X') true else false)
		    row.reduceToSize(size)  //This row-width shrinking is needed in case the user chose not to read the whole file by specifying a smaller size option
		    gridAsBuffer += row.toArray
		    nextGenGridAsBuffer += row.toArray
		  }
			gridAsBuffer.reduceToSize(size) //This column-length shrinking is needed in case the user chose not to read the whole file by specifying a smaller size option
		  grid = gridAsBuffer.toArray
		  nextGenGridAsBuffer.reduceToSize(size)
		  nextGenGrid = nextGenGridAsBuffer.toArray
	}
	/* initializeRandomly() function initializes the grid randomly in case the
	 * input file is not provided.
	 */
	def initializeRandomly() {
		size = if(size == null) 100 else size	 
		var rand = new Random(0)
		for(i <- 0 to size-1)
		  for(j <- 0 to size-1) 
				grid(i)(j) = if(rand.nextFloat < initLifeProb) true else false
	}
	//printGrid method simply prints the board nicely on 
	//the standard output.
	def printGrid() {
		var toggle = false
		for(row <- grid) {
			if(toggle)
				print(" ")
			for(cell <- row)
				if(cell)
					print("X ")
				else
					print("O ")
			println()
			toggle = !toggle
		}
	}
	/*processNeighbor() method returns the cell value at row i and column j.
	* Off the grid cells are assumed to be dead.
	*/
	def processNeighbor(i:Int, j:Int) = if(i<0 || i>=size || j<0 || j>=size || !grid(i)(j)) 0 else 1
}
