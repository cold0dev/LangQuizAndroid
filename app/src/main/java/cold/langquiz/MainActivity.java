package cold.langquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll;
    LQEngine m_engine = null;
    List<LQQuestion> m_questions = null;
    TextView m_part_label = null;
    TextView m_ca_label = null;
    TextView m_question_label = null;
    Button m_answer_button1 = null;
    Button m_answer_button2 = null;
    Button m_answer_button3 = null;
    Button m_answer_button4 = null;

    enum STATE {
        ANSWER,
        LOAD
    }

    STATE m_state = STATE.ANSWER;

    public String load_data(String inFile) {
        String tContents = "";

        try {
            InputStream stream = getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }

    private void load_string(){
        m_questions = m_engine.get_questions();
        int correct = m_engine.get_correct_index();

        String question = m_questions.get(correct).get_question();
        m_question_label.setText(question.toUpperCase(Locale.ROOT));

        String part = "Part:" + m_engine.get_active_part() + "/" + m_engine.get_part_count();
        m_part_label.setText(part);

        String ca = "Correct answers:" + m_engine.get_correct_answers() + "/20";
        m_ca_label.setText(ca);

        m_answer_button1.setText(m_questions.get(0).get_answer());
        m_answer_button2.setText(m_questions.get(1).get_answer());
        m_answer_button3.setText(m_questions.get(2).get_answer());
        m_answer_button4.setText(m_questions.get(3).get_answer());
    }

    private void answer(int index){
        if (m_state == STATE.ANSWER){
            boolean correct = m_engine.answer(index);
            if (correct){
                m_question_label.setTextColor(Color.GREEN);
            }
            else {
                m_question_label.setTextColor(Color.RED);
            }

            m_answer_button1.setTextColor(Color.RED);
            m_answer_button2.setTextColor(Color.RED);
            m_answer_button3.setTextColor(Color.RED);
            m_answer_button4.setTextColor(Color.RED);

            switch (m_engine.get_correct_index()){
                case 0:
                    m_answer_button1.setTextColor(Color.GREEN);
                    break;
                case 1:
                    m_answer_button2.setTextColor(Color.GREEN);
                    break;
                case 2:
                    m_answer_button3.setTextColor(Color.GREEN);
                    break;
                case 3:
                    m_answer_button4.setTextColor(Color.GREEN);
                    break;
            }

            m_state = STATE.LOAD;
        }
        else if (m_state == STATE.LOAD){
            m_question_label.setTextColor(Color.BLACK);
            m_answer_button1.setTextColor(Color.BLACK);
            m_answer_button2.setTextColor(Color.BLACK);
            m_answer_button3.setTextColor(Color.BLACK);
            m_answer_button4.setTextColor(Color.BLACK);

            load_string();
            m_state = STATE.ANSWER;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String database_text = load_data("1000spain.db");
        m_engine = new LQEngine(database_text);

        ll = findViewById(R.id.ll);

        m_part_label = new TextView(this);
        m_part_label.setText("Part:0/n");
        m_part_label.setTextSize(24);
        m_part_label.setTextColor(Color.BLACK);
        ll.addView(m_part_label);

        m_ca_label = new TextView(this);
        m_ca_label.setText("Correct answers:0/20");
        m_ca_label.setTextSize(24);
        m_ca_label.setTextColor(Color.BLACK);
        ll.addView(m_ca_label);

        m_question_label = new TextView(this);
        m_question_label.setText("Question");
        m_question_label.setGravity(Gravity.CENTER);
        m_question_label.setTextSize(38);
        m_question_label.setPadding(0,30,0,0);
        m_question_label.setTextColor(Color.BLACK);
        ll.addView(m_question_label);

        m_answer_button1 = new Button(this);
        m_answer_button1.setText("Answer1");
        m_answer_button1.setTextSize(22);
        ll.addView(m_answer_button1);

        m_answer_button2 = new Button(this);
        m_answer_button2.setText("Answer2");
        m_answer_button2.setTextSize(22);
        ll.addView(m_answer_button2);

        m_answer_button3 = new Button(this);
        m_answer_button3.setText("Answer3");
        m_answer_button3.setTextSize(22);
        ll.addView(m_answer_button3);

        m_answer_button4 = new Button(this);
        m_answer_button4.setText("Answer4");
        m_answer_button4.setTextSize(22);
        ll.addView(m_answer_button4);

        load_string();

        m_answer_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer(0);
            }
        });

        m_answer_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer(1);
            }
        });

        m_answer_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer(2);
            }
        });

        m_answer_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer(3);
            }
        });
    }
}