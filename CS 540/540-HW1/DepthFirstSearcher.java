import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Depth-First Search (DFS)
 * 
 * You should fill the search() method of this class.
 */
public class DepthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze
	 *            initial maze.
	 */
	public DepthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main depth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD

		// explored list is a 2D Boolean array that indicates if a state
		// associated with a given position in the maze has already been
		// explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze
				.getNoOfCols()];

		/*
		 * int noOfNodesExpanded = 0; int maxDepthSearched = 0; int
		 * maxSizeOfFrontier = 0;
		 */

		// Stack implementing the Frontier list
		LinkedList<State> stack = new LinkedList<State>();

		stack.push(new State(maze.getPlayerSquare(), null, 0, 0));

		while (!stack.isEmpty()) {
			

			if (maxSizeOfFrontier < stack.size()) {
				maxSizeOfFrontier = stack.size();
			}

			State current = stack.pop();
			
			explored[current.getSquare().X][current.getSquare().Y] = true;

			// Update search info variables
			noOfNodesExpanded++;
			if (maxDepthSearched < current.getDepth()) {
				maxDepthSearched = current.getDepth();
			}

			// TODO return true if find a solution
			if (current.isGoal(maze)) {
				
				current = current.getParent();
				cost++;

				while (current.getSquare() != maze.getPlayerSquare()) {
					
					maze.setOneSquare(current.getSquare(), '.');
					
					current = current.getParent();
					
					cost++;
				}

				return true;
			}

			ArrayList<State> successors = current.getSuccessors(explored, maze);

			while (!successors.isEmpty()) {
				stack.push(successors.remove(0));
			}

			// TODO maintain the cost, noOfNodesExpanded (a.k.a.
			// noOfNodesExplored),
			// maxDepthSearched, maxSizeOfFrontier during
			// the search
			// TODO update the maze if a solution found

			// use stack.pop() to pop the stack.
			// use stack.push(...) to elements to stack
		}

		return false;
	}
}
