package modules.travel;

import Helpers.Vector3;
import modules.weather.WeatherFileHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TravelPanel extends JPanel {

    //Transitdata
    private Long travelTime;
    private Date nextTransit;
    private TravelModuleHelper.Vehicle transit;
    private String transitTimeString;

    //GUI components
    private JLabel labelDuration;
    private JLabel labelNextDept;
    private BufferedImage image;

    public TravelPanel(TravelModuleHelper.Vehicle vec) throws IOException {

        this.transit = vec;
        this.nextTransit = new Date();
        this.setBackground(Color.black);
        initGUI(vec);
    }

    public void initGUI(TravelModuleHelper.Vehicle vec) throws IOException {
        this.setLayout(new GridLayout(3,1));
        image = ImageIO.read(new File(TravelModuleHelper.C_FILEPATH + TravelModuleHelper.getTransitIconName(vec)));
        JLabel imageLabel = new JLabel(new ImageIcon(image.getScaledInstance(60,60,0)));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        super.add(imageLabel);

        labelDuration = new JLabel("");
        labelDuration.setForeground(Color.white);
        labelDuration.setHorizontalAlignment(SwingConstants.CENTER);
        super.add(labelDuration);

        labelNextDept = new JLabel("");
        labelNextDept.setForeground(Color.white);
        labelNextDept.setHorizontalAlignment(SwingConstants.CENTER);
        super.add(labelNextDept);
    }

    public Long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Long travelTime) {
        this.travelTime = travelTime;
        this.labelDuration.setText(Long.toString(travelTime)+" min");
    }

    public Date getNextTransit() {
        return nextTransit;
    }

    public void setNextTransit(Date nextTransit) {
        SimpleDateFormat format = new SimpleDateFormat("H:m");
        this.nextTransit = nextTransit;

        if(this.transit == TravelModuleHelper.Vehicle.TRANSIT) {
            transitTimeString = "Nächste Abfahrt: " + format.format(nextTransit) + "Uhr";
            labelNextDept.setText(transitTimeString);
        }
    }
}
