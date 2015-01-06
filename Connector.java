public class Connector {
	
	// Implement an immutable connector that connects two points on the game board.
	// Invariant: 1 <= myPoint1 < myPoint2 <= 6.
	
	private int myPoint1, myPoint2;
	
	public Connector (int p1, int p2) {
		if (p1 < p2) {
			myPoint1 = p1;
			myPoint2 = p2;
		} else {
			myPoint1 = p2;
			myPoint2 = p1;
		}
	}
	
	public int endPt1 ( ) {
		return myPoint1;
	}
	
	public int endPt2 ( ) {
		return myPoint2;
	}
	
	public boolean equals (Object obj) {
		Connector e = (Connector) obj;
		return (e.myPoint1 == myPoint1 && e.myPoint2 == myPoint2);
	}
	
	public String toString ( ) {
		return "" + myPoint1 + myPoint2;
	}
	
	// Format of a connector is endPoint1 + endPoint2 (+ means string concatenation),
	// possibly surrounded by white space. Each endpoint is a digit between
	// 1 and 6, inclusive; moreover, the endpoints aren't identical.
	// If the contents of the given string is correctly formatted,
	// return the corresponding connector.  Otherwise, throw IllegalFormatException.
	public static Connector toConnector (String s) throws IllegalFormatException {
		int point1=-1;
		int point2=-1;
		if(s==null)
			throw new IllegalFormatException("null value given");
		s = s.replaceAll("\\s","");
		if(s.length() != 2)
			throw new IllegalFormatException("Exactly two points inbetween 1 and 6 inclusive must be provided.");
		
		try {
			point1 = Integer.parseInt(s.substring(0,1));
			point2 = Integer.parseInt(s.substring(1,2));
		}
		
		catch(NumberFormatException e){
			throw new IllegalFormatException("Integer values must be provided.");
		}
		
		if(point1<1 || point2<1 || point1>6 || point2>6)
			throw new IllegalFormatException("Point must be inbetween 1 and 6 inclusive.");
		
		if(point1 == point2)
			throw new IllegalFormatException("The two points cannot equal each other");
		
		if(point1 > point2)
			throw new IllegalFormatException("Please provide points in increasing order.");
		
		return new Connector(point1, point2);
	}
}
