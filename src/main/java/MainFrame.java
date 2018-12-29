import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private JPanel startPanel;
    private JPanel questionsPanel;

    private QuestionScreen questionScreen;

    private ArrayList<QuestionModel> questions;
    private int currentQuestion = 0;

    public MainFrame(String title){
        super(title);


        questions = new ArrayList<>();
        questions.add(new QuestionModel("1 + 2 = 3?", new String[]{"Tak", "Nie"}));
        questions.add(new QuestionModel("1 jest mniejsze od 2?", new String[]{"Tak", "Nie", "Nie wiem"}));
        questions.add(new QuestionModel("Baba wielkanocna to?", new String[]{"Taboret", "Macarena", "Nie wiem", "Baba"}));
        
        StartScreen startScreen = new StartScreen();
        startPanel = startScreen.getStartPanel();
        startScreen.addStartListener(this::startDoingThings);

        questionScreen = new QuestionScreen();

        questionScreen.setQuestionListener((nextQuestion, selectedQuestion) -> {
            questions.get(currentQuestion).setSelectedAnswer(selectedQuestion);

            if (nextQuestion){
                if (currentQuestion < questions.size()-1) currentQuestion++;
                questionScreen.setQuestionModel(questions.get(currentQuestion));
            }else {
                if (currentQuestion > 0) currentQuestion--;
                questionScreen.setQuestionModel(questions.get(currentQuestion));
            }

        });


        setContentPane(startPanel);
    }


    private void startDoingThings(ActionEvent event){
        System.out.println("Expert's stuff");
        questionsPanel = questionScreen.getQuestionPanel();
        questionScreen.setQuestionModel(questions.get(0));
        setContentPane(questionsPanel);
    }




}
