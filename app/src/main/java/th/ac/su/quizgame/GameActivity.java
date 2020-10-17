package th.ac.su.quizgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import th.ac.su.quizgame.model.WordItem;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mQuestionImageView;
    private Button[]  mButtons = new Button[4];

    private String mAnswerword;
    private Random mRandom;
    List<WordItem> mItemList;

    private int score = 0;
    TextView ShowScoreTextView;
    private int Loop = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionImageView = findViewById(R.id.question_image_view);
        mButtons[0] = findViewById(R.id.choice_1_button);
        mButtons[1] = findViewById(R.id.choice_2_button);
        mButtons[2] = findViewById(R.id.choice_3_button);
        mButtons[3] = findViewById(R.id.choice_4_button);

        mButtons[0].setOnClickListener(this);
        mButtons[1].setOnClickListener(this);
        mButtons[2].setOnClickListener(this);
        mButtons[3].setOnClickListener(this);

        ShowScoreTextView = findViewById(R.id.show_score_text_view);
        mItemList = new ArrayList<>(Arrays.asList(WordListActivity.items));
        mRandom = new Random();
        newQuiz();

    }

    private void newQuiz() {

        //สุ่ม index ของคำศัพท์
        int answerIndex = mRandom.nextInt(mItemList.size());

        //เข้าถึง worditem ตาม index ที่สุ่มได้
        WordItem item = mItemList.get(answerIndex);

        //เซทให้รูปภาพโชว์
        mQuestionImageView.setImageResource(item.imageResId);

        mAnswerword = item.word;

        //มีปุ่มrandom 4 ปุ่ม(ช้อยให้เลือก)
        int randomBotton = mRandom.nextInt(4);

        // แสดงคำศัพท์ที่เป็นคำตอบ
        mButtons[randomBotton].setText(item.word);

        //เอา worditem ที่เป็นคำตอบแสดงผล list
        mItemList.remove(item);

        // เอา list ที่เหลือมา shuffle
        Collections.shuffle(mItemList);

        for (int i = 0; i < 4; i++) {
            if (i == randomBotton) {
                continue;
            }
            mButtons[i].setText(mItemList.get(i).word);
        }
    }

    private  void checkLoop(){
        Loop++;
        if(Loop == 5){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("สรุปผล");
            builder.setMessage("คุณได้ "+score+" คะแนน\n\nคุณต้องการเล่นเกมใหม่หรือไม่");
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Loop = 0;
                    score = 0;
                    ShowScoreTextView.setText(score+" คะแนน");
                    mItemList = new ArrayList<>(Arrays.asList(WordListActivity.items));
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }else {
            newQuiz();
        }
    }

    @Override
    public void onClick(View view) {
        Button  b = findViewById(view.getId());
        String buttonText = b.getText().toString();

        if(buttonText.equals(mAnswerword)){
            Toast.makeText(GameActivity.this,"ถูกต้องครับ",Toast.LENGTH_SHORT).show();
            score++;
            ShowScoreTextView.setText(score+" คะแนน");
        }else{
            Toast.makeText(GameActivity.this,"ผิดครับ",Toast.LENGTH_SHORT).show();
        }
        checkLoop();
    }
}