package voting.model;

class PollAnswer {
    private final String text;
    private int votes;

    public PollAnswer(final String answer) {
        this(answer, 0);
    }

    public PollAnswer(final String answer, final int votes) {
        this.text = answer;
        this.votes = votes;
    }

    public String getText() {
        return this.text;
    }

    public int getVotes() {
        return this.votes;
    }

    public void setVotes(int votes) {
        if (votes < 0) {
            votes = 0;
        }
        this.votes = votes;
    }

    public void incrementVotes() {
        this.votes++;
    }

}
