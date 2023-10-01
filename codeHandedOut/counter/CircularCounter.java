package counter;


public abstract class CircularCounter implements CounterType {

	public enum Direction {
		INCREASING, DECREASING
	};
 
	protected int currentCount;
	private boolean isPaused;
	private final int MAX_NR_OF_COUNTS;
	protected Direction direction;
	private CounterType nextCounter;

	public CircularCounter(int maxNrOfCounts, Direction direction,CounterType nextCounter){
		this.direction = direction;
		this.nextCounter = nextCounter;
		//Fixa ej rimliga inputvärden
		if (maxNrOfCounts < 2)
			this.MAX_NR_OF_COUNTS = 0;
		else
			this.MAX_NR_OF_COUNTS = maxNrOfCounts;
		//Om det är en nedåträknare, börja räkna från högsta värde istället
		
		if (this.direction == Direction.DECREASING && this.MAX_NR_OF_COUNTS>0)
			currentCount = MAX_NR_OF_COUNTS - 1;
	}

	@Override
	public int getCount() {
		return currentCount;
	}

	// Starta om räknare från början
	@Override
	public void reset() {
		currentCount = 0;
	}

	@Override
	public void pause() {
		isPaused = true;
	}

	// Används för att häva paus
	@Override
	public void resume() {
		isPaused = false;
	}

	@Override
	public void count(){
		if (!isPaused && this.MAX_NR_OF_COUNTS>0){
			
			if (direction == Direction.INCREASING)	{
				currentCount++;
				//Kolla om räknaren har räknat ett helt varv
				if (currentCount >= MAX_NR_OF_COUNTS){
					currentCount = 0; //Se till att räknaren börjar om från noll
					//Om kopplad till en annan räknare (nextCounter)…
					if (nextCounter != null) {
						nextCounter.count();
					}
						//…räkna upp den andra räknaren ett steg.
				}
			}
			else if (direction == Direction.DECREASING){
				currentCount--;
				if(currentCount < 0) {
					currentCount = MAX_NR_OF_COUNTS - 1;
				  if (nextCounter != null) {
					 nextCounter.count();
				}
				}
				
			}
		}
	}

	@Override
	public String toString() {
		return nextCounter.toString() ;
	}
}
