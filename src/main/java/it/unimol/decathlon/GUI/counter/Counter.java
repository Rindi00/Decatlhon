package it.unimol.decathlon.GUI.counter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Counter extends JPanel {
    private int seconds =0;
    private int minutes =0;
    private int elapsedTime = 0;
    private String seconds_string = "0";
    private  String minutes_string = "0";

    JLabel timeLabel = new JLabel();

    Timer timer = new Timer(1000, new ActionListener() {

        public void actionPerformed(ActionEvent e) {

            elapsedTime = elapsedTime + 1000;
            minutes = (elapsedTime / 60000) % 60;
            seconds = (elapsedTime / 1000) % 60;
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);

            timeLabel.setText(minutes_string + ":" + seconds_string);

        }

    });

    public Counter() {
        timeLabel.setText(minutes_string +  ":" + seconds_string);

        this.add(timeLabel);
    }

    public void stop() {
        timer.stop();
    }


    public void start() {
        timer.start();

    }


    public void reset() {
        timer.stop();
        elapsedTime=0;
        seconds =0;
        minutes=0;
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        timeLabel.setText(minutes_string + ":" + seconds_string);
    }

}



