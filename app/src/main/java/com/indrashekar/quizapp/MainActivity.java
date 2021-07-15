package com.indrashekar.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Boolean exit = false;
    private JSONArray otherop;
    ArrayList<String> quetionsarr=new ArrayList<>();
    ArrayList<String> answerarr=new ArrayList<>();
    ArrayList<String> wrongansarr=new ArrayList<>();
    ArrayList<String> displayarr=new ArrayList<>();
    private TextView txt_que,question1;
    private MaterialButton btn_1,btn_2,btn_3,btn_4;
    int que=1,a=0,b=1,correctanslocation1,score=0;

    public class Downloadtask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            String result= "";
            URL url;
            HttpURLConnection urlconnection=null;
            try{
                url=new URL(urls[0]);
                urlconnection=(HttpURLConnection)url.openConnection();
                InputStream in=urlconnection.getInputStream();
                InputStreamReader reader= new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){
                    char current=(char) data;
                    result+=current;
                    data=reader.read();
                }
                return result;
            }
            catch(Exception e){
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject obj=new JSONObject(result);
                String question=obj.getString("results");
                JSONArray arr=new JSONArray(question);
                wrongansarr.clear();
                for(int i=0;i<arr.length();i++){
                    JSONObject jsonpart=arr.getJSONObject(i);
                    String questions="";
                    String answer="";
                    otherop=new JSONArray();
                    questions=jsonpart.getString("question");
                    answer=jsonpart.getString("correct_answer");
                    otherop=jsonpart.getJSONArray("incorrect_answers");//["Moldova","Czech Republic","Denmark"]
                    wrongansarr.add((String) otherop.get(0));
                    wrongansarr.add((String) otherop.get(1));
                    wrongansarr.add((String) otherop.get(2));
                    if(questions!=""&&answer!=""){
                        quetionsarr.add(questions);
                        answerarr.add(answer);
                    }
                }
                Random rand=new Random();
                correctanslocation1=rand.nextInt(4);
                displayarr.clear();
                for(int o=0;o<4;o++){
                    if(o==correctanslocation1){
                        displayarr.add(answerarr.get(0)); //[0,1,2,3]
                    }
                    else{
                        displayarr.add(String.valueOf(wrongansarr.get(a)));
                        a++;
                    }
                }
                Log.i("Answerarr",Arrays.toString(new ArrayList[]{answerarr}));
                question1.setText(quetionsarr.get(0));
                btn_1.setText(displayarr.get(0));
                btn_2.setText(displayarr.get(1));
                btn_3.setText(displayarr.get(2));
                btn_4.setText(displayarr.get(3));
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void chooseanswer(View view){
        if(String.valueOf(correctanslocation1).equals(String.valueOf(view.getTag()))){
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            score++;
        }
        else{
            String crct="Wrong! it's "+displayarr.get(correctanslocation1);
            Toast.makeText(this, crct, Toast.LENGTH_SHORT).show();
        }
        if(que==10){
            Intent intent=new Intent(MainActivity.this,ScoreActivity.class);
            intent.putExtra("score",String.valueOf(score));
            startActivity(intent);
            finish();
        }
        else {
            txt_que.setText("Question " + (que + 1) + "/10");
            question1.setText(quetionsarr.get(que));
            if (que <= 9) {
                que++;
            }
            try {
                Random rand = new Random();
                correctanslocation1 = rand.nextInt(4);
                displayarr.clear();
                for (int o = 0; o < 4; o++) {
                    if (o == correctanslocation1) {
                        displayarr.add(answerarr.get(b));
                        b++; //[0,1,2,3]
                    } else {
                        displayarr.add(String.valueOf(wrongansarr.get(a)));
                        a++;
                    }
                }
                btn_1.setText(displayarr.get(0));
                btn_2.setText(displayarr.get(1));
                btn_3.setText(displayarr.get(2));
                btn_4.setText(displayarr.get(3));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Are you sure you want to go back");
            dialog.setTitle("Conformation");
            dialog.setIcon(android.R.drawable.ic_dialog_alert);
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(MainActivity.this, StartActivity.class));
                    finish();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_que=(TextView)findViewById(R.id.txt_que);
        question1=(TextView)findViewById(R.id.question1);
        btn_1=(MaterialButton)findViewById(R.id.btn_1);
        btn_2=(MaterialButton)findViewById(R.id.btn_2);
        btn_3=(MaterialButton)findViewById(R.id.btn_3);
        btn_4=(MaterialButton)findViewById(R.id.btn_4);
        Downloadtask task=new Downloadtask();
        String result=null;
        try {
            result= task.execute("https://opentdb.com/api.php?amount=10&category=9&difficulty=easy&type=multiple").get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}