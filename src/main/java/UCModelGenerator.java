import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class UCModelGenerator implements IFormController {

    private MicroControllerModel ucModel;



    //"questions tree"
    private HashMap<Integer, QuestionRule> questionRules;
    private HashMap<Integer, IQuestionModel> questions;
    private Stack<Integer> usedQuestionIDs;


    public UCModelGenerator(List<IQuestionModel> questions, List<QuestionRule> rules){
        this.questions = new HashMap<>();
        for (IQuestionModel question:questions){
            this.questions.put(question.getQuestionID(), question);
        }

        this.questionRules = new HashMap<>();
        for (QuestionRule rule:rules) {
            this.questionRules.put(rule.getQuestionID(), rule);
        }

        this.usedQuestionIDs = new Stack<>();
    }






    @Override
    public void setAnswerForLastQuestion(int answer){
        questions.get(usedQuestionIDs.get(usedQuestionIDs.size()-1)).setSelectedAnswer(answer);
    }

    @Override
    public IQuestionModel getNextQuestion() {
        IQuestionModel nextQuestion = null;
        if (usedQuestionIDs.isEmpty()){
            usedQuestionIDs.push(0);
            nextQuestion = questions.get(0);
        } else {
            int lastQuestionID = usedQuestionIDs.peek();
            int lastQuestionAnswer = questions.get(lastQuestionID).selectedAnswer();
            int nextQuestionID = questionRules.get(lastQuestionID).getNextQuestionID(lastQuestionAnswer);
            if (nextQuestionID != -1) {
                usedQuestionIDs.push(nextQuestionID);
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
        int lastQuestionID = usedQuestionIDs.pop();
        questions.get(lastQuestionID).setSelectedAnswer(-1);    //remove selection from question
        lastQuestionID = usedQuestionIDs.peek();
        return questions.get(lastQuestionID);
    }
}
