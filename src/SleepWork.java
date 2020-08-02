
import java.awt.BorderLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class SleepWork {

	static boolean isProcessing = false;
	static JFrame frame;
	private static SwingWorker<Integer, Void> swingWorker;
	static JButton startButton = new JButton("Start");
	static JButton stopButton = new JButton("Stop");

	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setScreen();
			}
		});
	}

	private static void setScreen() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(100, 100);

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isProcessing = true;
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				startSleepWork();
			}
		});
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isProcessing = false;
				swingWorker.cancel(true);
			}
		});
		frame.getContentPane().add(BorderLayout.NORTH, startButton);
		frame.getContentPane().add(BorderLayout.SOUTH, stopButton);
		frame.setVisible(true);

	}

	private static void startSleepWork() {
		swingWorker = new SwingWorker<Integer, Void>() {
			@Override
			protected Integer doInBackground() throws Exception {

				Robot robot = new Robot();
				Random random = new Random();
				while (isProcessing) {
					robot.delay(1000 * 60);
					Point pObj = MouseInfo.getPointerInfo().getLocation();
					robot.mouseMove(pObj.x + 1, pObj.y + 1);
					robot.mouseMove(pObj.x - 1, pObj.y - 1);
					pObj = MouseInfo.getPointerInfo().getLocation();
					System.out.println("Sleepwork Working...");
				}

				return 0;
			}

			@Override
			public void done() {
				System.out.println("Sleepwork stopped");
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
			}
		};
		swingWorker.execute();
	}
}