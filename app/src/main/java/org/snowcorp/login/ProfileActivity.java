package org.snowcorp.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.snowcorp.login.helper.Functions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Akshay Raj on 6/16/2016.
 * akshay@snowcorp.org
 * www.snowcorp.org
 */

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = ProfileActivity.class.getSimpleName();

    private MaterialButton btnSubmit;

    private RadioButton qGender_a1Radio;
    private RadioButton qGender_a2Radio;
    private RadioButton qGender_a3Radio;

    DatePicker datePicker;

    private CheckBox qOrigina1Check;
    private CheckBox qOrigina2Check;
    private CheckBox qOrigina3Check;
    private CheckBox qOrigina4Check;
    private CheckBox qOrigina5Check;
    private CheckBox qOrigina6Check;
    private CheckBox qOrigina7Check;
    private CheckBox qOrigina8Check;
    private CheckBox qOrigina9Check;
    private CheckBox qOrigina10Check;

    private RadioButton qLanguage_a1Radio;
    private RadioButton qLanguage_a2Radio;
    private RadioButton qLanguage_a3Radio;
    private RadioButton qLanguage_a4Radio;

    private RadioButton qEducation_a1Radio;
    private RadioButton qEducation_a2Radio;
    private RadioButton qEducation_a3Radio;
    private RadioButton qEducation_a4Radio;
    private RadioButton qEducation_a5Radio;
    private RadioButton qEducation_a6Radio;
    private RadioButton qEducation_a7Radio;


    private RadioButton qDepression_a1Radio;
    private RadioButton qDepression_a2Radio;


    private RadioButton qMedication_a1Radio;
    private RadioButton qMedication_a2Radio;

    private int wait_depression =1;
    private int wait_medication = 1;
    private int wait_language=1;
    private ProgressDialog pDialog;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        setContentView(R.layout.activity_profile);

        qGender_a1Radio = (RadioButton) findViewById(R.id.genderAnswer1_radio);
        qGender_a2Radio = (RadioButton) findViewById(R.id.genderAnswer2_radio);
        qGender_a3Radio = (RadioButton) findViewById(R.id.genderAnswer3_radio);

        datePicker = (DatePicker) findViewById(R.id.datePickerDOB);

        qOrigina1Check = (CheckBox) findViewById(R.id.question_origin_Answer1_checkbox);
        qOrigina2Check = (CheckBox) findViewById(R.id.question_origin_Answer2_checkbox);
        qOrigina3Check = (CheckBox) findViewById(R.id.question_origin_Answer3_checkbox);
        qOrigina4Check = (CheckBox) findViewById(R.id.question_origin_Answer4_checkbox);
        qOrigina5Check = (CheckBox) findViewById(R.id.question_origin_Answer5_checkbox);
        qOrigina6Check = (CheckBox) findViewById(R.id.question_origin_Answer6_checkbox);
        qOrigina7Check = (CheckBox) findViewById(R.id.question_origin_Answer7_checkbox);
        qOrigina8Check = (CheckBox) findViewById(R.id.question_origin_Answer8_checkbox);
        qOrigina9Check = (CheckBox) findViewById(R.id.question_origin_Answer9_checkbox);
        qOrigina10Check = (CheckBox) findViewById(R.id.question_origin_Answer10_checkbox);

        qLanguage_a1Radio = (RadioButton) findViewById(R.id.languageAnswer1_radio);
        qLanguage_a2Radio = (RadioButton) findViewById(R.id.languageAnswer2_radio);
        qLanguage_a3Radio = (RadioButton) findViewById(R.id.languageAnswer3_radio);
        qLanguage_a4Radio = (RadioButton) findViewById(R.id.languageAnswer4_radio);

        qEducation_a1Radio = (RadioButton) findViewById(R.id.educationAnswer1_radio);
        qEducation_a2Radio = (RadioButton) findViewById(R.id.educationAnswer2_radio);
        qEducation_a3Radio = (RadioButton) findViewById(R.id.educationAnswer3_radio);
        qEducation_a4Radio = (RadioButton) findViewById(R.id.educationAnswer4_radio);
        qEducation_a5Radio = (RadioButton) findViewById(R.id.educationAnswer5_radio);
        qEducation_a6Radio = (RadioButton) findViewById(R.id.educationAnswer6_radio);
        qEducation_a7Radio = (RadioButton) findViewById(R.id.educationAnswer7_radio);


        qDepression_a1Radio = (RadioButton) findViewById(R.id.depressionAnswer1_radio);
        qDepression_a2Radio = (RadioButton) findViewById(R.id.depressionAnswer2_radio);

        qMedication_a1Radio = (RadioButton) findViewById(R.id.medicationAnswer1_radio);
        qMedication_a2Radio = (RadioButton) findViewById(R.id.medicationAnswer2_radio);


        btnSubmit = findViewById(R.id.btnSubmit);
        // Progress dialog
        pDialog = new ProgressDialog(this);


        // Hide Keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
    }

    private void init() {
        // Login button Click Event
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View view) {
                String gender;
                String english_proficiency = "Unknown";
                String education;
                String depression ="0";
                String medication="0";

                // Hide Keyboard
                Functions.hideSoftKeyboard(ProfileActivity.this);

                //Gender
                if (qGender_a1Radio.isChecked())
                {
                    gender = "Female";
                }else if (qGender_a2Radio.isChecked())
                {
                    gender = "Male";
                }else if (qGender_a3Radio.isChecked())
                {
                    gender = "Other";
                }else{
                    gender = "Prefer not to specify";
                }

                //Age
                String year_of_birth = String.valueOf(datePicker.getYear());

                //Origin
                List<String> temp_origin = new ArrayList<String>();
                if(qOrigina1Check.isChecked())
                {
                    temp_origin.add("White");
                }
                if(qOrigina2Check.isChecked())
                {
                    temp_origin.add("Chinese");
                }
                if(qOrigina3Check.isChecked())
                {
                    temp_origin.add("Black");
                }
                if(qOrigina4Check.isChecked())
                {
                    temp_origin.add("South Asian");
                }
                if(qOrigina5Check.isChecked())
                {
                    temp_origin.add("East Asian");
                }
                if(qOrigina6Check.isChecked())
                {
                    temp_origin.add("Southeast Asian");
                }
                if(qOrigina7Check.isChecked())
                {
                    temp_origin.add("Middle Eastern");
                }
                if(qOrigina8Check.isChecked())
                {
                    temp_origin.add("Latin American");
                }
                if(qOrigina9Check.isChecked())
                {
                    temp_origin.add("Aboriginal");
                }
                if(qOrigina10Check.isChecked())
                {
                    temp_origin.add("Other");
                }

                String origin="";
                if(temp_origin.size()==0)
                {
                    origin="Unknown";
                }
                else{
                    origin = TextUtils.join(", ", temp_origin);

                    Log.v(TAG,origin);
                }

                //Language
                if (qLanguage_a1Radio.isChecked())
                {
                    english_proficiency = "Native";
                    wait_language=0;
                }else if (qLanguage_a2Radio.isChecked())
                {
                    english_proficiency = "Fluent";
                    wait_language=0;
                }else if (qLanguage_a3Radio.isChecked())
                {
                    english_proficiency = "Intermediate";
                    wait_language=0;
                }else if(qLanguage_a4Radio.isChecked())
                {
                    english_proficiency = "Beginner";
                    wait_language=0;
                }
                else
                {
                    wait_language=1;
                }
                //Education
                if (qEducation_a1Radio.isChecked())
                {
                    education = "Doctoral";
                }else if (qEducation_a2Radio.isChecked())
                {
                    education = "Masters";
                }else if (qEducation_a3Radio.isChecked())
                {
                    education = "Undergraduate";
                }else if(qEducation_a4Radio.isChecked())
                {
                    education = "College";
                }else if(qEducation_a5Radio.isChecked())
                {
                    education = "High school";
                }else if(qEducation_a6Radio.isChecked())
                {
                    education = "Elementary school";
                }else if(qEducation_a7Radio.isChecked())
                {
                    education = "None";
                }
                else
                {
                    education = "Unknown";
                }

                //Depression
                if (qDepression_a1Radio.isChecked())
                {
                    depression = "1";
                    wait_depression=0;
                }else if (qDepression_a2Radio.isChecked())
                {
                    depression = "0";
                    wait_depression=0;
                }
                else
                {
                    wait_depression = 1;
                }

                //Medication
                if (qMedication_a1Radio.isChecked())
                {
                    medication = "1";
                    wait_medication=0;
                }else if (qMedication_a2Radio.isChecked())
                {
                    medication = "0";
                    wait_medication=0;
                }
                else
                {
                    wait_medication = 1;
                }
                if(wait_language==0 && wait_depression==0 && wait_medication==0)
                {
                    saveProfile(gender, year_of_birth, origin, english_proficiency, education, depression, medication);
                }
                else if(wait_language ==1)
                {
                    Toast.makeText(getApplicationContext(), "Please enter your level of English language proficiency", Toast.LENGTH_SHORT).show();
                }
                else if(wait_depression ==1)
                {
                    Toast.makeText(getApplicationContext(), "Please enter if you were diagnosed with depression before", Toast.LENGTH_SHORT).show();
                }
                else if(wait_medication ==1)
                {
                    Toast.makeText(getApplicationContext(), "Please enter if you take any medication for depression", Toast.LENGTH_SHORT).show();
                }


            }

        });

    }

    private void saveProfile(final String gender,
                             final String year_of_birth,
                             final String origin,
                             final String language_proficiency,
                             final String education,
                             final String depression,
                             final String medication)
    {
        String tag_string_req = "req_saveProfile";

        pDialog.setMessage("Saving your profile ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Functions.PROFILE_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Profile update response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Bundle b = new Bundle();
                        b.putString("email", email);
                        Intent i = new Intent(ProfileActivity.this, RecordSample1.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtras(b);
                        startActivity(i);
                        pDialog.dismiss();
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
                Log.e(TAG, "Registration Error: " + error.getMessage(), error);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("gender", gender);
                params.put("year_of_birth", year_of_birth);
                params.put("origin", origin);
                params.put("education", education);
                params.put("language_proficiency", language_proficiency);
                params.put("depression",depression);
                params.put("medication",medication);

                return params;
            }

        };

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
