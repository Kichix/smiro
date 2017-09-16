package modules.weather;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherPanel extends JPanel{


    //Elements
    private JLabel temperatureLabel;
    private JLabel header;
    private BufferedImage image;

    public WeatherPanel(Weatherdata wd, String label) {
        super();
        super.setBackground(Color.black);
        //super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        super.setLayout(new GridLayout(5,1));

        //Data
        header = new JLabel(label, SwingConstants.CENTER);
        header.setForeground(Color.white);
        temperatureLabel = new JLabel(Integer.toString(wd.getTemperature())+" °C");
        temperatureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        temperatureLabel.setForeground(Color.white);

        //Add the labels
        super.add(header);
        super.add(temperatureLabel);

        //Add the image
        try {
            System.out.println(WeatherFileHandler.C_FILEPATH + wd.getIcon());
            image = ImageIO.read(new File(WeatherFileHandler.C_FILEPATH + wd.getIcon()));
        } catch (IOException ex ) {
            ex.printStackTrace();
        }

        JLabel imageLabel = new JLabel(new ImageIcon(image.getScaledInstance(60,60,0)));
        super.add(imageLabel);
    }

}
