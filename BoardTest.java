import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import junit.framework.TestCase;

public class BoardTest extends TestCase {
	
	// Check empty board.
	public void testEmptyBoard ( ) {
		Board b = new Board ( );
		assertTrue (b.isOK ( ));
		checkCollection (b, 0, 0); // applies more tests
		assertTrue (!b.formsTriangle (new Connector (1, 2), Color.RED));
	}
	
	// Check one-connector board.
	public void test1Connector ( ) {
		Board b = new Board ( );
		checkCollection (b, 0, 0);
		b.add (new Connector (1, 2), Color.RED);
		assertTrue (b.isOK ( ));
		checkCollection (b, 1, 0);
		
		Iterator<Connector> iter = b.connectors (Color.RED);
		assertTrue (iter.hasNext ( ));
		Connector cnctr = iter.next ( );
		assertEquals (b.colorOf (cnctr), Color.RED);
		assertEquals (new Connector (1, 2), cnctr);
		assertTrue (!iter.hasNext ( ));
		
		assertTrue (!b.formsTriangle (new Connector(1,3), Color.RED));
		assertTrue (!b.formsTriangle (new Connector(5,6), Color.RED));
		assertTrue (!b.choice ( ).equals (new Connector (1, 2)));
		assertEquals (b.colorOf (b.choice ( )), Color.WHITE);
	}
	
	// More tests go here.
	
	// Tests a board with more than one connector.
	public void testMultipleConnector() {
		Board b = new Board();
		b.add(new Connector (1, 4), Color.RED);
		b.add(new Connector (1, 6), Color.BLUE);	
		b.add(new Connector (2, 4), Color.RED);
		b.add(new Connector (2, 3), Color.BLUE);
		b.add(new Connector (5, 6), Color.RED);	
		b.add(new Connector (1, 2), Color.BLUE);
		assertTrue(b.isOK());
		checkCollection(b, 3, 3);
		
		Iterator<Connector> iter = b.connectors (Color.BLUE);
		assertTrue (iter.hasNext ( ));
		Connector cnctr = iter.next ( );
		assertEquals (b.colorOf (cnctr), Color.BLUE);
		iter.next();
		iter.next();
		assertTrue(!iter.hasNext());
		
		assertTrue (b.formsTriangle (new Connector(1,2), Color.RED));
		assertTrue (!b.formsTriangle (new Connector(4,5), Color.RED));
		assertTrue (b.formsTriangle (new Connector(2,6), Color.BLUE));
		assertTrue (!b.formsTriangle (new Connector(3,4), Color.BLUE));
		assertEquals (b.colorOf (b.choice ( )), Color.WHITE);
		
		// Checks how Board handles illegal actions
		b.add(new Connector(1, 4), Color.BLUE);
		try{
			checkCollection(b, 3, 4);
		} catch (AssertionError e) {
			assertTrue(true); // Done so that checkCollection doesn't raise an error in JUnit
		}
		b.add(new Connector(2, 4), Color.WHITE);
		assertEquals(b.colorOf(new Connector(2, 4)), Color.RED);
	}
	
	// Checks isOK() is working as intended
	public void testIsOk() {
		Board b = new Board();
		b.add(new Connector (1, 5), Color.RED);
		b.add(new Connector (1, 6), Color.BLUE);	
		b.add(new Connector (3, 4), Color.RED);
		b.add(new Connector (5, 6), Color.BLUE);
		assertTrue(b.isOK());
		
		// Tests that fail the isOK check
		b.add(new Connector (1, 5), Color.BLUE);
		assertTrue(!b.isOK());
		b.add(new Connector (2, 5), Color.RED);
		b.add(new Connector (4, 6), Color.RED);	
		b.add(new Connector (3, 6), Color.RED);
		assertTrue(!b.isOK());
		
		b = new Board();
		b.add(new Connector(1, 2), Color.BLUE);
		assertTrue(!b.isOK());
	}
	
	
	// (a useful helper method)
	// Make the following checks on a board that should be legal:
	//	Check connector counts (# reds + # blues + # uncolored should be 15.
	//	Check red vs. blue counts.
	//	Check for duplicate connectors.
	//	Check for a blue triangle, which shouldn't exist.
	private void checkCollection (Board b, int redCount, int blueCount) {
		// Fill this in if you'd like to use this method.
		
		// We implemented the check for red and blue counts in isOK() 
		ArrayList<Connector> tempList = new ArrayList<Connector>();
		Iterator<Connector> tempIter = b.connectors();
		while (tempIter.hasNext()) {
			tempList.add(tempIter.next());
		}
		for (int j = 0; j < tempList.size(); j++) {
			for (int k = j+1; k < tempList.size(); k++) {
				if (j != k) {
					assertFalse(tempList.get(j).equals(tempList.get(k)));
				}
			}
		}
		for (int i = 0; i < tempList.size(); i++) {
		tempList = new ArrayList<Connector>();
		tempIter = b.connectors(Color.BLUE);
		while (tempIter.hasNext()) {
			tempList.add(tempIter.next());
		}
		for (int k = 0; k < tempList.size(); k++) {
			assertTrue(!b.formsTriangle(tempList.get(k), Color.BLUE));
		}
		}
	}
}
