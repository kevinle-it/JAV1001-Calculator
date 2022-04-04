package com.trile.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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

    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;

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

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

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
        btn0.setOnClickListener(view -> {});
        btn1.setOnClickListener(view -> {});
        btn2.setOnClickListener(view -> {});
        btn3.setOnClickListener(view -> {});
        btn4.setOnClickListener(view -> {});
        btn4.setOnClickListener(view -> {});
        btn6.setOnClickListener(view -> {});
        btn7.setOnClickListener(view -> {});
        btn8.setOnClickListener(view -> {});
        btn9.setOnClickListener(view -> {});

        btnDot.setOnClickListener(view -> {});
    }
}