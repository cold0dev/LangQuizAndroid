package cold.langquiz;

public class LQQuestion {
    private long m_id;
    private String m_question;
    private String m_answer;
    private boolean m_used;

    public LQQuestion(long id, String question, String answer){
        m_id = id;
        m_question = question;
        m_answer = answer;
        m_used = false;
    }

    String get_question() { return m_question; }
    String get_answer() { return m_answer; }
    boolean is_used() { return m_used; }
    void set_used(boolean status) { m_used = status; }

    public long get_id() {
        return m_id;
    }
}
