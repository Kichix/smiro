package screen;

import modules.weather.WeatherFileHandler;
import modules.weather.WeatherModule;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Michi on 26.07.2017.
 */
public class MainScreen {

    JPanel[] subPanels;

    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
        new MainScreen();
    }

    public MainScreen() throws IOException, ParseException, java.text.ParseException {
        this.init();
    }

    //Initializes the main screen and assigned modules
    public void init() throws IOException, ParseException, java.text.ParseException {
        //Mainframe
        JFrame mainFrame = new JFrame("Smiro");
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //mainFrame.setSize(300,300);
        mainFrame.setUndecorated(true);

        //MainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3,3));
        mainPanel.setBackground(Color.black);
        mainFrame.add(mainPanel);

        //SubPanels
        subPanels = new JPanel[9];
        for(int i=0; i<9; i++) {
            if(i == 0) {
                subPanels[i] = new WeatherModule();
            } else {
                subPanels[i] = new JPanel();
                subPanels[i].setBackground(Color.black);
            }
            mainPanel.add(subPanels[i]);
        }


        mainFrame.setVisible(true);
        mainPanel.setVisible(true);
        for(int i = 0; i<9; i++) {
            subPanels[i].setVisible(true);
        }
    }
}
