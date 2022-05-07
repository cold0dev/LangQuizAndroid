package cold.langquiz;

import java.util.LinkedList;
import java.util.List;

public class LQEngine {
    private LQDatabase m_database;
    private long m_active_part = 0;
    private int m_correct_answers = 0;

    private int m_correct_index = 0;

    public LQEngine(String database_text){
        m_database = new LQDatabase(database_text);
    }

    public void reset_correct_answers(){
        m_correct_answers = 0;
        m_database.reset_database();
    }

    public List<LQQuestion> get_questions(){
        List<LQQuestion> out = m_database.get_questions(m_active_part);

        while (true){
            m_correct_index = (int)Math.floor(Math.random()*4);
            if (out.get(m_correct_index).is_used()){
                out = m_database.get_questions(m_active_part);
                continue;
            }
            break;
        }
        out.get(m_correct_index).set_used(true);
        return out;
    }

    public boolean answer(int index){
        if (index == m_correct_index){
            m_correct_answers++;
            if (m_correct_answers == 20){
                m_correct_answers = 0;
                m_active_part++;
            }
            return true;
        }

        reset_correct_answers();
        return false;
    }

    public int get_correct_index() { return m_correct_index; }
    public long get_active_part() { return m_active_part; }
    public long get_part_count() { return m_database.get_part_count(); }
    public int get_correct_answers() { return m_correct_answers; }
}
