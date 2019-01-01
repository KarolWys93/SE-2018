import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UCModelGenerator implements IFormController {

    private MicroControllerModel ucModel;

    private HashMap<Integer, QuestionRule> questionRules;
    private HashMap<Integer, IQuestionModel> questions;
    private List<Integer> usedQuestionIDs;

    public UCModelGenerator(List<IQuestionModel> questions, List<QuestionRule> rules){
        this.questions = new HashMap<>();
        for (IQuestionModel question:questions){
            this.questions.put(question.getQuestionID(), question);
        }

        this.questionRules = new HashMap<>();
        for (QuestionRule rule:rules) {
            this.questionRules.put(rule.getQuestionID(), rule);
        }

        this.usedQuestionIDs = new ArrayList<>();
    }





    public void setAnswerForLastQuestion(int answer){
        questions.get(usedQuestionIDs.get(usedQuestionIDs.size()-1)).setSelectedAnswer(answer);
    }

    @Override
    public IQuestionModel getNextQuestion() {
        IQuestionModel nextQuestion = null;
        if (usedQuestionIDs.isEmpty()){
            usedQuestionIDs.add(0);
            nextQuestion = questions.get(0);
        } else {
            int lastQuestionID = usedQuestionIDs.get(usedQuestionIDs.size()-1);
            int lastQuestionAnswer = questions.get(lastQuestionID).selectedAnswer();
            int nextQuestionID = questionRules.get(lastQuestionID).getNextQuestionID(lastQuestionAnswer);
            if (nextQuestionID != -1) {
                usedQuestionIDs.add(nextQuestionID);
                nextQuestion = questions.get(nextQuestionID);
            }
        }
        return nextQuestion;
    }

    @Override
    public IQuestionModel getPreviousQuestion() {
        if (usedQuestionIDs.size() < 2){
            return null;
        }
        int lastQuestionID = usedQuestionIDs.get(usedQuestionIDs.size()-1);
        questions.get(lastQuestionID).setSelectedAnswer(-1);    //remove selection from question
        usedQuestionIDs.remove(usedQuestionIDs.size()-1);
        lastQuestionID = usedQuestionIDs.get(usedQuestionIDs.size()-1);
        return questions.get(lastQuestionID);
    }
}
