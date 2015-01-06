import java.awt.Color;
import java.util.*;

public class Board {

	public static boolean iAmDebugging = true;
	private ArrayList<Connector> moves;
	private Color[] colors;
	private int movesIndex;
	
	// Initialize an empty board with no colored edges.
	public Board ( ) {
		moves = new ArrayList<Connector>();
		colors = new Color [15];
		Arrays.fill(colors, Color.WHITE);
		movesIndex = 0;
	}
	
	// Add the given connector with the given color to the board.
	// Unchecked precondition: the given connector is not already chosen 
	// as RED or BLUE.
	public void add (Connector cnctr, Color c) {
		moves.add(cnctr);
		colors[movesIndex] = c;
		movesIndex++;
	}
	
	// Set up an iterator through the connectors of the given color, 
	// which is either RED, BLUE, or WHITE. 
	// If the color is WHITE, iterate through the uncolored connectors.
	// No connector should appear twice in the iteration.  
	public Iterator<Connector> connectors (Color c) {
		ArrayList<Connector> connectorsByColor = new ArrayList<Connector>();
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] == c) {
				connectorsByColor.add(moves.get(i));
			}
		}
		Iterator<Connector> connectorsByColorIter= connectorsByColor.iterator();
		return connectorsByColorIter;
	}
	
	// Set up an iterator through all the 15 connectors.
	// No connector should appear twice in the iteration.  
	public Iterator<Connector> connectors ( ) {
		Iterator<Connector> connectorsIter = moves.iterator();
		return connectorsIter;
	}
	
	// Return the color of the given connector.
	// If the connector is colored, its color will be RED or BLUE;
	// otherwise, its color is WHITE.
	public Color colorOf (Connector e) {
		int index = moves.indexOf(e);
		if(index>-1)
			if (colors[index] == Color.BLUE || colors[index] == Color.RED)
				return colors[index];
		return Color.WHITE;
	}
	
	// Unchecked prerequisite: cnctr is an initialized uncolored connector.
	// Let its endpoints be p1 and p2.
	// Return true exactly when there is a point p3 such that p1 is adjacent
	// to p3 and p2 is adjacent to p3 and those connectors have color c.
	public boolean formsTriangle (Connector cnctr, Color c) {
		int point = -1;
		for(int i=0; i<moves.size(); i++){
			if(c.equals(colors[i])){
				if(cnctr.endPt1() == moves.get(i).endPt1())
					point = moves.get(i).endPt2();
				else if(cnctr.endPt1() == moves.get(i).endPt2())
					point = moves.get(i).endPt1();
				else 
					point = -1;
			}
			if(point>-1)
				for(int j=0; j<=movesIndex; j++){
					if(c.equals(colors[j]) && (moves.get(j)!=moves.get(i))){
						if(moves.get(j).endPt1() == point)
							if(moves.get(j).endPt2() == cnctr.endPt2())
								return true;
						if(moves.get(j).endPt2() == point)
							if(moves.get(j).endPt1() == cnctr.endPt2())
								return true;
					}
				}
			}
		return false;
	}
	
	// The computer (playing BLUE) wants a move to make.
	// The board is assumed to contain an uncolored connector, with no 
	// monochromatic triangles.
	// There must be an uncolored connector, since if all 15 connectors are colored,
	// there must be a monochromatic triangle.
	// Pick the first uncolored connector that doesn't form a BLUE triangle.
	// If each uncolored connector, colored BLUE, would form a BLUE triangle,
	// return any uncolored connector.
	public Connector choice ( ) {
		Connector lose = null;
		int k = 0;
		ArrayList<Connector> possibleMoves= new ArrayList<Connector>();
		for(int i=1; i<=6; i++){
			for(int j=1; j<=6; j++){
				if(i != j && moves.indexOf(new Connector(i, j)) == -1) {
					possibleMoves.add(new Connector(i, j));
				}
			}
		}
		do {
			if (twoMixedColors(moves.get(k)) != null && !moves.contains(twoMixedColors(moves.get(k)))) {
				return twoMixedColors(moves.get(k));
			}
			k++;
		} while (k < moves.size());
		for (int m = 0; m < possibleMoves.size(); m++) {
			if (!formsTriangle(possibleMoves.get(m), Color.BLUE)) {
				if (!formsTriangle(possibleMoves.get(m), Color.RED)) {
					return possibleMoves.get(m);
				} else {
					lose = possibleMoves.get(m);
				}
			} else {
				lose = possibleMoves.get(m);
			}
		}
		return lose;
	}
	
	// If the connector given in the argument has an adjacent
	// connector that is a different color, return a new connector
	// forming a triangle with the former two connectors
	private Connector twoMixedColors (Connector e) {
		Connector choice = null;
		String ans = hasDiffColorConnector(e);
		if (ans != null){
			choice = new Connector(Integer.parseInt(ans.substring(0, 1)), Integer.parseInt(ans.substring(1)));
		}
		return choice;
	}
	
	// Checks to see if the connector in the argument has an
	// adjacent connector that is a different color. If there is,
	// returns the unshared endpoints in string format.
	private String hasDiffColorConnector(Connector e) {
		for (int k = 0; k < moves.size(); k++) {
			if (moves.get(k).endPt1() == e.endPt1() && colors[k] !=  colors[moves.indexOf(e)]) {
				return Integer.toString(moves.get(k).endPt2()) + Integer.toString(e.endPt2());
			} else if (moves.get(k).endPt2() == e.endPt1() && colors[k] !=  colors[moves.indexOf(e)]) {
				return Integer.toString(moves.get(k).endPt1()) + Integer.toString(e.endPt2());
			} else if (moves.get(k).endPt1() == e.endPt2() && colors[k] !=  colors[moves.indexOf(e)]) {
				return Integer.toString(moves.get(k).endPt2()) + Integer.toString(e.endPt1());
			} else if (moves.get(k).endPt2() == e.endPt2() && colors[k] !=  colors[moves.indexOf(e)]) {
				return Integer.toString(moves.get(k).endPt1()) + Integer.toString(e.endPt1());
			}
		}
		return null;
	}
	

	// Return true if the instance variables have correct and internally
	// consistent values.  Return false otherwise.
	// Unchecked prerequisites:
	//	Each connector in the board is properly initialized so that 
	// 	1 <= myPoint1 < myPoint2 <= 6.
	public boolean isOK ( ) {
		int redCount = 0;
		int blueCount = 0;
		int whiteCount = 0;
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] == Color.WHITE) {
				whiteCount++;
			} else if (colors[i] == Color.RED) {
				redCount++;
			} else if (colors[i] == Color.BLUE) {
				blueCount++;
			}
		}
		for (int i = 0; i < colors.length; i++) {
			if (colors[i] == null) {
				return false;
			}
		}
		if (moves.size() > 15 || colors.length > 15) {
			return false;
		} else if (blueCount + redCount + whiteCount != 15 || redCount - blueCount > 1 || redCount - blueCount < 0){
			return false;
		} else {
			return true;
		}
	}
}
