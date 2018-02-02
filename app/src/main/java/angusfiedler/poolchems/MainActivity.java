package angusfiedler.poolchems;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public TextView currentpH;
    public TextView newpH;
    public TextView Result;
    public TextView phChemical;
    public EditText poolCapacity;
    String phRaiseChemical = "Soda Ash";
    String phLowerChemical = "Sodium Bisulfate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        currentpH = (TextView) findViewById(R.id.textviewcurrentph);
        newpH = (TextView) findViewById(R.id.newpH);
        Result = (TextView) findViewById(R.id.textviewResult);
        poolCapacity = (EditText) findViewById(R.id.poolCap);
        poolCapacity.addTextChangedListener(new NumberTextWatcherForThousand(poolCapacity));
        phChemical = (TextView) findViewById(R.id.phChemical);


        Button buttonConvert = (Button) findViewById(R.id.buttonConvert);
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentpH.getText().length() > 0 && newpH.getText().length() > 0 && poolCapacity.getText().length() > 0) {
                    double currentPH = Double.parseDouble(currentpH.getText().toString());
                    double newPH = Double.parseDouble(newpH.getText().toString());

                    phChemical.setText(phChemical(currentPH, newPH));
                    String poolCapComma = poolCapacity.getText().toString();
                    final int poolCap = Integer.valueOf(poolCapComma.replaceAll(",", "").toString());
                    //TextView invalid = (TextView) findViewById(R.id.invalid)
                    if (newPH > currentPH) {
                        if(newPH > 7.5){
                            newpH.setText(Double.toString(7.5));
                            newPH = Double.parseDouble(newpH.getText().toString());
                            Result.setText(Double.toString((RoundTo2Decimals(sodaRaisePH(currentPH, newPH, poolCap)))));
                        }
                        else if (currentPH < 6.0){
                            Result.setText(Double.toString((RoundTo2Decimals(sodaRaisePH(currentPH, newPH, poolCap)))));
                        }
                    } else if (newPH == currentPH) {
                        Result.setText(Double.toString(0));
                    } else {
                        Result.setText(Double.toString(RoundTo2Decimals(bisulfateLowerPH(currentPH, newPH, poolCap))));
                    }
                }
            }
        });
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


    double RoundTo2Decimals(double val) {
        DecimalFormat df2 = new DecimalFormat("###.##");
        return Double.valueOf(df2.format(val));
    }


    private String phChemical(double currentph, double newph) {
        if (newph > currentph) {
            return phRaiseChemical;
        } else
            return phLowerChemical;
    }


    double total = 0;

    private double sodaRaisePH(double currentPH, double newPH, int poolCapacity) {
        List<Double> bracket = Arrays.asList(6.0, 6.1, 6.2, 6.3, 6.4, 6.5, 6.6, 6.7, 6.8, 6.9, 7.0, 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7, 7.8, 7.9, 8.0, 8.1, 8.2, 8.3, 8.4);
        //6.0 to 7.5
        List<Double> sodaRaise = Arrays.asList(31.0, 28.0, 25.0, 22.0, 20.0, 17.0, 15.0, 13.0, 12.0, 10.0, 8.6, 7.3, 6.2, 5.2, 4.4);


        for (int i = 14; i >= 0; i--) {
            if (currentPH >= newPH)
                return 0;
            else if (currentPH >= bracket.get(i) && newPH >= bracket.get(i + 1)) {
                return sodaRaisePH(bracket.get(i + 1), newPH, poolCapacity) + sodaRaise.get(i) * (poolCapacity / sPool);
            }
        }
        return total;
    }

    private double bisulfateLowerPH(double currentPH, double newPH, int poolCapacity) {
        List<Double> bracket = Arrays.asList(6.0, 6.1, 6.2, 6.3, 6.4, 6.5, 6.6, 6.7, 6.8, 6.9, 7.0, 7.1, 7.2, 7.3, 7.4, 7.5, 7.6, 7.7, 7.8, 7.9, 8.0, 8.1, 8.2, 8.3, 8.4);
        //8.4 to 7.8 array
        List<Double> bisulfateLower = Arrays.asList(2.2, 2.0, 1.9, 1.9, 2.0, 2.2);


        for (int i = 18, k = 5; i <= 24; i++, k--) {
            if (currentPH <= newPH || currentPH <= bracket.get(18))
                return 0;
            else if (currentPH <= bracket.get(i + 1) && newPH <= bracket.get(i)) {
                return bisulfateLowerPH(bracket.get(i), newPH, poolCapacity) + bisulfateLower.get(k) * (poolCapacity / sPool);
            }
        }
        return total;
    }


    private static final double sPool = 10000; //standard pool capacity
    //amount of Soda Ash needed per 10,000 gallons, in ounces by volume

}