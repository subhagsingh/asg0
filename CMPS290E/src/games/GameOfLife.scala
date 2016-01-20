package games

import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import java.util.Random
import util.control.Breaks._

abstract class GameOfLife {
	var tweleveNeighbors = false
	var size:Integer = null
	var fileName:String = null
	var generations:Int = 10
	var printGen = 1
	var initLifeProb = 0.5
	var grid = Array.ofDim[Boolean](100,100)
	var nextGenGrid = Array.ofDim[Boolean](100,100)

	def play():Unit  
	
	/* initializeFromFile() provides many functionalities 
	 * and therefore looks bulgy.
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
		  for(line <- lines) {
		    val row = new ArrayBuffer[Boolean]
		    for(c <- line)
		      row += (if(c=='X') true else false)
		    gridAsBuffer += row.toArray
		  }
		  grid = gridAsBuffer.toArray
		  nextGenGrid = gridAsBuffer.toArray
	}

	def initializeRandomly() {
		size = if(size == null) 100 else size	 
		var rand = new Random(0)
		for(i <- 0 to size-1)
		  for(j <- 0 to size-1) 
				grid(i)(j) = if(rand.nextFloat < initLifeProb) true else false
	}
	//printGrid function simply prints the board nicely on 
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
	//Off the grid cells are assumed to be dead.
	def processNeighbor(i:Int, j:Int):Int = if(i<0 || i>=size || j<0 || j>=size || !grid(i)(j)) 0 else 1
}
