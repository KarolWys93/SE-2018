public class QuestionModel implements IQuestionModel {

    private int questionID;
    private String question;
    private String[] answers;
    private int selectedAnswer;

    public QuestionModel(int questionID, String question, String[] answers){
        this.questionID = questionID;
        this.question = question;
        this.answers = answers;
        this.selectedAnswer = -1;
    }

    @Override
    public int getQuestionID() {
        return questionID;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public String[] getAnswers() {
        return answers;
    }

    @Override
    public int selectedAnswer() {
        return selectedAnswer;
    }

    @Override
    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

}
