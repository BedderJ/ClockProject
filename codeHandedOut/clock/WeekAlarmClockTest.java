 package clock;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.SwingUtilities;

import org.junit.jupiter.api.Test;

import GUI.CounterGUI;
import alarm.Alarm;
import alarm.AlarmManager;
import alarm.AlarmType;
import junit.framework.Assert;
import time.Time;

class WeekAlarmClockTest {
	
	//@Test
	void Tid() {
		WeekAlarmClock w = new WeekAlarmClock();

		Time t = new Time(4, 6, 24, 57);

		assertEquals(4, t.getDay());
		assertEquals(6, t.getHour());
		assertEquals(24, t.getMinute());
		assertEquals(57, t.getSecond());

		w.setTime(t);

		System.out.println(w.getTime());
		assertEquals(w.getTime().getDay(), 4);

		
		t.setDay(2);
		t.setHour(3);
		t.setMinute(12);
		t.setSecond(27);
		w.setTime(t);

		assertEquals(2,w.getTime().getDay());
		assertEquals(3,w.getTime().getHour());
		assertEquals(12,w.getTime().getMinute());
		assertEquals(27,w.getTime().getSecond());

	}

	@Test
	void Alarm() {
		WeekAlarmClock w = new WeekAlarmClock();
		Time t = new Time(0, 6, 0, 0);
		Time t2 = new Time(4, 6, 0, 0);

		Alarm a = new Alarm(t);
		Alarm a2 = new Alarm(t2);
		
		w.addAlarm(a);
		w.addAlarm(a2);
		

		for (int i = 0; i < 64800; i++) {
			w.tickTack();
		}
		
		
		assertEquals(w.getAlarms().toString(), "[Mon 06:00:00 Alarmet är true, Fri 06:00:00 Alarmet är true]");
		System.out.println(w.getAlarms());
		assertEquals(true, a.isActive());
		assertEquals(true, a2.isActive());

		w.removeAlarm(a);

		System.out.println(w.getAlarms());
		assertEquals(w.getAlarms().toString(), "[Fri 06:00:00 Alarmet är true]");

		w.removeAllAlarms();

		assertEquals(w.getAlarms().toString(), "[]");
		
		a.setActive(false);
		a2.setActive(false);

		assertEquals(false, a.isActive());
		assertEquals(false, a2.isActive());


	}
	@Test
	void Klocka() {
		WeekAlarmClock w = new WeekAlarmClock();
		Time t = new Time(0, 0, 0, 0);

		w.setTime(t);
		
		for (int i = 0; i < 1500000; i++) {
			w.tickTack();
		}
		assertEquals(w.getTime().getDay(), 6);
		assertEquals(w.getTime().getHour(), 8);
		assertEquals(w.getTime().getMinute(), 40);
		assertEquals(w.getTime().getSecond(), 0);

		System.out.println(w.getTime());

		
		
	

	}

}
