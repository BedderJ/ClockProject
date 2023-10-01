package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import alarm.Alarm;
import alarm.AlarmType;
import clock.WeekAlarmClock;
import time.Time;
import time.TimeType;

public class CounterGUI extends JFrame {

	private WeekAlarmClock w = new WeekAlarmClock();

	private JTextArea digitalclock = new JTextArea("");

	private JTextField setTimeDigital = new JTextField("day:hour:min:sec");

	private JButton setTimeButton = new JButton("Set Time");
	private JButton removeAllButton = new JButton("Remove All Alarms");
	private JButton AddAlarmButton = new JButton("Add Alarm");
	private JButton showAlarm = new JButton("Show Alarm");
	private JTextField addAlarm1 = new JTextField("Enter", 20);
	private JTextField displayAlarmDig = new JTextField("Enter");

	private JButton removeAlarm = new JButton("Reset button");

	private JPanel DigPanel = new JPanel(new BorderLayout());
	private JPanel AnalogPanel = new JPanel(new BorderLayout());
	private JPanel resetPanel = new JPanel(new BorderLayout());
	private JPanel AlarmPanel = new JPanel(new GridLayout(2, 2));
	private JPanel analogPanel = new JPanel(new BorderLayout());
	private JPanel circle = new CircleView(w);

	private JTabbedPane TabbedPaneDigital = new JTabbedPane();

