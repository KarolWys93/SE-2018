import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class UCModelGenerator implements IFormController {

    private MicroControllerModel ucModel;



    //"questions tree"
    private HashMap<Integer, QuestionRule> questionRules;
    private HashMap<Integer, IQuestionModel> questions;
    private Stack<Integer> usedQuestionIDs;

    private HashMap<Integer, MicroControllerModelRule> modelRules;


    public UCModelGenerator(List<IQuestionModel> questions, List<QuestionRule> questionTree, List<MicroControllerModelRule> modelRules){
        ucModel = new MicroControllerModel();

        this.questions = new HashMap<>();
        for (IQuestionModel question:questions){
            this.questions.put(question.getQuestionID(), question);
        }

        this.questionRules = new HashMap<>();
        for (QuestionRule rule:questionTree) {
            this.questionRules.put(rule.getQuestionID(), rule);
        }

        this.usedQuestionIDs = new Stack<>();

        assert validateModelRules(modelRules, ucModel);
        this.modelRules = new HashMap<>();
        for (MicroControllerModelRule rule:modelRules) {
            this.modelRules.put(rule.getRule_id(), rule);
        }
    }


    private boolean validateModelRules(List<MicroControllerModelRule> modelRules, MicroControllerModel model){
        Set<String> flags = model.parametersFlags.keySet();
        Set<String> values = model.parametersValues.keySet();

        for (MicroControllerModelRule rule:modelRules) {
            Set<String> properties = rule.getRules().keySet();
            for (String property:properties) {
                if (!(flags.contains(property) || values.contains(property))){
                    System.err.println("Property \"" + property + "\" in rule " + rule.getRule_id() + " is unknown");
                    return false;
                }
            }
        }
        return true;
    }


    public MicroControllerModel generateModel(){

        for (Integer questionID:usedQuestionIDs) {

            MicroControllerModelRule rule = modelRules.get(questionID);
            if (rule == null) continue;
            IQuestionModel question = questions.get(questionID);

            for (String property : rule.getRules().keySet()) {
                if (ucModel.parametersValues.keySet().contains(property)) {
                    ucModel.parametersValues.put(property, ((Double) rule.getRules().get(property)[question.selectedAnswer()]).intValue());
                }
                if (ucModel.parametersFlags.keySet().contains(property)) {
                    ucModel.parametersFlags.put(property, (Boolean) rule.getRules().get(property)[question.selectedAnswer()]);
                }
            }
        }


        return ucModel;
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
