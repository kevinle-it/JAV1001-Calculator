package com.trile.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {

    enum Operation {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        UNDEFINED
    }

    final int MAX_NUM_INT_DIGITS = 9;
    TextView textResult;
    Boolean shouldResetTextResult = true;
    Double leftOperand = null;
    Double rightOperand = null;
    Double result = 0.0;

    Button btnClear;
    Button btnNegation;
    Button btnPercentage;

    Button btnAddition;
    Button btnSubtraction;
    Button btnMultiplication;
    Button btnDivision;
    Button btnEqual;

    Operation operation = Operation.UNDEFINED;

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

        // ========== Only allow clicking equal button when entering full equation ==========
        disableEqual();

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
        btnClear.setOnClickListener(view -> onClickClear());
        btnNegation.setOnClickListener(view -> onClickNegation());
        btnPercentage.setOnClickListener(view -> {});

        btnAddition.setOnClickListener(view -> onClickOperator(Operation.ADD));
        btnSubtraction.setOnClickListener(view -> onClickOperator(Operation.SUBTRACT));
        btnMultiplication.setOnClickListener(view -> onClickOperator(Operation.MULTIPLY));
        btnDivision.setOnClickListener(view -> onClickOperator(Operation.DIVIDE));
        btnEqual.setOnClickListener(view -> onClickEqual());
    }

    private void setupNumberBtnClickListeners() {
        for (int i = 0; i < btnNumbers.size(); ++i) {
            int finalI = i;
            btnNumbers.get(i).setOnClickListener(view -> onClickNumber(finalI));
        }

        btnDot.setOnClickListener(view -> onClickDot());
    }

    private void onClickNumber(int number) {
        // Set text for clear button
        String textBtnClear = getResources().getString(R.string.text_btn_clear);
        if (!btnClear.getText().equals(textBtnClear)) {
            btnClear.setText(R.string.text_btn_clear);
        }

        String curTextResult = textResult.getText().toString();
        if (curTextResult.equals("0") || shouldResetTextResult) {
            textResult.setText("");
            curTextResult = "";
            shouldResetTextResult = false;
        }
        
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        Double value;
        try {
            // If textResult is an Integer
            if (curTextResult.isEmpty()) {
                textResult.setText(String.valueOf(number));

                // ========== Right Operand is being entered ==========
                if (operation != Operation.UNDEFINED) {
                    // Set Right operand now for onClickNegation() to work
                    rightOperand = Double.valueOf(number);
                }
            } else if (!curTextResult.contains(".")) {
                Number parsedNumber = format.parse(curTextResult);
                value = parsedNumber.doubleValue();
                value = value * 10 + number;
                int decimalNum = value.intValue();
                if (String.valueOf(decimalNum).length() <= MAX_NUM_INT_DIGITS) {
                    DecimalFormat df = getFormatter(value);
                    textResult.setText(df.format(value));
                }

                // ========== Right Operand is being entered ==========
                if (operation != Operation.UNDEFINED) {
                    // Set Right operand now for onClickNegation() to work
                    rightOperand = value;
                }
            } else {    // Else, textResult is a Floating-point number
                Number parsedNumber = format.parse(curTextResult + number);
                value = parsedNumber.doubleValue();
                // MAX_NUM_INT_DIGITS + 1 (the digit plus the decimal point)
                if (String.valueOf(value).length() <= MAX_NUM_INT_DIGITS + 1) {
                    DecimalFormat df = getFormatter(value);
                    textResult.setText(df.format(value));
                }

                // ========== Right Operand is being entered ==========
                if (operation != Operation.UNDEFINED) {
                    // Set Right operand now for onClickNegation() to work
                    rightOperand = value;
                }
            }

            // ========== Right Operand is being entered ==========
            if (operation != Operation.UNDEFINED) {
                // Allow only one equation at a time
                disableOperators();
                // ========== Right Operand is being entered -> Enable equal button ==========
                enableEqual();
            }
        } catch (Exception exception) {
            Log.e("MainActivity", "onClickNumber: exception = " + exception);
        }
    }

    private void onClickDot() {
        // Set text for clear button
        String textBtnClear = getResources().getString(R.string.text_btn_clear);
        if (!btnClear.getText().equals(textBtnClear)) {
            btnClear.setText(R.string.text_btn_clear);
        }

        String curTextResult = textResult.getText().toString();
        if (!curTextResult.contains(".")) {
            textResult.setText(curTextResult + ".");
        }
        shouldResetTextResult = false;
    }

    private void onClickOperator(Operation operation) {
        this.operation = operation;

        if (leftOperand == null) {
            String curTextResult = textResult.getText().toString();
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            try {
                Number parsedNumber = format.parse(curTextResult);
                leftOperand = parsedNumber.doubleValue();
            } catch (Exception exception) {
                Log.e("MainActivity", "onClickOperator: exception = " + exception);
            }

            // Prepare for entering Right Operand
            shouldResetTextResult = true;
        }
    }

    private void onClickClear() {
        textResult.setText("0");
        btnClear.setText(R.string.text_btn_clear_initial);
        // Allow only one equation at a time
        disableEqual();
        // Reset equation & Re-enable operator buttons for entering another equation
        resetEquation();
        enableOperators();
    }

    private void onClickNegation() {
        // Operation is Undefined means left operand is still being entered
        if (operation == Operation.UNDEFINED || rightOperand != null) {
            String curTextResult = textResult.getText().toString();
            NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
            try {
                Number parsedNumber = format.parse(curTextResult);
                Double value = parsedNumber.doubleValue();
                value = -value;
                DecimalFormat df = getFormatter(value);
                textResult.setText(df.format(value));

                // Right operand is being entered -> apply negation to it
                // No need to set Left operand here because it will be set by onClickOperator()
                if (rightOperand != null) {
                    rightOperand = value;
                }
            } catch (Exception exception) {
                Log.e("MainActivity", "onClickNegation: exception = " + exception);
            }
        } else {    // rightOperand == null (it has not been entered after clicking an operator)
            textResult.setText("-0");   // Set text for right operand
            rightOperand = -0.0;
            // Until here, there are Left operand, Right operand, and Operator
            // -> Allow clicking equal button now
            // Allow only one equation at a time
            disableOperators();
            // Enable equal button
            enableEqual();
        }
    }

    private void onClickEqual() {
        // Allow only one equation at a time
        disableEqual();

        if (operation == Operation.DIVIDE && rightOperand == 0) {
            textResult.setText("Error");
        } else {
            switch (operation) {
                case ADD:
                    result = leftOperand + rightOperand;
                    break;
                case SUBTRACT:
                    result = leftOperand - rightOperand;
                    break;
                case MULTIPLY:
                    result = leftOperand * rightOperand;
                    break;
                case DIVIDE:
                    result = leftOperand / rightOperand;
                    break;
                default:
                    break;
            }
            DecimalFormat df = getFormatter(result);
            textResult.setText(df.format(result));
        }

        // Reset equation & Re-enable operator buttons for entering another equation
        resetEquation();
        enableOperators();
    }

    // =============== Allow only one equation at a time ===============
    private void disableOperators() {
        btnAddition.setEnabled(false);
        btnSubtraction.setEnabled(false);
        btnMultiplication.setEnabled(false);
        btnDivision.setEnabled(false);
    }

    private void enableOperators() {
        btnAddition.setEnabled(true);
        btnSubtraction.setEnabled(true);
        btnMultiplication.setEnabled(true);
        btnDivision.setEnabled(true);
    }

    private void disableEqual() {
        btnEqual.setEnabled(false);
    }

    private void enableEqual() {
        btnEqual.setEnabled(true);
    }

    private void resetEquation() {
        shouldResetTextResult = true;
        leftOperand = null;
        rightOperand = null;
        operation = Operation.UNDEFINED;
        result = 0.0;
    }

    // ========== Formatter to format textResult number to fit screen width ==========
    private DecimalFormat getFormatter(Double value) {
        DecimalFormatSymbols dfSymbols = DecimalFormatSymbols.getInstance(Locale.getDefault());
        DecimalFormat df = new DecimalFormat("0.#", dfSymbols);

        if (abs(value) >= 0 && abs(value) < 10) {
            df = new DecimalFormat("#.########", dfSymbols);
        } else if (abs(value) >= 10 && abs(value) < 100) {
            df = new DecimalFormat("#.#######", dfSymbols);
        } else if (abs(value) >= 100 && abs(value) < 1000) {
            df = new DecimalFormat("#.######", dfSymbols);
        } else if (abs(value) >= 1000 && abs(value) < 10000) {
            df = new DecimalFormat("#,###.#####", dfSymbols);
        } else if (abs(value) >= 10000 && abs(value) < 100000) {
            df = new DecimalFormat("#,###.####", dfSymbols);
        } else if (abs(value) >= 100000 && abs(value) < 1000000) {
            df = new DecimalFormat("#,###.###", dfSymbols);
        } else if (abs(value) >= 1000000 && abs(value) < 10000000) {
            df = new DecimalFormat("#,###.##", dfSymbols);
        } else if (abs(value) >= 10000000 && abs(value) < 100000000) {
            df = new DecimalFormat("#,###.#", dfSymbols);
        } else if (abs(value) >= 100000000) {
            df = new DecimalFormat("#,###", dfSymbols);
        }

        df.setRoundingMode(RoundingMode.HALF_UP);

        return df;
    }
}