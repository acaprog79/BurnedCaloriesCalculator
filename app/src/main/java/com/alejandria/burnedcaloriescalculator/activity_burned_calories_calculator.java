package com.alejandria.burnedcaloriescalculator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView.OnEditorActionListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class activity_burned_calories_calculator extends AppCompatActivity implements OnEditorActionListener, OnSeekBarChangeListener, OnItemSelectedListener, OnKeyListener{

    private EditText weightED;
    private TextView mrTV;
    private TextView cbTV;
    private TextView bmiTV;
    private SeekBar seekBar;
    private Spinner feetSpinner;
    private Spinner inchSpinner;

    private SharedPreferences savedValues;

    private String weightString = "";
    private int miles = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burned_calories_calculator);

        weightED = (EditText)findViewById(R.id.weightED);
        mrTV = (TextView)findViewById(R.id.mrTV);
        cbTV = (TextView)findViewById(R.id.cbTV);
        bmiTV = (TextView)findViewById(R.id.bmiTV);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        feetSpinner = (Spinner)findViewById(R.id.feetSpinner);
        inchSpinner = (Spinner)findViewById(R.id.inchSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,R.array.feet_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feetSpinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,R.array.inch_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inchSpinner.setAdapter(adapter1);

        weightED.setOnEditorActionListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setOnKeyListener(this);
        feetSpinner.setSelection(5);
        inchSpinner.setSelection(6);

        savedValues = getSharedPreferences("SavedValue", MODE_PRIVATE);
    }
    public void onPause() {
        Editor editor = savedValues.edit();
        editor.putString("weightString", weightString);
        editor.putInt("miles", miles);
        editor.commit;

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        weightString = savedValues.getString("weightString", "");
        miles = savedValues.getInt("miles", 1);

        weightED.setText(weightString);

        int progress = Math.abs(miles * 100);
        seekBar.setProgress(progress);

    }
    public void calculateAndDisplay() {
        weightString = weightED.getText().toString();
        float weightAmount;
        if (weightString.equals("")){
            weightAmount = 0;
        }
        else {
            weightAmount = Float.parseFloat(weightString);
        }

        int progress = seekBar.getProgress();


        float caloriesBurned = (float) (0.75 * weightAmount * progress);
        float bmi = (weightAmount * 703) / ((12 * (float) feetSpinner.getSelectedItem() + (float) inchSpinner.getSelectedItem()) * ((12 * (float) feetSpinner.getSelectedItem() + (float) inchSpinner.getSelectedItem()));

        cbTV.setText(caloriesBurned);
        bmiTV.setText(bmi);

    }

}
