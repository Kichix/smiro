package modules.weather;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Created by Michi on 26.07.2017.
 */
public class WeatherModule extends JPanel {

    WeatherModuleHelper weatherModuleHelper;
    JPanel[] subPanels;
    Timer timer;

    public WeatherModule() throws IOException, ParseException, java.text.ParseException {

        super();
        super.setBackground(Color.black);

        //Modulecomponents
        WeatherFileHandler.updateFile();
        weatherModuleHelper = new WeatherModuleHelper();
        weatherModuleHelper.updateValues();

        super.setVisible(true);

        initGUI();
        initTimer();
    }

    public void initGUI() throws IOException {
        //Padding
        Border padding = BorderFactory.createEmptyBorder(50,50,50,50);
        super.setBorder(padding);

        //Layout and Components
        super.setLayout(new GridLayout(1,5));
        subPanels = new JPanel[5];
        updatePanels();

        for(int i = 0; i<5; i++) {
            //subPanels[i].setLayout(new GridLayout(5,1));
            subPanels[i].setVisible(true);
        }
    }

    //Updates the file and the weatherpanels
    public void updatePanels() throws IOException {
        for(int i = 0; i<5; i++) {
            WeatherFileHandler.updateFile();
            subPanels[i] = new WeatherPanel(weatherModuleHelper.getWeatherdataPos(i), weatherModuleHelper.getHeader(i));
            super.add(subPanels[i]);
        }
    }

    //Initializes the times which resets
    public void initTimer() {

        final int timerInterval = 3000;

        timer = new Timer(timerInterval,new ActionListener() {
            int ii = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ii==timerInterval){
                    ii = 0;
                    try {
                        updatePanels();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    timer.restart();
                }
                ii++;
            }
        });
        timer.start();
    }
}
