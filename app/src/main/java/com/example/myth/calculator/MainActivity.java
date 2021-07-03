package com.example.myth.calculator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText eTnr2;
    private EditText eTnr1;
    private TextView tVoperatie;

    private Double dBuff = null;
    private String sOperatie = "=";
    private static final String SAVE_OPERATIE = "SAVE_OPERATIE";
    private static final String SAVE_OPERATOR = "SAVE_OPERATOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        final long FIVE_MINUTES = 1000 * 60 * 8;
        Handler handler = new Handler();

        final Runnable r = () -> getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        handler.postDelayed(r, FIVE_MINUTES);

        eTnr2 = findViewById(R.id.result);
        eTnr1 = findViewById(R.id.newNumber);
        tVoperatie = findViewById(R.id.operation);

        Button btn0 = findViewById(R.id.button0);
        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button6);
        Button btn7 = findViewById(R.id.button7);
        Button btn8 = findViewById(R.id.button8);
        Button btn9 = findViewById(R.id.button9);

        Button btnDot = findViewById(R.id.buttonDot);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonMinus = findViewById(R.id.buttonMinus);
        Button buttonPlus = findViewById(R.id.buttonPlus);
        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonProcent = findViewById(R.id.btnProcent);
        Button btnRad = findViewById(R.id.btnRadical);

        Button btnC = findViewById(R.id.btnC);
        Button btnCE = findViewById(R.id.btnCE);

        btnCE.setOnClickListener(v -> {
            String s = eTnr1.getText().toString();
            if (s.length() > 0) {
                s = s.substring(0, s.length() - 1);
                eTnr1.setText(s);
            }
        });


        View.OnClickListener vocl = view -> {
            if (eTnr2 != null) {
                eTnr2.setText("");
                dBuff = null;
            }
            if (eTnr1 != null) {
                eTnr1.setText("");
                dBuff = null;
            }
            if (!sOperatie.equals("=")) {
                sOperatie = "=";
                tVoperatie.setText(sOperatie);
            }
        };
        btnC.setOnClickListener(vocl);

        View.OnClickListener listener = view -> {
            Button b = (Button) view;
            eTnr1.append(b.getText().toString());
        };
        btn0.setOnClickListener(listener);
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
        btn6.setOnClickListener(listener);
        btn7.setOnClickListener(listener);
        btn8.setOnClickListener(listener);
        btn9.setOnClickListener(listener);
        btnDot.setOnClickListener(listener);

        View.OnClickListener opListener = view -> {
            Button b = (Button) view;
            String sOperatie = b.getText().toString();
            String value = eTnr1.getText().toString();
            try {
                Double doubleValue = Double.valueOf(value);
                performOperation(doubleValue, sOperatie);
            } catch (NumberFormatException nfe) {
                eTnr1.setText("");
            }
            MainActivity.this.sOperatie = sOperatie;
            tVoperatie.setText(MainActivity.this.sOperatie);
        };
        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonProcent.setOnClickListener(opListener);
        btnRad.setOnClickListener(opListener);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_m, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setPositiveButton("OK", null);
            adb.setTitle("Calculator v1.0");
            adb.setMessage("Development: Stark C.");
            AlertDialog ad = adb.create();
            ad.show();
        } else if (item.getItemId() == R.id.contents) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setPositiveButton("OK", null);
            adb.setTitle("Help");
            adb.setMessage("???");
            AlertDialog ad = adb.create();
            ad.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle b) {
        b.putString(SAVE_OPERATIE, sOperatie);
        if (dBuff != null) {
            b.putDouble(SAVE_OPERATOR, dBuff);
        }
        super.onSaveInstanceState(b);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle b) {
        super.onRestoreInstanceState(b);
        sOperatie = b.getString(SAVE_OPERATIE);
        if (dBuff != null)
            dBuff = b.getDouble(SAVE_OPERATOR);
        tVoperatie.setText(sOperatie);
    }

    @SuppressLint("SetTextI18n")
    private void performOperation(Double dNr1, String sOperatie) {
        if (null == dBuff) {
            dBuff = dNr1;
        } else {

            if (this.sOperatie.equals("=")) {
                this.sOperatie = sOperatie;
            }

            switch (this.sOperatie) {
                case "=":
                case "%":
                    dBuff = dNr1;
                    break;
                case "/":
                    if (dNr1 == 0) {
                        dBuff = 0.0;
                    } else {
                        dBuff /= dNr1;
                    }
                    break;
                case "*":
                    dBuff *= dNr1;
                    break;
                case "-":
//                    String s = eTnr1.getText().toString();
//                    String s2 = eTnr2.getText().toString();
//                    if (s.length() == 0 && s2.length() == 0){
//                         eTnr1.setText("-");
//                    }else
                    dBuff -= dNr1;
                    break;
                case "+":
                    dBuff += dNr1;
                    break;
                case "Rad":
                    dBuff = Math.sqrt(dNr1);
                    break;
            }
        }
        eTnr2.setText(dBuff.toString());
        eTnr1.setText("");
    }
}
