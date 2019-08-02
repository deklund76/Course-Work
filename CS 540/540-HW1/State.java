import java.util.ArrayList;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

	private Square square;
	private State parent;

	// Maintain the gValue (the distance from start)
	// You may not need it for the DFS but you will
	// definitely need it for AStar
	private int gValue;

	// States are nodes in the search tree, therefore each has a depth.
	private int depth;

	/**
	 * @param square
	 *            current square
	 * @param parent
	 *            parent state
	 * @param gValue
	 *            total distance from start
	 */
	public State(Square square, State parent, int gValue, int depth) {
		this.square = square;
		this.parent = parent;
		this.gValue = gValue;
		this.depth = depth;
	}

	/**
	 * @param explored
	 *            explored[i][j] is true if (i,j) is already explored
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @return all the successors of the current state
	 */
	public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {

		Square current = this.square;

		ArrayList<State> unvisited = new ArrayList<State>();

		// check all four neighbors (up, right, down, left)
		if (current.Y >= 0) {
			if (explored[current.X][current.Y - 1] == false
					&& maze.getSquareValue(current.X, current.Y - 1) != '%') {
				unvisited.add(new State(new Square(current.X, current.Y - 1),
						this, this.gValue + 1, this.depth + 1));
				
			}
		}

		if (current.X != maze.getNoOfRows() - 1) {
			if (explored[current.X + 1][current.Y] == false
					&& maze.getSquareValue(current.X + 1, current.Y) != '%') {
				unvisited.add(new State(new Square(current.X + 1, current.Y),
						this, this.gValue + 1, this.depth + 1));
				
			}
		}

		if (current.Y != maze.getNoOfCols() - 1) {
			if (explored[current.X][current.Y + 1] == false
					&& maze.getSquareValue(current.X, current.Y + 1) != '%') {
				unvisited.add(new State(new Square(current.X, current.Y + 1),
						this, this.gValue + 1, this.depth + 1));
			
			}
		}

		if (current.X != 0) {
			if (explored[current.X - 1][current.Y] == false
					&& maze.getSquareValue(current.X - 1, current.Y) != '%') {
				unvisited.add(new State(new Square(current.X - 1, current.Y),
						this, this.gValue + 1, this.depth + 1));
				
			}
		}

		return unvisited;
	}

	/**
	 * @return x coordinate of the current state
	 */
	public int getX() {
		return square.X;
	}

	/**
	 * @return y coordinate of the current state
	 */
	public int getY() {
		return square.Y;
	}

	/**
	 * @param maze
	 *            initial maze
	 * @return true is the current state is a goal state
	 */
	public boolean isGoal(Maze maze) {
		if (square.X == maze.getGoalSquare().X
				&& square.Y == maze.getGoalSquare().Y)
			return true;

		return false;
	}

	/**
	 * @return the current state's square representation
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * @return parent of the current state
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * You may not need g() value in the DFS but you will need it in A-star
	 * search.
	 * 
	 * @return g() value of the current state
	 */
	public int getGValue() {
		return gValue;
	}

	/**
	 * @return depth of the state (node)
	 */
	public int getDepth() {
		return depth;
	}
}
