public class QuestionModel implements IQuestionModel {

    private String question;
    private String[] answers;
    private int selectedAnswer;

    public QuestionModel(String question, String[] answers){
        this.question = question;
        this.answers = answers;
        this.selectedAnswer = -1;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
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
}
