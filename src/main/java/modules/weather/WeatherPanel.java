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
        updateData(wd);
        super.setBackground(Color.black);
        //super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        super.setLayout(new GridBagLayout());

        //Header constraints
        GridBagConstraints constraintsHeader = new GridBagConstraints();
        constraintsHeader.fill = GridBagConstraints.VERTICAL;
        constraintsHeader.anchor = GridBagConstraints.FIRST_LINE_START;
        constraintsHeader.gridwidth = GridBagConstraints.REMAINDER;

        //Center constraints
        GridBagConstraints constraintsCenter = new GridBagConstraints();
        constraintsCenter.fill = GridBagConstraints.VERTICAL;
        constraintsCenter.anchor = GridBagConstraints.CENTER;
        constraintsCenter.gridwidth = GridBagConstraints.REMAINDER;
        constraintsCenter.ipady = 10;

        //Data
        header = new JLabel(label, SwingConstants.CENTER);
        header.setForeground(Color.white);
        temperatureLabel = new JLabel(Integer.toString(wd.getTemperature())+" °C");
        temperatureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        temperatureLabel.setForeground(Color.white);

        //Add the labels
        super.add(header, constraintsHeader);
        super.add(temperatureLabel, constraintsCenter);

        //Add the image
        try {
            System.out.println(WeatherFileHandler.C_FILEPATH + wd.getIcon());
            image = ImageIO.read(new File(WeatherFileHandler.C_FILEPATH + wd.getIcon()));
        } catch (IOException ex ) {
            ex.printStackTrace();
        }

        JLabel imageLabel = new JLabel(new ImageIcon(image.getScaledInstance(75,75,0)));
        super.add(imageLabel);
    }

    public void updateData(Weatherdata wd) {

    }

}
