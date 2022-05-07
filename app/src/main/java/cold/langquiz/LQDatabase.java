package cold.langquiz;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LQDatabase {
    LinkedList<LQQuestion> m_questions;
    long m_parts = 0;

    public long get_part_count() { return m_parts; }

    public void reset_database() {
        for (LQQuestion q : m_questions){
            q.set_used(false);
        }
    }

    private LQQuestion get_random_question(long part, List<Long> used_ids){
        LQQuestion question;
        long min = part * 20;
        long max = min + 19;
        while (true){
            int random = (int)Math.floor(Math.random()*(max-min+1)+min);
            question = m_questions.get(random);

            boolean contain = false;
            for (long id : used_ids){
                if (id == question.get_id()){
                    contain = true;
                }
                else if (question.get_question().equals(m_questions.get((int) id).get_question())){
                    contain = true;
                }
                else if (question.get_answer().equals(m_questions.get((int) id).get_answer())){
                    contain = true;
                }
            }

            if (!contain)break;
        }

        return question;
    }

    public List<LQQuestion> get_questions(long part){
        LinkedList<LQQuestion> out = new LinkedList<>();
        ArrayList<Long> used_ids = new ArrayList<>();

        LQQuestion question1 = get_random_question(part,used_ids);
        used_ids.add(question1.get_id());
        LQQuestion question2 = get_random_question(part,used_ids);
        used_ids.add(question2.get_id());
        LQQuestion question3 = get_random_question(part,used_ids);
        used_ids.add(question3.get_id());
        LQQuestion question4 = get_random_question(part,used_ids);

        out.add(question1);
        out.add(question2);
        out.add(question3);
        out.add(question4);

        return out;
    }

    private void parse(String database_text){
        long id = 0;

        for (String line : database_text.split("\n")){
            String[] args = line.split(";");
            if (args.length < 2)continue;
            LQQuestion question = new LQQuestion(id,args[0],args[1]);
            m_questions.add(question);
            id += 1;
        }

        m_parts = m_questions.size() / 20;
        m_parts--;
        if (m_questions.size() % 20 != 0)m_parts++;
    }

    public LQDatabase(String database_text){
        m_questions = new LinkedList<>();
        parse(database_text);
    }
}
