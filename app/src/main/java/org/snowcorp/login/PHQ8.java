package org.snowcorp.login;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.snowcorp.login.helper.Functions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PHQ8 extends AppCompatActivity {

    //UI objects
    private RadioButton q1a1Radio;
    private RadioButton q1a2Radio;
    private RadioButton q1a3Radio;
    private RadioButton q1a4Radio;

    private RadioButton q2a1Radio;
    private RadioButton q2a2Radio;
    private RadioButton q2a3Radio;
    private RadioButton q2a4Radio;

    private RadioButton q3a1Radio;
    private RadioButton q3a2Radio;
    private RadioButton q3a3Radio;
    private RadioButton q3a4Radio;

    private RadioButton q4a1Radio;
    private RadioButton q4a2Radio;
    private RadioButton q4a3Radio;
    private RadioButton q4a4Radio;

    private RadioButton q5a1Radio;
    private RadioButton q5a2Radio;
    private RadioButton q5a3Radio;
    private RadioButton q5a4Radio;

    private RadioButton q6a1Radio;
    private RadioButton q6a2Radio;
    private RadioButton q6a3Radio;
    private RadioButton q6a4Radio;

    private RadioButton q7a1Radio;
    private RadioButton q7a2Radio;
    private RadioButton q7a3Radio;
    private RadioButton q7a4Radio;

    private RadioButton q8a1Radio;
    private RadioButton q8a2Radio;
    private RadioButton q8a3Radio;
    private RadioButton q8a4Radio;

    private RadioButton q9a1Radio;
    private RadioButton q9a2Radio;
    private RadioButton q9a3Radio;
    private RadioButton q9a4Radio;


    private ScrollView scrollView;
    private String email;
    private ProgressDialog pDialog;
    private static final String TAG = PHQ8.class.getSimpleName();

    int q1_score=0;
    int q2_score=0;
    int q3_score=0;
    int q4_score=0;
    int q5_score=0;
    int q6_score=0;
    int q7_score=0;
    int q8_score=0;
    int q9_score=0;

    final static int RQS_1 = 4;

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    int score=0;
    int score_phq8 = 0;
    int score_phq9 = 0;
    int answered = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phq8);

        HttpsTrustManager.allowAllSSL();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                email= null;
            } else {
                email= extras.getString("email");
            }
        } else {
            email= (String) savedInstanceState.getSerializable("email");
        }

        //Initialize

        q1a1Radio = (RadioButton) findViewById(R.id.question1Answer1_radio);
        q1a2Radio = (RadioButton) findViewById(R.id.question1Answer2_radio);
        q1a3Radio = (RadioButton) findViewById(R.id.question1Answer3_radio);
        q1a4Radio = (RadioButton) findViewById(R.id.question1Answer4_radio);

        q2a1Radio = (RadioButton) findViewById(R.id.question2Answer1_radio);
        q2a2Radio = (RadioButton) findViewById(R.id.question2Answer2_radio);
        q2a3Radio = (RadioButton) findViewById(R.id.question2Answer3_radio);
        q2a4Radio = (RadioButton) findViewById(R.id.question2Answer4_radio);

        q3a1Radio = (RadioButton) findViewById(R.id.question3Answer1_radio);
        q3a2Radio = (RadioButton) findViewById(R.id.question3Answer2_radio);
        q3a3Radio = (RadioButton) findViewById(R.id.question3Answer3_radio);
        q3a4Radio = (RadioButton) findViewById(R.id.question3Answer4_radio);

        q4a1Radio = (RadioButton) findViewById(R.id.question4Answer1_radio);
        q4a2Radio = (RadioButton) findViewById(R.id.question4Answer2_radio);
        q4a3Radio = (RadioButton) findViewById(R.id.question4Answer3_radio);
        q4a4Radio = (RadioButton) findViewById(R.id.question4Answer4_radio);

        q5a1Radio = (RadioButton) findViewById(R.id.question5Answer1_radio);
        q5a2Radio = (RadioButton) findViewById(R.id.question5Answer2_radio);
        q5a3Radio = (RadioButton) findViewById(R.id.question5Answer3_radio);
        q5a4Radio = (RadioButton) findViewById(R.id.question5Answer4_radio);

        q6a1Radio = (RadioButton) findViewById(R.id.question6Answer1_radio);
        q6a2Radio = (RadioButton) findViewById(R.id.question6Answer2_radio);
        q6a3Radio = (RadioButton) findViewById(R.id.question6Answer3_radio);
        q6a4Radio = (RadioButton) findViewById(R.id.question6Answer4_radio);

        q7a1Radio = (RadioButton) findViewById(R.id.question7Answer1_radio);
        q7a2Radio = (RadioButton) findViewById(R.id.question7Answer2_radio);
        q7a3Radio = (RadioButton) findViewById(R.id.question7Answer3_radio);
        q7a4Radio = (RadioButton) findViewById(R.id.question7Answer4_radio);

        q8a1Radio = (RadioButton) findViewById(R.id.question8Answer1_radio);
        q8a2Radio = (RadioButton) findViewById(R.id.question8Answer2_radio);
        q8a3Radio = (RadioButton) findViewById(R.id.question8Answer3_radio);
        q8a4Radio = (RadioButton) findViewById(R.id.question8Answer4_radio);

        q9a1Radio = (RadioButton) findViewById(R.id.question9Answer1_radio);
        q9a2Radio = (RadioButton) findViewById(R.id.question9Answer2_radio);
        q9a3Radio = (RadioButton) findViewById(R.id.question9Answer3_radio);
        q9a4Radio = (RadioButton) findViewById(R.id.question9Answer4_radio);


        scrollView = (ScrollView) findViewById(R.id.scrollViewPHQ);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

    }

    /* This method is called when the submit quiz button is pressed
     * @params view
     */
    public void submitQuiz(View view) {
        handleGrading();

        if(answered<9)
        {
            scrollView.smoothScrollTo(0, 0);
            Toast.makeText(getApplicationContext(), "Please make sure you have answered all 9 questions",
                    Toast.LENGTH_LONG).show();
        }
        else{

//            Toast.makeText(getApplicationContext(), "Your depression score is: "+score,
//                    Toast.LENGTH_LONG).show();
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            score_phq9=score;
            score_phq8=score - q9_score;
            saveScore(email, score_phq8, score_phq9, date);
//            Intent intent = new Intent(this,Lobby.class);
////                intent.putExtra("Emergency_phone",person.emergency_phone);
////                intent.putExtra("Self_name", person.name);
//            startActivity(intent);
        }

    }

    private void saveScore(final String email, final int score_phq8, final int score_phq9, final String date) {
        // Tag used to cancel the request
        String tag_string_req = "req_saveScore";

        pDialog.setMessage("Saving your depression score ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Functions.SCORE_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Save Score Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        setAlarm();
//                        Bundle b = new Bundle();
//                        b.putString("email", email);
//                        Intent i = new Intent(PHQ8.this, Lobby.class);
//                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        i.putExtras(b);
//                        startActivity(i);
                        pDialog.dismiss();
                        moveTaskToBack(true);
                        finish();

                    } else {
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Save Score Error: " + error.getMessage(), error);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("score_q1", String.valueOf(q1_score));
                params.put("score_q2", String.valueOf(q2_score));
                params.put("score_q3", String.valueOf(q3_score));
                params.put("score_q4", String.valueOf(q4_score));
                params.put("score_q5", String.valueOf(q5_score));
                params.put("score_q6", String.valueOf(q6_score));
                params.put("score_q7", String.valueOf(q7_score));
                params.put("score_q8", String.valueOf(q8_score));
                params.put("score_q9", String.valueOf(q9_score));
                params.put("score_phq8", String.valueOf(score_phq8));
                params.put("score_phq9", String.valueOf(score_phq9));
                params.put("date", date);

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /*
     * This method handles the Grading Logic
     * Return total points
     */
    private void handleGrading(){
        //Declare and initialize var. If no question is answered the total score will be 0

        //Question 1 Correct answer: Answer1
        //award +1 point!
        if (q1a1Radio.isChecked())
        {
            answered++;
            q1_score=0;
        }
        else if(q1a2Radio.isChecked())
        {
            score+=1;
            q1_score=1;
            answered++;
        }
        else if(q1a3Radio.isChecked())
        {
            score+=2;
            q1_score=2;
            answered++;
        }
        else if(q1a4Radio.isChecked())
        {
            score+=3;
            q1_score=3;
            answered++;
        }
//Question 2
        if (q2a1Radio.isChecked())
        {
            answered++;
            q2_score=0;
        }
        else if(q2a2Radio.isChecked())
        {
            score+=1;
            q2_score=1;
            answered++;
        }
        else if(q2a3Radio.isChecked())
        {
            score+=2;
            q2_score=2;
            answered++;
        }
        else if(q2a4Radio.isChecked())
        {
            score+=3;
            q2_score=3;
            answered++;
        }

        if (q3a1Radio.isChecked())
        {
            answered++;
            q3_score=0;
        }
        else if(q3a2Radio.isChecked())
        {
            score+=1;
            q3_score=1;
            answered++;
        }
        else if(q3a3Radio.isChecked())
        {
            score+=2;
            q3_score=2;
            answered++;
        }
        else if(q3a4Radio.isChecked())
        {
            score+=3;
            q3_score=3;
            answered++;
        }

        if (q4a1Radio.isChecked())
        {
            answered++;
            q4_score=0;
        }
        else if(q4a2Radio.isChecked())
        {
            score+=1;
            q4_score=1;
            answered++;
        }
        else if(q4a3Radio.isChecked())
        {
            score+=2;
            q4_score=2;
            answered++;
        }
        else if(q4a4Radio.isChecked())
        {
            score+=3;
            q4_score=3;
            answered++;
        }

        if (q5a1Radio.isChecked())
        {
            answered++;
            q5_score=0;
        }
        else if(q5a2Radio.isChecked())
        {
            score+=1;
            q5_score=1;
            answered++;
        }
        else if(q5a3Radio.isChecked())
        {
            score+=2;
            q5_score=2;
            answered++;
        }
        else if(q5a4Radio.isChecked())
        {
            score+=3;
            q5_score=3;
            answered++;
        }

        if (q6a1Radio.isChecked())
        {
            answered++;
            q6_score=0;
        }
        else if(q6a2Radio.isChecked())
        {
            score+=1;
            q6_score=1;
            answered++;
        }
        else if(q6a3Radio.isChecked())
        {
            score+=2;
            q6_score=2;
            answered++;
        }
        else if(q6a4Radio.isChecked())
        {
            score+=3;
            q6_score=3;
            answered++;
        }

        if (q7a1Radio.isChecked())
        {
            answered++;
            q7_score=0;
        }
        else if(q7a2Radio.isChecked())
        {
            score+=1;
            q7_score=1;
            answered++;
        }
        else if(q7a3Radio.isChecked())
        {
            score+=2;
            q7_score=2;
            answered++;
        }
        else if(q7a4Radio.isChecked())
        {
            score+=3;
            q7_score=3;
            answered++;
        }

        if (q8a1Radio.isChecked())
        {
            answered++;
            q8_score=0;
        }
        else if(q8a2Radio.isChecked())
        {
            score+=1;
            q8_score=1;
            answered++;
        }
        else if(q8a3Radio.isChecked())
        {
            score+=2;
            q8_score=2;
            answered++;
        }
        else if(q8a4Radio.isChecked())
        {
            score+=3;
            q8_score=3;
            answered++;
        }

        if (q9a1Radio.isChecked())
        {
            answered++;
            q9_score=0;
        }
        else if(q9a2Radio.isChecked())
        {
            score+=1;
            answered++;
            q9_score=1;
        }
        else if(q9a3Radio.isChecked())
        {
            score+=2;
            answered++;
            q9_score=2;

        }
        else if(q9a4Radio.isChecked())
        {
            score+=3;
            answered++;
            q9_score=3;
        }

    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private Date setAlarm()
    {
        Calendar calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Date currentTime = Calendar.getInstance().getTime();
        calendar.setTime(currentTime);
        calendar.add(Calendar.DAY_OF_YEAR, 4);
//        calendar.add(Calendar.SECOND, 30);
        Date newDate = calendar.getTime();
        String nextDate = new SimpleDateFormat("dd-MM-yyyy hh:mm aaa").format(newDate);
        Log.v("Set alarm at",newDate.toString());



        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class); //ALARM IS SET
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),3*1000, pendingIntent);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        setBootReceiverEnabled(PackageManager.COMPONENT_ENABLED_STATE_ENABLED);


//        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
//        intent.putExtra(AlarmClock.EXTRA_DAYS,day);
//        intent.putExtra(AlarmClock.EXTRA_HOUR,hour);
//        intent.putExtra(AlarmClock.EXTRA_MINUTES,minute);
//        startActivity(intent);


        String message = "Thank you for participating in depression detection Study. Your data has been received successfully. You will be reminded to provide data on "+ nextDate+". Please save the alarm.";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        return newDate;
    }

    private void setBootReceiverEnabled(int componentEnabledState) {
        ComponentName componentName = new ComponentName(this, AlarmReceiver.class);
        PackageManager packageManager = getPackageManager();
        packageManager.setComponentEnabledSetting(componentName,componentEnabledState,PackageManager.DONT_KILL_APP);
    }

}