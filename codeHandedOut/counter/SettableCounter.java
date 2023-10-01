package counter;

public class SettableCounter extends CircularCounter implements SettableCounterType {

	public SettableCounter(int maxNrOfCounts, Direction direction, CounterType nextCounter) {
		super(maxNrOfCounts, direction, nextCounter);
		// TODO Auto-generated constructor stub
	}

	public void setCount(int value){
		currentCount = value;	  
	}
}
