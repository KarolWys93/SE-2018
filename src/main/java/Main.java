import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {


    private static String appName = "System Ekspertowy";

    public static void main(String[] args) {
        EventQueue.invokeLater(Main::runGUI);
    }

    private static void runGUI(){

//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        JFrame frame = new MainFrame(appName);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                closeApp();
            }
        });
        frame.setBounds(100,100,600,400);

        frame.setVisible(true);
    }

    private static void closeApp(){
        int selection = JOptionPane.showConfirmDialog(
                null,
                "Zamknąć "+ appName + "?",
                "Potwierdź wyjście",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if(selection == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }


}
