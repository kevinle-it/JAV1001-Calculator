package com.trile.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textResult;

    Button btnClear;
    Button btnNegation;
    Button btnPercentage;

    Button btnAddition;
    Button btnSubtraction;
    Button btnMultiplication;
    Button btnDivision;
    Button btnEqual;

    List<Button> btnNumbers = Arrays.asList(new Button[10]);

    Button btnDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = findViewById(R.id.textResult);

        btnClear = findViewById(R.id.btnClear);
        btnNegation = findViewById(R.id.btnNegation);
        btnPercentage = findViewById(R.id.btnPercentage);

        btnAddition = findViewById(R.id.btnAddition);
        btnSubtraction = findViewById(R.id.btnSubtraction);
        btnMultiplication = findViewById(R.id.btnMultiplication);
        btnDivision = findViewById(R.id.btnDivision);
        btnEqual = findViewById(R.id.btnEqual);

        // Add all references of number buttons (0-9) by iterating from 0 to 9
        String btnId;
        int resourceViewID;
        for (int i = 0; i < btnNumbers.size(); ++i) {
            btnId = "btn" + i;
            resourceViewID = getResources().getIdentifier(btnId, "id", getPackageName());
            btnNumbers.set(i, findViewById(resourceViewID));
        }

        btnDot = findViewById(R.id.btnDot);

        setupOperatorBtnClickListeners();
        setupNumberBtnClickListeners();
    }

    private void setupOperatorBtnClickListeners() {
        btnClear.setOnClickListener(view -> {});
        btnNegation.setOnClickListener(view -> {});
        btnPercentage.setOnClickListener(view -> {});

        btnAddition.setOnClickListener(view -> {});
        btnSubtraction.setOnClickListener(view -> {});
        btnMultiplication.setOnClickListener(view -> {});
        btnDivision.setOnClickListener(view -> {});
        btnEqual.setOnClickListener(view -> {});
    }

    private void setupNumberBtnClickListeners() {
        for (int i = 0; i < btnNumbers.size(); ++i) {
            int finalI = i;
            btnNumbers.get(i).setOnClickListener(view -> onClickNumber(finalI));
        }

        btnDot.setOnClickListener(view -> {});
    }

    private void onClickNumber(int number) {
        String curTextResult = textResult.getText().toString();
        if (curTextResult.equals("0")) {
            textResult.setText("");
            curTextResult = "";
        }
        textResult.setText(curTextResult + number);
    }
}