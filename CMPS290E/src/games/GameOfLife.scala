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
	
	def initializeFromFile() {
			val gridAsList = new ArrayBuffer[ArrayBuffer[Boolean]]
			var i=0
			var wrongSize = false
			for(line <- Source.fromFile(fileName).getLines()) {
				val row = new ArrayBuffer[Boolean]
				var j=0
				val charLine = line.toCharArray()
				for(c <- charLine) {
					if(c == 'X') 
						row += true
					else 
						row += false
					j += 1
					if(size != null && j == size)	break
				}
				gridAsList += row
				if(size != null && j != size)
					wrongSize = true
				i += 1
				if(size != null && i == size) break
			}
				
			//If i and/or j is not equal to size, print warning
			if(size != null && i != size)
				wrongSize = true
			if(wrongSize)
				println("WARNING: The size of grid in the input file is smaller than the \"size\" option provided")
			
			size = i	
			
			grid = Array.ofDim[Boolean](gridAsList.size, gridAsList.size)
			nextGenGrid = Array.ofDim[Boolean](gridAsList.size, gridAsList.size)

			for(i <- 0 to gridAsList.size - 1) {  //Deep copy
				grid(i) = gridAsList(i).toArray
				Array.copy(grid(i),0,nextGenGrid(i),0,grid(i).size)
			}
	}
	def initializeRandomly() {
		if(size == null)	//Size option not present 
			size = 100
		var rand = new Random(0)
		for(i <- 0 to size-1)
		  for(j <- 0 to size-1) 
				if(rand.nextFloat < initLifeProb)
					grid(i)(j) = true
				else
					grid(i)(j) = false
	}
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
	def processNeighbor(i:Int, j:Int):Int = {
		if(i<0 || i>=size || j<0 || j>=size) 	//Off the grid cells are assumed to be dead.
			0
		if(grid(i)(j)) 1 else 0
	}
}
