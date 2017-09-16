package modules.travel;

import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static modules.travel.TravelModuleHelper.getTraveltime;

public class TravelModule extends JPanel{

    JPanel[] subPanels;
    Timer timer;

    public TravelModule() throws ParseException, java.text.ParseException, IOException {

        super();
        super.setBackground(Color.black);
        super.setVisible(true);
        super.setLayout(new GridLayout(1,3));

        initGUI();
        initTimer();
    }

    public void initGUI() throws ParseException, java.text.ParseException, IOException {

        subPanels = new JPanel[3];
        Border padding = BorderFactory.createEmptyBorder(50,50,50,50);
        super.setBorder(padding);
        update();
    }

    private void update() throws ParseException, java.text.ParseException, IOException {
        int i = 0;

        for(TravelModuleHelper.Vehicle vec : TravelModuleHelper.Vehicle.values()) {
            switch(vec) {
                case CAR:
                    subPanels[i] = getTraveltime(vec);
                    this.add(subPanels[i]);
                    i += 1;
                    break;
                case BICYCLE:
                    subPanels[i] = getTraveltime(vec);
                    this.add(subPanels[i]);
                    i += 1;
                    break;
                case TRANSIT:
                    subPanels[i] = getTraveltime(vec);
                    this.add(subPanels[i]);
                    i += 1;
                    break;
            }
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
                    try {
                        update();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    } catch (java.text.ParseException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    ii = 0;
                    timer.restart();
                }
                ii++;
            }
        });
        timer.start();
    }
}
