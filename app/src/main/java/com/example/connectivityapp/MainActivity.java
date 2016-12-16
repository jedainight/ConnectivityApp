package com.example.connectivityapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button email, bluetooth, webpage, nfc, sms;
	ConnectivityManager getconnections;
	NfcManager nfcmanager;
	NfcAdapter nfcadapter;
	BluetoothAdapter GetBluetoothAdapter;
	@SuppressLint("NewApi")
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		email = (Button) findViewById(R.id.email);
		bluetooth = (Button) findViewById(R.id.bluetooth);
		webpage = (Button) findViewById(R.id.webpage);
		nfc = (Button) findViewById(R.id.nfc);
		sms = (Button) findViewById(R.id.sms);
		
		//Δημιουργία αντικειμένου της κλάσης ConnectivityManager()
		getconnections = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

		//Δημιουργία αντικειμένου της κλάσης NfcManager() και NfcAdapter()
		nfcmanager = (NfcManager) MainActivity.this.getSystemService(Context.NFC_SERVICE);
		nfcadapter = nfcmanager.getDefaultAdapter();
		
		//Δημιουργία αντικειμένου της κλάσης BluetoothAdapter()
		GetBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		//Intents για την μεταφορά του χρήστη στις ρυθμίσεις της συσκευής
		//startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
		//startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
		//Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG).show();
		
		email.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Δημιουργία αντικειμένων της κλάσης NetworkInfo
				NetworkInfo WiFi = getconnections.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				NetworkInfo Mobile = getconnections.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				
				if (WiFi.isConnected()==true || Mobile.isConnected()==true){
					Intent sentEmail = new Intent(Intent.ACTION_SEND);
					sentEmail.putExtra(Intent.EXTRA_EMAIL,new String[] { "Address@Address.com" });
					sentEmail.putExtra(Intent.EXTRA_SUBJECT, "Θέμα E-Mail");
					sentEmail.putExtra(Intent.EXTRA_TEXT, "Κείμενο E-Mail");
					sentEmail.setType("message/rfc822");
					startActivity(Intent.createChooser(sentEmail,"Επιλέξτε εφαρμογή αποστολής E-Mail:"));
				}
				else
				{
					Toast.makeText(MainActivity.this, "Σύνδεση μη διαθέσιμη", Toast.LENGTH_LONG).show();
					startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
				}
				
			}
		});
		
		webpage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NetworkInfo WiFi = getconnections.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				NetworkInfo Mobile = getconnections.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				
				if (WiFi.isConnected()==true || Mobile.isConnected()==true){
					Intent ShowWebPage = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
					startActivity(ShowWebPage);
				}
				else
				{
					Toast.makeText(MainActivity.this, "Σύνδεση μη διαθέσιμη", Toast.LENGTH_LONG).show();
					startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
				}

			}
		});

		bluetooth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					
					if(GetBluetoothAdapter!=null)
					{
						if(GetBluetoothAdapter.isEnabled())
						{
							Intent shareImageIntent = new Intent();
							shareImageIntent.setAction(Intent.ACTION_SEND);
							shareImageIntent.setType("image/png");
							Uri uri = Uri.parse("android.resource://com.example.connectivityapp/"+ R.drawable.ic_launcher);
							shareImageIntent.putExtra(Intent.EXTRA_STREAM, uri);
							startActivity(Intent.createChooser(shareImageIntent,"Αποστολή εικόνας"));
						}
						else{
							Toast.makeText(MainActivity.this, "Bluetouth μη ενεργοποιημένο", Toast.LENGTH_LONG).show();
							startActivity(new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS));
						}
					}
					else{
						Toast.makeText(MainActivity.this, "Bluetouth μη διαθέσιμο", Toast.LENGTH_LONG).show();
					}
			}
		});

		nfc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(nfcadapter!=null){
					if(nfcadapter.isEnabled()){
						Toast.makeText(MainActivity.this, "NFC ενεργοποιημένο", Toast.LENGTH_LONG).show();
					}
					else
					{
						Toast.makeText(MainActivity.this, "NFC μη ενεργοποιημένο", Toast.LENGTH_LONG).show();
					}
				}
				else
				{
					Toast.makeText(MainActivity.this, "NFC μη διαθέσιμο", Toast.LENGTH_LONG).show();
				}
			}
		});

		sms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+ "12456"));
				intent.putExtra("sms_body", "Κείμενο");
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
