package games;

import org.apache.commons.cli._

object Play {
	var game:GameOfLife = new SixNeighbors();

	def main(args: Array[String]) {
			val options = new Options();
			options.addOption("12", false, "use the 12 neighbor rules (default is the 6 neighbor rules)");
			options.addOption("size", true, "specify the size of the grid to be n X n (default is 100)");
			options.addOption("f", true, "read in the initial configuration from the specified file (default is random generation of grid)");
			options.addOption("g", true, "specifiy the number of generations to simulate (default is 10)");
			options.addOption("p", true, "specify that every nth generation should be printed (for plain text output only, default is 1)");
			options.addOption("i", true, "specify the probabilty of a cell being alive in the initial configuration if no file is provided (default is .5)");
			options.addOption("help", false, "print this message");
			
			val parser = new DefaultParser();
			val cmd = parser.parse(options, args);
			
			if(cmd.hasOption("help")) {
//				HelpFormatter formatter = new HelpFormatter();
//				formatter.printHelp( "GOL", options );
				System.exit(0);
			}
			if(cmd.hasOption("12")) {
				game.tweleveNeighbors = true;
				game = new TwelveNeighbors();
			}
			if(cmd.hasOption("size")) {
				var sz = Integer.valueOf(cmd.getOptionValue("size"));
				if(sz != null && sz >= 0) {
					game.size = sz;
					game.grid = Array.ofDim[Boolean](game.size,game.size);
					game.nextGenGrid = Array.ofDim[Boolean](game.size,game.size);
				}
				else {
					println("Please specify a valid size (default is 100 when not specified)");
					System.exit(1);
				}
			}
			if(cmd.hasOption("f")) {
//			  println("===============TRUE================")
				val name = cmd.getOptionValue("f");
				if(name != null) {
					game.fileName = name;
				}
				else {
					System.out.println("Please specify a valid file name (deafult is randomly generated grid)");
				}
			}
			if(cmd.hasOption("g")) {
				val g = Integer.valueOf(cmd.getOptionValue("g"));
				if(g != null && g > 0) {
					game.generations = g;
				}
				else {
					System.out.println("Please specify a valid number of generations (default is 10 generations)");
				}
			}
			if(cmd.hasOption("p")) {
				val p = Integer.valueOf(cmd.getOptionValue("p"));
				if(p != null && p > 0) {
					game.printGen = p;
				}
				else {
					System.out.println("Please specify a valid number of generations (default is 10 generations)");
				} 
			}
			if(cmd.hasOption("i")) {
				val i = cmd.getOptionValue("i");
				if(i != null && i.toDouble >= 0 && i.toDouble <= 1) {
					game.initLifeProb = i.toDouble;
				}
				else {
					System.out.println("Please specify a valid number of generations (default is 10 generations)");
				} 
			}
			//Conflicting option warning
			if(cmd.hasOption("f") && cmd.hasOption("i"))
				System.err.println("WARNING: Option i ignored as input file is provided with option f");

			//At this point all the options are read and processed.

			System.out.println("-12:" + game.tweleveNeighbors + "\nsize:" + game.size + "\nf:" + game.fileName 
					+ "\ng:" + game.generations + "\np:" + game.printGen + "\ni:" + game.initLifeProb);
			if(game.printGen > game.generations)
				System.err.println("WARNING: You have chosen p>g, nothing will be printed!");
			//Initialize the grid: from file or randomly
			if(game.fileName != null) {
				game.initializeFromFile();
			}
			else {
				game.initializeRandomly();
			}
			//Print generations as game proceeds
			if(game.printGen == 1) {
				System.out.println("\nGeneration 1");
				System.out.println("------------");
				game.printGrid();
			}
			for(i <- 1 to game.generations) {
				game.play()
				val t = game.grid
				game.grid = game.nextGenGrid;
				game.nextGenGrid = t;
				if((i+1)%game.printGen == 0) {
					System.out.println("\n\nGeneration " + (i+1));
					System.out.println("------------");
					game.printGrid();
				}				
			}
	}
}
