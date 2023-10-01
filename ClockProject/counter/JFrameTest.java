package counter;

import javax.swing.SwingUtilities;

import GUI.CounterGUI;
import GUI.CounterGUI.CircleView;
import alarm.Alarm;
import clock.WeekAlarmClock;

public class JFrameTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WeekAlarmClock week = new WeekAlarmClock();
		SwingUtilities.invokeLater(() -> new CounterGUI(week));
		
	}

}