	public CounterGUI(WeekAlarmClock weekalarm) {
		super("Klocka");

		digitalclock.setFont(new Font("SansSerif", Font.PLAIN, 48));

		// Tabs
		TabbedPaneDigital.addTab("Digital Clock", DigPanel);
		TabbedPaneDigital.addTab("Analog Clock", AnalogPanel);
		TabbedPaneDigital.addTab("Set Time", resetPanel);
		TabbedPaneDigital.addTab("Alarm", AlarmPanel);

		// Panels
		AlarmPanel.add(addAlarm1, BorderLayout.CENTER);
		AlarmPanel.add(AddAlarmButton, BorderLayout.EAST);
		AlarmPanel.add(showAlarm, BorderLayout.SOUTH);
		AlarmPanel.add(removeAlarm, BorderLayout.EAST);
		AlarmPanel.add(removeAllButton, BorderLayout.SOUTH);
		DigPanel.add(digitalclock, BorderLayout.CENTER);
		DigPanel.add(displayAlarmDig, BorderLayout.SOUTH);
		resetPanel.add(setTimeDigital, BorderLayout.CENTER);
		resetPanel.add(setTimeButton, BorderLayout.EAST);
		analogPanel.add(circle);
		AnalogPanel.add(analogPanel, BorderLayout.CENTER);

		// Gör GUIn Visible och sätter storleken
		setVisible(true);
		setSize(800, 1000);

		// Addar alla paneler
		add(TabbedPaneDigital);

		// TickTack
		Timer timer = new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Update the time and set it in the JTextArea
				w.tickTack();
				digitalclock.setText(w.getTime().toString());

				// Check for triggered alarms and display an alert if needed

				TimeType currentTime = w.getTime();
				int currentDay = currentTime.getDay();
				int currentHour = currentTime.getHour();
				int currentMinute = currentTime.getMinute();
				int currentSecond = currentTime.getSecond();

				Collection<AlarmType> alarms = w.getAlarms();

				for (AlarmType alarm : alarms) {
					if (alarm.isActive()) {
						TimeType alarmTime = alarm.getTime();
						int alarmDay = alarmTime.getDay();
						int alarmHour = alarmTime.getHour();
						int alarmMinute = alarmTime.getMinute();
						int alarmSecond = alarmTime.getSecond();

						// Compare each time component separately
						if (alarmDay == currentDay && alarmHour == currentHour && alarmMinute == currentMinute
								&& alarmSecond == currentSecond) {
							displayAlarmDig.setText("Alarm is triggered at " + w.getAlarms());
						}
					}
				}

			}
		});

		timer.start();
		// ResetButton
		setTimeDigital.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				w.setTime(new Time(0, 0, 0, 0));
			}
		});

		// Add Alarm
		AddAlarmButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AlarmGui();
			}
		});

		// Show Alarm
		showAlarm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog(w.getAlarms());
			}
		});

		removeAlarm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				removeAlarm();
			}
		});

		removeAllButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				w.removeAllAlarms();
			}
		});

		setTimeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setDigitalClock();
				w.tickTack();

			}
		});

	}

	private void setDigitalClock() {
		String input = setTimeDigital.getText();

		String[] components = input.split(":");

		if (components.length == 4) {
			int day = Integer.parseInt(components[0].trim());
			int hour = Integer.parseInt(components[1].trim());
			int minute = Integer.parseInt(components[2].trim());
			int second = Integer.parseInt(components[3].trim());

			w.setTime(new Time(day, hour, minute, second));
		}

	}

	private void AlarmGui() {
		String input = addAlarm1.getText();

		try {
			String[] components = input.split(":");

			if (components.length == 4) {
				int day = Integer.parseInt(components[0].trim());
				int hour = Integer.parseInt(components[1].trim());
				int minute = Integer.parseInt(components[2].trim());
				int second = Integer.parseInt(components[3].trim());

				Alarm alarm = new Alarm(new Time(day, hour, minute, second));
				w.addAlarm(alarm);
				JOptionPane.showMessageDialog(null, "Alarm added successfully.");
				// JOptionPane.showMessageDialog(null, "Day: " + day + "\nHour: " + hour +
				// "\nMinute: " + minute + "\nSecond: " + second);
			} else {
				JOptionPane.showMessageDialog(null,
						"Invalid time format. Please enter time in the format 'day hour:min:sec'");
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Invalid number format. Please enter valid integers.");
		}

	}

	private void removeAlarm() {
		String input = addAlarm1.getText();

		try {
			String[] components = input.split(":");
			if (components.length == 4) {
				int day = Integer.parseInt(components[0].trim());
				int hour = Integer.parseInt(components[1].trim());
				int minute = Integer.parseInt(components[2].trim());
				int second = Integer.parseInt(components[3].trim());

				// Create an Alarm object with the specified time
				Alarm alarmToRemove = new Alarm(new Time(day, hour, minute, second));

				// Check if the alarm exists in the WeekAlarmClock
				if (w.getAlarms() != null) {
					w.removeAlarm(alarmToRemove);
					JOptionPane.showMessageDialog(null, "Alarm removed successfully.");
				} else {
					JOptionPane.showMessageDialog(null, "Alarm not found.");
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Invalid time format. Please enter time in the format 'day:hour:min:sec'");
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Invalid number format. Please enter valid integers.");
		}

	}

	public class CircleView extends JPanel {
		private double secondAngle;
		private double hourAngle;
		private double minuteAngle;

		public CircleView(WeekAlarmClock clock) {
			setBackground(Color.black);
			Timer timer = new Timer(1, e -> {
				updateAngle(clock);
				repaint();
			});
			timer.start();
		}

		private void updateAngle(WeekAlarmClock clock) {
			TimeType time = clock.getTime();
			int seconds = time.getSecond();
			int minutes = time.getMinute();
			int hours = time.getHour();

			secondAngle = 2 * Math.PI * seconds / 60;
			minuteAngle = 2 * Math.PI * (minutes % 60) / 60 + (secondAngle / 60);
			hourAngle = 2 * Math.PI * (hours % 12) / 12 + (minuteAngle / 12);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			int width = getWidth();
			int height = getHeight();
			int xCenter = width / 2;
			int yCenter = height / 2;
			int radius = Math.min(width, height) / 2 - 10;

			// Draw the clock face
			g.setColor(Color.white);
			g.fillOval(xCenter - radius, yCenter - radius, 2 * radius, 2 * radius);

			// Horisontiell och vertikal linje
			g.setColor(Color.black);
			g.drawLine(0, yCenter, width, yCenter);
			g.drawLine(xCenter, 0, xCenter, height);

			// Draw clock hands
			drawClockHand(g, xCenter, yCenter, secondAngle, radius, Color.red);
			drawClockHand(g, xCenter, yCenter, minuteAngle, radius, Color.blue);
			drawClockHand(g, xCenter, yCenter, hourAngle, radius, Color.green);
		}

		private void drawClockHand(Graphics g, int xCenter, int yCenter, double angle, int length, Color color) {
			int xEnd = (int) (xCenter + length * Math.sin(angle));
			int yEnd = (int) (yCenter - length * Math.cos(angle));

			g.setColor(color);
			g.drawLine(xCenter, yCenter, xEnd, yEnd);
		}
	}

}
