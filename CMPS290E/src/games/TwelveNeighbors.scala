package games;

class TwelveNeighbors extends GameOfLife {
	
	def play() {
	  for(i <- 0 to size-1) {
	    	  for(j <- 0 to size-1) {
					nextGenGrid(i)(j) = grid(i)(j);	//Unchanged cells over generations
					//First tier neighbors
					var aliveFirstNeighborsCount = 0;
					aliveFirstNeighborsCount += processNeighbor(i-1, j);
					aliveFirstNeighborsCount += processNeighbor(i+1, j);
					aliveFirstNeighborsCount += processNeighbor(i, j-1);
					aliveFirstNeighborsCount += processNeighbor(i, j+1);
					var J = j-1;
					if(i%2 != 0) {
						J = j+1;
					}
					aliveFirstNeighborsCount += processNeighbor(i-1, J);
					aliveFirstNeighborsCount += processNeighbor(i+1, J);	
					//Second tier neighbors
					var aliveSecondNeighborsCount = 0;
					aliveSecondNeighborsCount += processNeighbor(i-2, j);
					aliveSecondNeighborsCount += processNeighbor(i+2, j);
					var jOnLeft = j-2
					var jOnRight = j+1;
					if(i%2 != 0) {
						jOnLeft = j-1;
						jOnRight = j+2;
					}
					aliveSecondNeighborsCount += processNeighbor(i-1, jOnLeft);
					aliveSecondNeighborsCount += processNeighbor(i+1, jOnLeft);
					aliveSecondNeighborsCount += processNeighbor(i-1, jOnRight);
					aliveSecondNeighborsCount += processNeighbor(i+1, jOnRight);
					
					//Sum
					var neighborSum = 1.0 * aliveFirstNeighborsCount + 0.3 * aliveSecondNeighborsCount;
					print("(" + aliveFirstNeighborsCount + "," + aliveSecondNeighborsCount + "," + neighborSum + ")");
					if(grid(i)(j)) {	//Alive cell
						if(neighborSum < 2.0 || neighborSum > 3.3)
							nextGenGrid(i)(j) = false;
					}
					else {					//Dead cell
						if(2.3 <= neighborSum && neighborSum <= 2.9)
							nextGenGrid(i)(j) = true;
					}							
				}
				print("| ");
			}
		}
}
