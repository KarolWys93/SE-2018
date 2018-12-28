import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {

    private JPanel startPanel;

    public MainFrame(String title){
        super(title);

        StartScreen startScreen = new StartScreen();
        startPanel = startScreen.getStartPanel();
        startScreen.addStartListener(this::startDoingThings);


        setContentPane(startPanel);
    }


    private void startDoingThings(ActionEvent event){
        System.out.println("Expert's stuff");
    }


}
