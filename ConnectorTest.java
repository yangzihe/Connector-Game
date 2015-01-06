import junit.framework.TestCase;

public class ConnectorTest extends TestCase {
	
	public void testToConnector () {
		String [] inputs = new String [] {"12", "     12", "12         ", "1   2", "   12    "};
		Connector output = new Connector(1,2);
		for(int i=0; i<inputs.length; i++){
			assertEquals(Connector.toConnector(inputs[i]), output);
		}
		
	}
	public void testIllegalvalues(){
		String [] test = new String [] {"1444", "1b", "79", "22", "51", "", "1", null};
		String [] message = new String [] {"too many inputs", "no int value",
			"out of bounds values", "when the same point is given twice", 
			"points not provided in increasing order", "empty input", "single value", "null input"};
		
		for(int i=0; i<test.length; i++){
			try{
				Connector.toConnector(test[i]);
				System.out.println("Failure to catch "+ message[i] + ": " + test[i]);
				assertTrue(false);
			}catch(IllegalFormatException e){}
		}
	}
}