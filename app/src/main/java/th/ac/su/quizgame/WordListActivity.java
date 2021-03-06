package th.ac.su.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import th.ac.su.quizgame.model.WordItem;

public class WordListActivity extends AppCompatActivity {
    static public WordItem[] items = {
            new WordItem(R.drawable.cat,"CAT"),
            new WordItem(R.drawable.dog,"DOG"),
            new WordItem(R.drawable.dolphin,"DOLPHIN"),
            new WordItem(R.drawable.koala,"KOALA"),
            new WordItem(R.drawable.lion,"LION"),
            new WordItem(R.drawable.owl,"OWL"),
            new WordItem(R.drawable.penguin,"PENGGUIN"),
            new WordItem(R.drawable.pig,"PIG"),
            new WordItem(R.drawable.rabbit,"RABBIT"),
            new WordItem(R.drawable.tiger,"TIGER"),

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        //สร้าง Layout Manager
        LinearLayoutManager lm = new LinearLayoutManager(WordListActivity.this);

        List<WordItem> wordList = Arrays.asList(items);

        //สร้าง Adapter Object
        MyAdapter adapter = new MyAdapter(WordListActivity.this,wordList);

        //การเข้าถึง Recycler View ใน Layout
        RecyclerView rv = findViewById(R.id.word_list_recycler_view);
        rv.setLayoutManager(lm); //กำหนด Layout Manager ให้กับ RecyclerView
        rv.setAdapter(adapter); //กำหนด  Adapter ให้กับ RecyclerView
         }
    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        final Context mContext;
        final List<WordItem> mWordList;

        public MyAdapter(Context context,List<WordItem> wordList){
            this.mContext = context;
            this.mWordList = wordList;

        }

        @NonNull
        @Override
        //หน้าตาขอแต่ละแถวของรูปกับคำศัพท์จะออกมาเป็นแบบไหน
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_word, parent, false);
            MyViewHolder vh = new MyViewHolder(mContext,v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
          holder.ImageView.setImageResource(mWordList.get(position).imageResId);
          holder.wordTextView.setText(mWordList.get(position).word);
          holder.item = mWordList.get(position);
        }

        @Override //เป็นตัวกำหนดว่ารูปกับคำศัพท์จะมีกี่อัน
        public int getItemCount() {
            return mWordList.size();
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {
            View rootView;
            ImageView  ImageView;
            TextView wordTextView;
            WordItem item;

            MyViewHolder(final Context context,@NonNull View itemView) {
               super(itemView);
               rootView = itemView;
               ImageView = itemView.findViewById(R.id.image_view);
               wordTextView = itemView.findViewById(R.id.word_text_view);

                rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       Toast.makeText(context,item.word,Toast.LENGTH_SHORT).show();

                       Intent intent = new Intent(context,WordDetailsActivity.class);

                       /*intent.putExtra("word",item.word); //เป็น string
                       intent.putExtra("image",item.imageResId); //เป็น int*/

                       String itemJason = new Gson().toJson(item); //แปลงไป
                       intent.putExtra("item",itemJason);

                        context.startActivity(intent);

                    }
                });
           }
        }
    }



