package clock;

import java.util.Collection;
import alarm.AlarmType;
import alarm.AlarmManager;
import counter.CircularCounter.Direction;
import counter.CircularCounter24;
import counter.Circularcounter60;
import counter.Counter7;
import counter.SettableCounterType;
import time.TimeType;
import time.Time;

public class WeekAlarmClock implements AlarmClockType {

	SettableCounterType dag = new Counter7(Direction.INCREASING, null);
	SettableCounterType timme = new CircularCounter24(Direction.INCREASING, dag);
	SettableCounterType minuter = new Circularcounter60(Direction.INCREASING, timme);
	SettableCounterType sekunder = new Circularcounter60(Direction.INCREASING, minuter);
	AlarmManager alarm = new AlarmManager();

	@Override
	public void tickTack() {
		sekunder.count();
		alarm.checkForAlarm(getTime());
	}

	@Override
	public void setTime(TimeType time) {
		dag.setCount(time.getDay());
		timme.setCount(time.getHour());
		minuter.setCount(time.getMinute());
		sekunder.setCount(time.getSecond());
	}

	@Override
	public TimeType getTime() {
		Time tid = new Time(dag.getCount(), timme.getCount(), minuter.getCount(), sekunder.getCount());
		return tid;
	}

	@Override
	public void addAlarm(AlarmType larm) {
		alarm.addAlarm(larm);
	}

	@Override
	public void removeAlarm(AlarmType larm) {
		alarm.removeAlarm(larm);
	}

	@Override
	public void removeAllAlarms() {
		alarm.removeAllAlarms();
	}

	@Override
	public Collection<AlarmType> getAlarms() {
		return alarm.getAlarms();

	}

}
