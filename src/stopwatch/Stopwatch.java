package stopwatch;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Stopwatch extends JFrame {

    private JPanel panel;
    static JButton resetButton;
    static JButton stopStartButton;

    static long millisecond = 0;
    static int minute = 0;
    static long second = 0;
    static boolean clockStarted = false;

    static ZonedDateTime startTime = ZonedDateTime.now();
    static ZonedDateTime stopTime = startTime;

    public Stopwatch() {

        // Panel Settings
        setTitle("Stopwatch");
        panel = new JPanel();
        JLabel label = new JLabel();
        label.setFont(new Font("Calibri", Font.PLAIN, 36));

        panel.add(label);

        add(panel, BorderLayout.CENTER);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resetButton = new JButton("RESET");
        resetButton.addActionListener(new ResetAction());
        panel.add(resetButton, BorderLayout.EAST);

        stopStartButton = new JButton("START");
        stopStartButton.addActionListener(new StopStartAction());
        panel.add(stopStartButton, BorderLayout.WEST);

        // loop to show counter changing
        while (true) {

            Duration duration;
            if (clockStarted) {
                duration = Duration.between(startTime, ZonedDateTime.now());

            } else {
                duration = Duration.between(startTime, stopTime);
            }

            // Calculate the difference in times for each unit type
            //System.out.println(duration.toMillis() + "dur millis");
            second = (duration.toMillis() / 1000) - (duration.toMinutes() * 60);
            //System.out.println(second + "sec");
            millisecond = duration.toMillis() - (duration.toMinutes() * 60000) - (second * 1000);
            //System.out.println(millisecond + "milli");

            // Format output to look standardized eg. 3 significant places for milliseconds
            String strMillisecond = "00" + Long.toString(millisecond);
            strMillisecond = strMillisecond.substring(strMillisecond.length() - 3, strMillisecond.length());
            String strSecond = "0" + Long.toString(second);
            strSecond = strSecond.substring(strSecond.length() - 2, strSecond.length());
            String strMinute = "0" + duration.toMinutes();
            strMinute = strMinute.substring(strMinute.length() - 2, strMinute.length());

            label.setText("" + strMinute + ":" + strSecond + ":" + strMillisecond + "");
            try {
                sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Stopwatch.class.getName()).log(Level.SEVERE, null, ex);
            }

            setVisible(true);

        }

    }

    public static void main(String[] args) {
        new Stopwatch();

    }

    private static class ResetAction implements ActionListener {

        public ResetAction() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            // User hits reset - Clock stops, timer is zeroed
            startTime = ZonedDateTime.now();
            stopTime = startTime;
            stopStartButton.setLabel("START");
            clockStarted = false;

        }
    }

    private static class StopStartAction implements ActionListener {

        public StopStartAction() {
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            // User hits stop - Flips button name
            if (clockStarted) {
                stopTime = ZonedDateTime.now();
                stopStartButton.setLabel("START");
                clockStarted = false;

            } else { // User hits start - Flips button name
                startTime = ZonedDateTime.now();
                stopStartButton.setLabel("STOP");
                clockStarted = true;
            }

        }
    }
}
