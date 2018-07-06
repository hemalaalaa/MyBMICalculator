package sg.edu.rp.c346.mybmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvShowDate;
    TextView tvShowBMI;
    TextView tvShowDesc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = (EditText) findViewById(R.id.etWeight);
        etHeight = (EditText) findViewById(R.id.etHeight);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        btnReset = (Button) findViewById(R.id.btnReset);
        tvShowDate = (TextView) findViewById(R.id.tvShowDate);
        tvShowBMI = (TextView) findViewById(R.id.tvShowBMI);
        tvShowDesc = (TextView) findViewById(R.id.tvShowDesc);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvShowDate.setText("Last Calculated Date: " + datetime);

                float floatWeight = Float.valueOf(etWeight.getText().toString());
                float floatHeight = Float.valueOf(etHeight.getText().toString());
                Float bmi = floatWeight/(floatHeight*floatHeight);



                tvShowBMI.setText("Last Calculated BMI:"+ bmi);



                if (bmi>=30){
                    tvShowDesc.setText("You are obese");
                }
                else if((bmi<=29.9)&&(bmi>=25)){
                    tvShowDesc.setText("You are overweight");

                }
                else if((bmi<=24.9)&&(bmi>=18.5)){
                    tvShowDesc.setText("Your BMI is normal");

                }
                else{
                    tvShowDesc.setText("You are underweight");

                }




            }
        });





        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                tvShowBMI.setText("Last Calculated BMI:");
                tvShowDate.setText("Last Calculated Date:");
                etHeight.setText("");
                etWeight.setText("");



            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();


        String dateTime = tvShowDate.getText().toString();
        float bmi = Float.parseFloat(tvShowBMI.getText().toString());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preEdit = prefs.edit();

        preEdit.putString("dateTime", dateTime);

        preEdit.putFloat("bmi", bmi);

        preEdit.commit();




    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String dateTime= prefs.getString("dateTime","");

        Float bmi= prefs.getFloat("bmi",0);

        tvShowBMI.setText("Last Calculated BMI:"+ bmi);
        tvShowDate.setText(dateTime);


    }
}
