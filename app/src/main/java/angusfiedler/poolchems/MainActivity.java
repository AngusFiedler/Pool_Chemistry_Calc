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
                    final double currentPH = Double.parseDouble(currentpH.getText().toString());
                    final double newPH = Double.parseDouble(newpH.getText().toString());

                    phChemical.setText(phChemical(currentPH,newPH));
                    String poolCapComma = poolCapacity.getText().toString();
                    final int poolCap = Integer.valueOf(poolCapComma.replaceAll(",", "").toString());
                    //TextView invalid = (TextView) findViewById(R.id.invalid)
                    if (newPH > currentPH) {
                        Result.setText(Double.toString((RoundTo2Decimals(sodaRaisePH(currentPH, newPH, poolCap)))));
                    }
                    else if (newPH == currentPH) {
                        Result.setText(Double.toString(0));
                    }
                    else {
                        Result.setText(Double.toString(RoundTo2Decimals(bisulfateLowerPH(currentPH,newPH,poolCap))));
                    }
                    }

                    //Result.setText(Double.toString(poolCap));
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




    private String phChemical(double currentph, double newph){
        if(newph > currentph){
            return phRaiseChemical;
        }
        else
            return phLowerChemical;
    }
    private double sodaRaisePH(double currentPH, double newPH, int poolCapacity)
    {
        if (currentPH >= bracket15)
            throw new IllegalArgumentException("This pH is high not low!");
        if (currentPH >= newPH)
            return 0;
        else if (currentPH >= bracket14 && newPH >= bracket15)
            return sodaRaise14 * (poolCapacity / sPool);
        else if (currentPH >= bracket13 && newPH >= bracket14)
            return sodaRaisePH(bracket14,newPH,poolCapacity) + sodaRaise13 * (poolCapacity / sPool);
        else if (currentPH >= bracket12 && newPH >= bracket13)
            return sodaRaisePH(bracket13,newPH,poolCapacity) + sodaRaise12 * (poolCapacity / sPool);
        else if (currentPH >= bracket11 && newPH >= bracket12)
            return sodaRaisePH(bracket12,newPH,poolCapacity) + sodaRaise11 * (poolCapacity / sPool);
        else if (currentPH >= bracket10 && newPH >= bracket11)
            return sodaRaisePH(bracket11,newPH,poolCapacity) + sodaRaise10 * (poolCapacity / sPool);
        else if (currentPH >= bracket9 && newPH >= bracket10)
            return sodaRaisePH(bracket10,newPH,poolCapacity) + sodaRaise9 * (poolCapacity / sPool);
        else if (currentPH >= bracket8 && newPH >= bracket9)
            return sodaRaisePH(bracket9,newPH,poolCapacity) + sodaRaise8 * (poolCapacity / sPool);
        else if (currentPH >= bracket7 && newPH >= bracket8)
            return sodaRaisePH(bracket8,newPH,poolCapacity) + sodaRaise7 * (poolCapacity / sPool);
        else if (currentPH >= bracket6 && newPH >= bracket7)
            return sodaRaisePH(bracket7,newPH,poolCapacity) + sodaRaise6 * (poolCapacity / sPool);
        else if (currentPH >= bracket5 && newPH >= bracket6)
            return sodaRaisePH(bracket6,newPH,poolCapacity) + sodaRaise5 * (poolCapacity / sPool);
        else if (currentPH >= bracket4 && newPH >= bracket5)
            return sodaRaisePH(bracket5,newPH,poolCapacity) + sodaRaise4 * (poolCapacity / sPool);
        else if (currentPH >= bracket3 && newPH >= bracket4)
            return sodaRaisePH(bracket4,newPH,poolCapacity) + sodaRaise3 * (poolCapacity / sPool);
        else if (currentPH >= bracket2 && newPH >= bracket3)
            return sodaRaisePH(bracket3,newPH,poolCapacity) + sodaRaise2 * (poolCapacity / sPool);
        else if (currentPH >= bracket1 && newPH >= bracket2)
            return sodaRaisePH(bracket2,newPH,poolCapacity) + sodaRaise1 * (poolCapacity / sPool);
        else if (currentPH >= bracket && newPH >= bracket1)
            return sodaRaisePH(bracket1,newPH,poolCapacity) + sodaRaise * (poolCapacity / sPool);

        else
            throw new IllegalArgumentException("An error has occurred");
    }

    private double bisulfateLowerPH(double currentPH, double newPH, int poolCapacity) {
        if (currentPH <= bracket10)
            return 0;
        if (currentPH <= newPH)
            return 0;
        else if (currentPH <= bracket19 && newPH <= bracket18)
            return bisulfateLower5 *(poolCapacity/sPool);
        else if (currentPH <= bracket20 && newPH <= bracket19)
            return bisulfateLowerPH(bracket19, newPH, poolCapacity) + bisulfateLower4 *(poolCapacity/sPool);
        else if (currentPH <= bracket21 && newPH <= bracket20)
            return bisulfateLowerPH(bracket20, newPH, poolCapacity) + bisulfateLower3 *(poolCapacity/sPool);
        else if (currentPH <= bracket22 && newPH <= bracket21)
            return bisulfateLowerPH(bracket21, newPH, poolCapacity) + bisulfateLower2 *(poolCapacity/sPool);
        else if (currentPH <= bracket23 && newPH <= bracket22)
            return bisulfateLowerPH(bracket22, newPH, poolCapacity) + bisulfateLower1 *(poolCapacity/sPool);
        else if (currentPH <= bracket24 && newPH <= bracket23)
            return bisulfateLowerPH(bracket23, newPH, poolCapacity) + bisulfateLower *(poolCapacity/sPool);

        else
            throw new IllegalArgumentException("An error has occurred");
    }




    private static final double sPool = 10000; //standard pool capacity
    //pH Levels
    private static final double bracket = 6.0;
    private static final double bracket1 = 6.1;
    private static final double bracket2 = 6.2;
    private static final double bracket3 = 6.3;
    private static final double bracket4 = 6.4;
    private static final double bracket5 = 6.5;
    private static final double bracket6 = 6.6;
    private static final double bracket7 = 6.7;
    private static final double bracket8 = 6.8;
    private static final double bracket9 = 6.9;
    private static final double bracket10 = 7.0;
    private static final double bracket11 = 7.1;
    private static final double bracket12 = 7.2;
    private static final double bracket13 = 7.3;
    private static final double bracket14 = 7.4;
    private static final double bracket15 = 7.5;
    private static final double bracket16 = 7.6;
    private static final double bracket17 = 7.7;
    private static final double bracket18 = 7.8;
    private static final double bracket19 = 7.9;
    private static final double bracket20 = 8.0;
    private static final double bracket21 = 8.1;
    private static final double bracket22 = 8.2;
    private static final double bracket23 = 8.3;
    private static final double bracket24 = 8.4;

    //amount of Bisulfate needed per 10,000 gallons, in ounces
    private static final double bisulfateLower = 2.2;   //8.4 to 8.3
    private static final double bisulfateLower1 = 2;  //8.3 to 8.2
    private static final double bisulfateLower2 = 1.9;  //8.2 to 8.1
    private static final double bisulfateLower3 = 1.9;   //8.1 to 8.0
    private static final double bisulfateLower4 = 2;  //8.0 to 7.9
    private static final double bisulfateLower5 = 2.2;  //7.9 to 7.8
    //amount of Soda Ash needed per 10,000 gallons, in ounces by volume
    private static final double sodaRaise = 31;     //6.0 to 6.1
    private static final double sodaRaise1 = 28;     //6.1 to 6.2
    private static final double sodaRaise2 = 25;     //6.2 to 6.3
    private static final double sodaRaise3 = 22;     //6.3 to 6.4
    private static final double sodaRaise4 = 20;     //6.4 to 6.5
    private static final double sodaRaise5 = 17;     //6.5 to 6.6
    private static final double sodaRaise6 = 15;     //6.6 to 6.7
    private static final double sodaRaise7 = 13;     //6.7 to 6.8
    private static final double sodaRaise8 = 12;     //6.8 to 6.9
    private static final double sodaRaise9 = 10;     //6.9 to 7.0
    private static final double sodaRaise10 = 8.6;     //7.0 to 7.1
    private static final double sodaRaise11 = 7.3;     //7.1 to 7.2
    private static final double sodaRaise12 = 6.2;     //7.2 to 7.3
    private static final double sodaRaise13 = 5.2;     //7.3 to 7.4
    private static final double sodaRaise14 = 4.4;     //7.4 to 7.5

}