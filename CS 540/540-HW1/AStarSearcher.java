import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze
	 *            initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {

		// FILL THIS METHOD

		// explored list is a Boolean array that indicates if a state associated
		// with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze
				.getNoOfCols()];
		// ...

		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();

		double initDistance = Math.abs(maze.getPlayerSquare().X
				- maze.getGoalSquare().X)
				+ Math.abs(maze.getPlayerSquare().Y - maze.getGoalSquare().Y);

		frontier.add(new StateFValuePair(new State(maze.getPlayerSquare(),
				null, 0, 0), initDistance));

		while (!frontier.isEmpty()) {

			if (maxSizeOfFrontier < frontier.size()) {
				maxSizeOfFrontier = frontier.size();
			}

			StateFValuePair current = frontier.poll();

			State curstate = current.getState();

			explored[curstate.getSquare().X][curstate.getSquare().Y] = true;

			// Update search info variables
			noOfNodesExpanded++;
			if (maxDepthSearched < curstate.getDepth()) {
				maxDepthSearched = curstate.getDepth();
			}

			// TODO return true if find a solution
			if (curstate.isGoal(maze)) {

				curstate = curstate.getParent();
				cost++;

				while (curstate.getSquare() != maze.getPlayerSquare()) {

					maze.setOneSquare(curstate.getSquare(), '.');

					curstate = curstate.getParent();

					cost++;
				}

				return true;
			}

			ArrayList<State> successors = current.getState().getSuccessors(
					explored, maze);

			while (!successors.isEmpty()) {
				State sfvp = successors.remove(0);
				double heur = Math.abs(sfvp.getX() - maze.getGoalSquare().X)
						+ Math.abs(sfvp.getY() - maze.getGoalSquare().Y);
				frontier.add(new StateFValuePair(sfvp, sfvp.getGValue() + heur));

				System.out.println("added to queue");
			}

		}

		return false;
	}
}
