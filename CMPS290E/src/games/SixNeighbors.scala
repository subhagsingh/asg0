package games;

class SixNeighbors extends GameOfLife {
	
	def play() {
	  for(i <- 0 to size-1) {
	    	  for(j <- 0 to size-1) {
				nextGenGrid(i)(j) = grid(i)(j);	//Copy unchanged cells over generations
				var aliveNeighborsCount = 0;
				aliveNeighborsCount += processNeighbor(i-1, j);
				aliveNeighborsCount += processNeighbor(i+1, j);
				aliveNeighborsCount += processNeighbor(i, j-1);
				aliveNeighborsCount += processNeighbor(i, j+1);
				var J = j-1;
				if(i%2 != 0) {
					J = j+1;
				}
				aliveNeighborsCount += processNeighbor(i-1, J);
				aliveNeighborsCount += processNeighbor(i+1, J);	
				print(aliveNeighborsCount + " ");
				if(grid(i)(j)) {		//Alive cell
					if(aliveNeighborsCount < 2 || aliveNeighborsCount > 3)
						nextGenGrid(i)(j) = false;	
				}
				else {					//Dead cell
					if(aliveNeighborsCount == 3)
						nextGenGrid(i)(j) = true;
				}
			}
			print("| ");
		}
	}
}
