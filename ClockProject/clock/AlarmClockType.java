package clock;

import java.util.Collection;
import alarm.AlarmType;
import time.TimeType;

public interface AlarmClockType {
	
	public void tickTack();

	public void setTime(TimeType time);
	
	public TimeType getTime();

	public void addAlarm(AlarmType larm);

	public void removeAlarm(AlarmType alarm);

	public void removeAllAlarms();

	public Collection<AlarmType> getAlarms();


	public String toString();
}
