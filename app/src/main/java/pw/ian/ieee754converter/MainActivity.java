package pw.ian.ieee754converter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;


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
    }

    public void set32Bit(boolean value) {
        final ToggleButton x32 = (ToggleButton) findViewById(R.id.x32);
        final ToggleButton x64 = (ToggleButton) findViewById(R.id.x64);
        x32.setChecked(value);
        x64.setChecked(!value);
        is32Bit = value;
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
