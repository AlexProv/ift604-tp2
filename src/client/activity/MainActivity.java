package client.activity;

import java.io.IOException;

import client.InitialiseurDeRequete;
import client.Communication.Client;

import com.example.tp2.R;

import common.Commands;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private String ip ="192.168.0.101";
	private int port1 = 9876;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("LaSoireeDuHockey");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        TextView tv = (TextView)findViewById(R.id.textView1);
        tv.setText("Liste des parties :");
		try {
			Client client = new Client(ip, port1);
			InitialiseurDeRequete idr = new InitialiseurDeRequete(client);
			StringBuilder data = idr.ParseAnswer(client.envoyerRequete(Commands.GET_LIST_MATCH.toString()));
			String[] lines = data.toString().split("\\n");
			for (String line : lines) {
				createButton(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void createButton(String text){
		Button myButton = new Button(this);
		int index = text.indexOf(":");
		int id = Integer.parseInt(text.substring(0, index));
		String equipes = text.substring(index + 1);
		myButton.setText(equipes);
		myButton.setId(id);
		LinearLayout ll = (LinearLayout)findViewById(R.id.layoutButton);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll.addView(myButton, lp);
	}
}
