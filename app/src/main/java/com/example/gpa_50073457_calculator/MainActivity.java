package com.example.gpa_50073457_calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_COURSES = 5;
    private EditText[] inputCourses = new EditText[NUM_COURSES];
    private Button buttonComputeGPA;
    private Button buttonClear;
    private TextView textViewGpaResult;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        initializeViews();

        // Set button click listener
        buttonComputeGPA.setOnClickListener(view -> {
            if (buttonComputeGPA.getText().toString().equalsIgnoreCase("Compute GPA")) {
                if (validateInputs()) {
                    computeGPA();
                }
            }
            else if ( buttonComputeGPA.getText().toString().equalsIgnoreCase("Clear Form")){
                clearForm();
            }
        });
        buttonClear.setOnClickListener(view -> {
            if (buttonClear.getText().toString().equalsIgnoreCase("Clear" )) {
                clearForm();
            }
        });


    }


    private void initializeViews() {
        inputCourses[0] = findViewById(R.id.input_course1);
        inputCourses[1] = findViewById(R.id.input_course2);
        inputCourses[2] = findViewById(R.id.input_course3);
        inputCourses[3] = findViewById(R.id.input_course4);
        inputCourses[4] = findViewById(R.id.input_course5);
        buttonComputeGPA = findViewById(R.id.button_compute);
        buttonClear = findViewById(R.id.clear_text);
        textViewGpaResult = findViewById(R.id.GPA);
        rootView = findViewById(android.R.id.content).getRootView();

        for (EditText inputCourse : inputCourses) {
            inputCourse.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    buttonComputeGPA.setText("Compute GPA");
                    buttonClear.setText("Clear");
                    inputCourse.setBackgroundColor(Color.TRANSPARENT);
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    private boolean validateInputs() {
        textViewGpaResult.setText(""); // Clears the GPA result text
        rootView.setBackgroundColor(Color.WHITE); // Resets form background to white
        boolean isValid = true;
        for (EditText inputCourse : inputCourses) {
            String gradeText = inputCourse.getText().toString();
            if (gradeText.isEmpty()) {
                inputCourse.setError("Cannot be empty");
                inputCourse.setBackgroundColor(Color.RED); // Set background to red
                isValid = false;
            } else {
                try {
                    float grade = Float.parseFloat(gradeText);
                    if (grade < 0 || grade > 100) {
                        inputCourse.setError("Invalid grade");
                        inputCourse.setBackgroundColor(Color.RED); // Set background to red
                        isValid = false;
                    } else {
                        inputCourse.setBackgroundColor(Color.TRANSPARENT); // Reset background if data is correct
                    }
                } catch (NumberFormatException e) {
                    inputCourse.setError("Not a number");
                    inputCourse.setBackgroundColor(Color.RED); // Set background to red
                    isValid = false;
                }
            }
        }
        return isValid;
    }


    private void computeGPA() {

        float total = 0;
        for (EditText inputCourse : inputCourses) {
            total += Float.parseFloat(inputCourse.getText().toString());
        }
        float gpa = total / inputCourses.length;

        textViewGpaResult.setText(String.format("%.2f", gpa));
        updateBackgroundColor(gpa);
        buttonComputeGPA.setText("Clear Form");
    }

    private void updateBackgroundColor(float gpa) {
        if (gpa < 60) {
            rootView.setBackgroundColor(Color.RED);
        } else if (gpa < 80) {
            rootView.setBackgroundColor(Color.YELLOW);
        } else {
            rootView.setBackgroundColor(Color.GREEN);
        }
    }

    private void clearForm() {
        for (EditText inputCourse : inputCourses) {
            inputCourse.setText(""); // Clears all input fields
            inputCourse.setBackgroundColor(Color.TRANSPARENT);
            inputCourse.setError(null);// Resets to default (no background color)
        }
        textViewGpaResult.setText(""); // Clears the GPA result text
        rootView.setBackgroundColor(Color.WHITE); // Resets form background to white
        buttonComputeGPA.setText("Compute GPA"); // Resets button text to "Compute GPA"

    }



}
