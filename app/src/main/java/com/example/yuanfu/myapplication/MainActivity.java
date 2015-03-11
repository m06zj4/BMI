package com.example.yuanfu.myapplication;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    TextView name, height, weight, show, oC, oF;
    EditText entername, enterheight, enterweight, enterC, enterF;
    Button button;
    public String showheight, showweight,showBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (TextView) findViewById(R.id.textView);
        height = (TextView) findViewById(R.id.textView2);
        weight = (TextView) findViewById(R.id.textView3);
        show = (TextView) findViewById(R.id.textView4);
       oC = (TextView) findViewById(R.id.textView5);
        oF = (TextView) findViewById(R.id.textView6);
        entername = (EditText) findViewById(R.id.editText);
        enterheight = (EditText) findViewById(R.id.editText2);
        enterweight = (EditText) findViewById(R.id.editText3);
        enterC = (EditText) findViewById(R.id.editText4);
        enterF = (EditText) findViewById(R.id.editText5);
        button = (Button) findViewById(R.id.button);

        // 硬解
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
                .build());
    }

    public void postData() {
// Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://172.20.174.49/0311/action_page.php");
        try {
// Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("height", showheight));
            nameValuePairs.add(new BasicNameValuePair("weight", showweight));
            nameValuePairs.add(new BasicNameValuePair("BMI", showBMI));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
                    HTTP.UTF_8));
// Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
        } catch (IOException e) {
        }
    }

    public void start(View view) {
//EditText        if ("".equals(enterC.getText().toString().trim()));
//判斷是否為空格        if ("".equals(enterF.getText().toString().trim()));
        String showname = entername.getText().toString();
        showheight = enterheight.getText().toString();
        showweight = enterweight.getText().toString();

        String showC = enterC.getText().toString();
        String showF = enterF.getText().toString();


        double height = Double.parseDouble(enterheight.getText().toString()) / 100;
        double weight = Double.parseDouble(showweight);
        double BMI = weight / (height * height);
        double C = Double.parseDouble(showC);
        double F = Double.parseDouble(showF);
        double oF = C * (9 / 5) + 32;
        double oC = (F - 32) * 5 / 9;
        BMI = (double)((int)(BMI*100)/100.0);

        show.setText("Name:" + showname + "\n" + "Height:" + showheight + "\n" + "weight:" + showweight + "\n" + "BMI:" + BMI + "\n" + "攝氏" + oC + "\n" + "華視" + oF);
        showBMI= String.valueOf(BMI);
        postData();
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
