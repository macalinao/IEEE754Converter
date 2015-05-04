package pw.ian.ieee754converter;

import android.database.MatrixCursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DecimalFormat;


public class MainActivity extends ActionBarActivity {
    ToggleButton x32;
    ToggleButton x64;
    boolean is32Bit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x32 = (ToggleButton) findViewById(R.id.x32);
        x64 = (ToggleButton) findViewById(R.id.x64);
        x32.setChecked(is32Bit);

        x32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set32Bit(true);
            }
        });
        x64.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set32Bit(false);
            }
        });

        ((EditText) findViewById(R.id.number)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recalculate();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void set32Bit(boolean value) {
        final ToggleButton x32 = (ToggleButton) findViewById(R.id.x32);
        final ToggleButton x64 = (ToggleButton) findViewById(R.id.x64);
        x32.setChecked(value);
        x64.setChecked(!value);
        is32Bit = value;
        recalculate();
    }

    public void recalculate() {
        double number;
        long digits, sign, exponent, mantissa;
        try {
            number = Double.parseDouble(((EditText) findViewById(R.id.number)).getText().toString());

            digits = is32Bit ? // Get the binary representation
                    Float.floatToIntBits((float) number) :
                    Double.doubleToLongBits(number);

            sign = is32Bit ? // first bit
                    ((digits >>> 32 - 1) & 0b1)
                    : ((digits >>> 64 - 1) & 0b1);

            exponent = is32Bit ?
                    ((digits >>> 32 - 1 - 8) & 0b11111111) : // next 8 bits
                    ((digits >>> 64 - 1 - 11) & 0b11111111111); // next 11 bits

            mantissa = is32Bit ?
                    (digits & 0x7fffff) : // last 23 bits
                    (digits & 0x1fffffffffffffl); // last 53 bits
        } catch (NumberFormatException ex) {
            // Reset on invalid number
            digits = sign = exponent = mantissa = 0;
            return;
        }

        // Sign
        String signStr = Long.toBinaryString(sign);
        signStr = (sign == 1 ? "-1" : "+1") + "\n" + signStr;
        ((TextView) findViewById(R.id.sign)).setText(signStr);

        // Exponent w/ bias
        int expBias = is32Bit ? 127 : 1023;
        String expStr = Long.toBinaryString(exponent);
        expStr = (exponent + " - " + expBias + "= " + (exponent - expBias)) + "\n" + expStr;
        ((TextView) findViewById(R.id.exponent)).setText(expStr);

        // Mantissa
        String manStr = Long.toBinaryString(mantissa);
        manStr = (mantissa) + "\n" + manStr;
        ((TextView) findViewById(R.id.mantissa)).setText(manStr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
