import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private JPanel startPanel;
    private JPanel questionsPanel;

    private QuestionScreen questionScreen;

    private ArrayList<IQuestionModel> questions;
    private ArrayList<QuestionRule> questionRules;

    private UCModelGenerator ucModelGenerator;
    public MainFrame(String title){
        super(title);


        questions = new ArrayList<>();
        questions.add(new QuestionModel(0, "1 + 2 = 3?", new String[]{"Tak", "Nie"}));
        questions.add(new QuestionModel(1,"1 jest mniejsze od 2?", new String[]{"Tak", "Nie", "Nie wiem"}));
        questions.add(new QuestionModel(2,"Baba wielkanocna to?", new String[]{"Taboret", "Macarena", "Nie wiem", "Baba"}));
        questionRules = new ArrayList<>();
        questionRules.add(new QuestionRule(0, new int[]{1, 2}));
        questionRules.add(new QuestionRule(1, new int[]{2, 2, 2}));
        questionRules.add(new QuestionRule(2, new int[]{-1, -1, -1, -1}));

        ucModelGenerator = new UCModelGenerator(questions, questionRules);

        StartScreen startScreen = new StartScreen();
        startPanel = startScreen.getStartPanel();
        startScreen.addStartListener(this::startDoingThings);

        questionScreen = new QuestionScreen();

        questionScreen.setQuestionListener((nextQuestion, selectedQuestion) -> {
            ucModelGenerator.setAnswerForLastQuestion(selectedQuestion);
            IQuestionModel question = null;
            if (nextQuestion){
                question = ucModelGenerator.getNextQuestion();
            }else {
                question = ucModelGenerator.getPreviousQuestion();
            }
            if(question != null) questionScreen.setQuestionModel(question);
        });


        setContentPane(startPanel);
    }


    private void startDoingThings(ActionEvent event){
        System.out.println("Expert's stuff");
        questionsPanel = questionScreen.getQuestionPanel();
        questionScreen.setQuestionModel(ucModelGenerator.getNextQuestion());
        setContentPane(questionsPanel);
    }




}
