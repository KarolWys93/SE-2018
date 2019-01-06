import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jdk.nashorn.api.scripting.URLReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame {

    private JPanel startPanel;
    private JPanel questionsPanel;

    private QuestionScreen questionScreen;

    private List<IQuestionModel> questions;
    private List<MicroControllerModelRule> modelRules;

    private UCModelGenerator ucModelGenerator;
    public MainFrame(String title){
        super(title);
        loadKnowledgeBase();

        ucModelGenerator = new UCModelGenerator(questions, modelRules);

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

    private void loadKnowledgeBase(){
        String json = "[" +
                "  {" +
                "    \"rule_id\": 1," +
                "    \"rules\" : {" +
                "      \"external_flash\" : [true, true, false]," +
                "      \"flash_size\" : [1, 5, 0, 12]" +
                "    }" +
//                "  }," +
//                "  {" +
//                "    \"rule_id\": 2," +
//                "    \"rules\" : {" +
//                "      \"red\" : [0, 1, 2, 3]," +
//                "      \"green\" : [true, false]" +
//                "    }" +
                "  }" +
                "]";


        Gson gson = new Gson();
        modelRules = gson.fromJson(json, new TypeToken<List<MicroControllerModelRule>>() {}.getType());

        try(Reader reader = new URLReader(MainFrame.class.getResource("/questions.json"))) {
            questions = gson.fromJson(reader, new TypeToken<List<QuestionModel>>() {}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        questions.forEach(questionModel -> questionModel.setSelectedAnswer(-1));    //hack for default values
    }


    private void startDoingThings(ActionEvent event){
        System.out.println("Start expert's stuff");
        questionsPanel = questionScreen.getQuestionPanel();
        questionScreen.setQuestionModel(ucModelGenerator.getNextQuestion());
        setContentPane(questionsPanel);
    }
}
