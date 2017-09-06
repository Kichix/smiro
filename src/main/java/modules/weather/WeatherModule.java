package modules.weather;

import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * Created by Michi on 26.07.2017.
 */
public class WeatherModule extends JPanel {

    WeatherModuleHelper weatherModuleHelper;

    public WeatherModule() throws IOException, ParseException, java.text.ParseException {

        super();
        super.setBackground(Color.black);

        //Modulecomponents
        WeatherFileHandler.updateFile();
        weatherModuleHelper = new WeatherModuleHelper();
        weatherModuleHelper.updateValues();

        //Padding
        Border padding = BorderFactory.createEmptyBorder(50,50,50,50);
        super.setBorder(padding);

        //Layout and Components
        super.setLayout(new GridLayout(1,5));
        JPanel[] subPanels = new JPanel[5];
        for(int i = 0; i<5; i++) {
            subPanels[i] = new WeatherPanel(weatherModuleHelper.getWeatherdataPos(i), weatherModuleHelper.getHeader(i));
            System.out.println(i+": "+weatherModuleHelper.getHeader(i));
            super.add(subPanels[i]);
        }

        super.setVisible(true);
        for(int i = 0; i<5; i++) {
            subPanels[i].setVisible(true);
        }
    }
}
