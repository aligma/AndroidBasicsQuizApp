package com.example.david.androidbasicsquizapp;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private int[] questionRadioGroups;
    private int[] markedRadioButtons;
    private int[] markedCheckBoxes;
    private int[] allCheckBoxes;
    private int[] hints;
    private int[] finalComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionRadioGroups = new int[]{R.id.kimchi_answer, R.id.bulgogi_answer, R.id.bibimbap_answer, R.id.kimbap_answer, R.id.ramyeon_answer, R.id.jeon_answer};
        markedRadioButtons = new int[]{R.id.q1a2, R.id.q2a4, R.id.q3a3, R.id.q4a2, R.id.q5a2, R.id.q5a3, R.id.q7a3};
        markedCheckBoxes = new int[]{R.id.table_1, R.id.table_3, R.id.table_4, R.id.sangchu_1, R.id.sangchu_4};
        allCheckBoxes = new int[]{R.id.table_1, R.id.table_2, R.id.table_3, R.id.table_4, R.id.table_5, R.id.sangchu_1, R.id.sangchu_2, R.id.sangchu_3, R.id.sangchu_4};
        hints = new int[]{R.id.kimchi_hint, R.id.q2hint, R.id.bibimbap_hint, R.id.kimbap_hint, R.id.ramyeon_hint, R.id.table_hint, R.id.jeon_hint, R.id.sangchu_hint, R.id.gochu_hint, R.id.meal_hint};
        finalComments = new int[]{R.id.final_dont_worry, R.id.final_good_start, R.id.final_great_job};
    }

    // based on https://stackoverflow.com/questions/151777/saving-android-activity-state-using-save-instance-state
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        for (int i = 0; i < questionRadioGroups.length; i++) {
            RadioGroup radioGroup = (RadioGroup) findViewById(questionRadioGroups[i]);
            savedInstanceState.putInt(Integer.toString(radioGroup.getId()), radioGroup.getCheckedRadioButtonId());
        }

        for (int i = 0; i < allCheckBoxes.length; i++) {
            CheckBox checkBox = (CheckBox) findViewById(allCheckBoxes[i]);
            savedInstanceState.putBoolean(Integer.toString(checkBox.getId()), checkBox.isChecked());
        }

        EditText q9EditText = (EditText) findViewById(R.id.gochu_answer);
        savedInstanceState.putString(Integer.toString(R.id.gochu_answer), q9EditText.getText().toString());

        EditText q10EditText = (EditText) findViewById(R.id.meal_answer);
        savedInstanceState.putString(Integer.toString(R.id.meal_answer), q10EditText.getText().toString());

        for (int i = 0; i < markedRadioButtons.length; i++) {
            RadioButton radioButton = (RadioButton) findViewById(markedRadioButtons[i]);
            savedInstanceState.putInt("marked" + Integer.toString(markedRadioButtons[i]), radioButton.getCurrentTextColor());
        }

        for (int i = 0; i < markedCheckBoxes.length; i++) {
            CheckBox checkBox = (CheckBox) findViewById(markedCheckBoxes[i]);
            savedInstanceState.putInt("marked" + Integer.toString(markedCheckBoxes[i]), checkBox.getCurrentTextColor());
        }

        for (int i = 0; i < hints.length; i++) {
            TextView hintTextView = (TextView) findViewById(hints[i]);
            savedInstanceState.putInt(Integer.toString(hintTextView.getId()), hintTextView.getVisibility());
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

        for (int i = 0; i < questionRadioGroups.length; i++) {
            RadioGroup radioGroup = (RadioGroup) findViewById(questionRadioGroups[i]);
            radioGroup.check(savedInstanceState.getInt(Integer.toString(radioGroup.getId())));
        }

        for (int i = 0; i < allCheckBoxes.length; i++) {
            CheckBox checkBox = (CheckBox) findViewById(allCheckBoxes[i]);
            checkBox.setChecked(savedInstanceState.getBoolean(Integer.toString(checkBox.getId())));
        }

        EditText q9EditText = (EditText) findViewById(R.id.gochu_answer);
        q9EditText.setText(savedInstanceState.getString(Integer.toString(R.id.gochu_answer)));

        EditText q10EditText = (EditText) findViewById(R.id.meal_answer);
        q10EditText.setText(savedInstanceState.getString(Integer.toString(R.id.meal_answer)));

        for (int i = 0; i < markedRadioButtons.length; i++) {
            RadioButton radioButton = (RadioButton) findViewById(markedRadioButtons[i]);
            radioButton.setTextColor(savedInstanceState.getInt("marked" + Integer.toString(markedRadioButtons[i])));
        }

        for (int i = 0; i < markedCheckBoxes.length; i++) {
            CheckBox checkBox = (CheckBox) findViewById(markedCheckBoxes[i]);
            checkBox.setTextColor(savedInstanceState.getInt("marked" + Integer.toString(markedCheckBoxes[i])));
        }

        for (int i = 0; i < hints.length; i++) {
            TextView hintTextView = (TextView) findViewById(hints[i]);
            int visibility = savedInstanceState.getInt(Integer.toString(hintTextView.getId()));
            if (visibility == View.VISIBLE)
                hintTextView.setVisibility(View.VISIBLE);
            if (visibility == View.GONE)
                hintTextView.setVisibility(View.GONE);
        }
    }

    public void checkAnswers(View view) {

        reset();
        int correct = 0;

        correct += checkMultipleChoiceQuestion(R.id.kimchi_answer, R.id.q1a2, R.id.kimchi_hint) ? 1 : 0;
        correct += checkMultipleChoiceQuestion(R.id.bulgogi_answer, R.id.q2a4, R.id.q2hint) ? 1 : 0;
        correct += checkMultipleChoiceQuestion(R.id.bibimbap_answer, R.id.q3a3, R.id.bibimbap_hint) ? 1 : 0;
        correct += checkMultipleChoiceQuestion(R.id.kimbap_answer, R.id.q4a2, R.id.kimbap_hint) ? 1 : 0;
        correct += checkMultipleChoiceQuestion(R.id.ramyeon_answer, new int[]{R.id.q5a2, R.id.q5a3}, R.id.ramyeon_hint) ? 1 : 0;
        correct += checkCheckBoxQuestion(new int[]{R.id.table_1, R.id.table_2, R.id.table_3, R.id.table_4, R.id.table_5}, new int[]{R.id.table_1, R.id.table_3, R.id.table_4}, R.id.table_hint) ? 1 : 0;
        correct += checkMultipleChoiceQuestion(R.id.jeon_answer, R.id.q7a3, R.id.jeon_hint) ? 1 : 0;
        correct += checkCheckBoxQuestion(new int[]{R.id.sangchu_1, R.id.sangchu_2, R.id.sangchu_3, R.id.sangchu_4}, new int[]{R.id.sangchu_1, R.id.sangchu_4}, R.id.sangchu_hint) ? 1 : 0;
        correct += checkEditTextQuestion(R.id.gochu_answer, new String[]{"gochu"}, R.id.gochu_hint) ? 1 : 0;
        correct += checkEditTextQuestion(R.id.meal_answer, new String[]{"jal", "m", "subnida"}, R.id.meal_hint) ? 1 : 0;


        TextView finalScore = (TextView) findViewById(R.id.final_score);
        finalScore.setText(getString(R.string.final_score, correct));
        finalScore.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(), finalScore.getText().toString(), Toast.LENGTH_LONG).show();

        int finalCommentId = (correct > 5)
                ? R.id.final_great_job
                : (correct > 0)
                ? R.id.final_good_start
                : R.id.final_dont_worry;

        TextView finalComment = (TextView) findViewById(finalCommentId);
        finalComment.setVisibility(View.VISIBLE);

        // Scroll to the credit text when marking
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_layout);
        TextView creditTextView = (TextView) findViewById(R.id.credit);
        linearLayout.getParent().requestChildFocus(linearLayout, creditTextView);
    }

    public boolean checkEditTextQuestion(int editTextId, String[] terms, int hintId) {
        EditText editText = (EditText) findViewById(editTextId);
        String text = editText.getText().toString().toLowerCase();
        int index = 0;

        for (int i = 0; i < terms.length; i++) {
            Log.v("FIND STRING", "Search " + text + " for " + terms[i] + " after index " + index);

            if (!text.substring(index).contains(terms[i])) {
                TextView hint = (TextView) findViewById(hintId);
                hint.setVisibility(View.VISIBLE);
                return false;
            }

            index = text.indexOf(terms[i], index);
        }

        return true;
    }

    public boolean checkCheckBoxQuestion(final int[] allCheckBoxIds, int[] correctCheckBoxIds, int hintId) {

        boolean correct = true;

        for (int i = 0; i < allCheckBoxIds.length; i++) {
            CheckBox checkBox = (CheckBox) findViewById(allCheckBoxIds[i]);
            if (contains(correctCheckBoxIds, allCheckBoxIds[i])) {
                // this is a correct answer and SHOULD be checked.
                if (checkBox.isChecked()) {
                    checkBox.setTextColor(ContextCompat.getColor(this, R.color.correct));
                } else {
                    correct = false;
                }
            } else {
                // this is an incorrect answer and SHOULD NOT be checked.
                if (checkBox.isChecked()) {
                    correct = false;
                }
            }
        }

        if (!correct) {
            TextView tableHint = (TextView) findViewById(hintId);
            tableHint.setVisibility(View.VISIBLE);
        }

        return correct;
    }

    private boolean contains(int[] array, int search) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == search)
                return true;
        }
        return false;
    }

    // for multiple choice questions with a single correct answer
    public boolean checkMultipleChoiceQuestion(int radioGroupId, int correctRadioButtonId, int hintId) {
        return checkMultipleChoiceQuestion(radioGroupId, new int[]{correctRadioButtonId}, hintId);
    }

    // for multiple choice questions with one or more correct answers
    public boolean checkMultipleChoiceQuestion(int radioGroupId, int[] correctRadioButtonIds, int hintId) {
        RadioGroup radioGroup = (RadioGroup) findViewById(radioGroupId);

        for (int i = 0; i < correctRadioButtonIds.length; i++) {
            if (radioGroup.getCheckedRadioButtonId() == correctRadioButtonIds[i]) {
                RadioButton radioButton = (RadioButton) findViewById(correctRadioButtonIds[i]);
                radioButton.setTextColor(ContextCompat.getColor(this, R.color.correct));
                return true;
            }
        }

        TextView hint1 = (TextView) findViewById(hintId);
        hint1.setVisibility(View.VISIBLE);
        return false;
    }

    // perform these actions before remarking or trying again
    private void reset() {
        hideTextViews(hints);
        hideTextViews(finalComments);
        resetTextColourOnRadioButtons(markedRadioButtons);
        resetTextColourOnCheckBoxes(markedCheckBoxes);
    }

    public void tryAgain(View view) {
        reset();
        hideTextView(R.id.final_score);
        resetSelectionsOfRadioGroups(questionRadioGroups);
        resetSelectionsOfCheckBoxes(allCheckBoxes);
        EditText gochuEditText = (EditText) findViewById(R.id.gochu_answer);
        gochuEditText.setText("");
        EditText mealEditText = (EditText) findViewById(R.id.meal_answer);
        mealEditText.setText("");

        // Scroll to the top when trying again
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_layout);
        TextView quizTitleTextView = (TextView) findViewById(R.id.quiz_title);
        linearLayout.getParent().requestChildFocus(linearLayout, quizTitleTextView);
    }

    private void resetSelectionsOfRadioGroups(int[] radioGroupIds) {
        for (int i = 0; i < radioGroupIds.length; i++) {
            RadioGroup radioGroup = (RadioGroup) findViewById(radioGroupIds[i]);
            if (radioGroup != null)
                radioGroup.clearCheck();
        }
    }

    private void resetSelectionsOfCheckBoxes(int[] checkBoxIds) {
        for (int i = 0; i < checkBoxIds.length; i++) {
            CheckBox checkBox = (CheckBox) findViewById(checkBoxIds[i]);
            if (checkBox != null)
                checkBox.setChecked(false);
        }
    }

    private void resetTextColourOnRadioButtons(int[] radioButtonIds) {
        for (int i = 0; i < radioButtonIds.length; i++) {
            RadioButton radioButton = (RadioButton) findViewById(radioButtonIds[i]);
            if (radioButton != null) {
                radioButton.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
            }
        }
    }

    private void resetTextColourOnCheckBoxes(int[] checkBoxIds) {
        for (int i = 0; i < checkBoxIds.length; i++) {
            CheckBox checkBox = (CheckBox) findViewById(checkBoxIds[i]);
            if (checkBox != null) {
                checkBox.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
            }
        }
    }

    private void hideTextViews(int[] textViewIds) {
        for (int i = 0; i < textViewIds.length; i++) {
            hideTextView(textViewIds[i]);
        }
    }

    private void hideTextView(int textViewId) {
        TextView textView = (TextView) findViewById(textViewId);
        if (textView != null) {
            textView.setVisibility(GONE);
        }
    }
}
