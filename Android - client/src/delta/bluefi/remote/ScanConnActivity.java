/* < BluetoothWifiRemote app (android touchpad for desktop - send files from android device to Desktop 
	- scroll slides in datashow presentation) - accross wifi and blutooth connection (tools: android / j2se).>
    Copyright (C) 2014 Askao(AhmedSaad)-Omar EzzElDien

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>. */  	

package delta.bluefi.remote;

import java.util.ArrayList;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.bluefi.remote.R;

public class ScanConnActivity extends Activity implements OnItemClickListener {

	Button btnAvailableDevices;
	ImageButton btnBluetoothEnable, btnWiFiEnable;
	ListView listAvailableDevices;

	ArrayAdapter<String> listAdapter;
	ArrayList<String> pairedDevices;

	BluetoothAdapter bluetoothAdapter;
	Set<BluetoothDevice> devicesArray;
	IntentFilter filter;
	BroadcastReceiver receiver;
	
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_conn);

		init();
		initUI();
		// getPairedDevices();
		// startDiscovery();

		/*		if (bluetoothAdapter == null) {
			Toast.makeText(getApplicationContext(), "no bluetooth detected",
					Toast.LENGTH_LONG).show();
			finish();
		}		*/
	}

	private void startDiscovery() {
		bluetoothAdapter.cancelDiscovery();
		bluetoothAdapter.startDiscovery();
	}

	private void getPairedDevices() {
		devicesArray = bluetoothAdapter.getBondedDevices();
		if (devicesArray.size() > 0) {
			for (BluetoothDevice device : devicesArray) {
				pairedDevices.add(device.getName());				
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplicationContext(),
					"Bluetooth must be enabled to continue", Toast.LENGTH_LONG)
					.show();
			finish();
		}
	}

	private void initUI() {
		btnBluetoothEnable.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!bluetoothAdapter.isEnabled()) {
					turnOnBT();
				}
			}
		});

		btnAvailableDevices.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				listAdapter.clear();
				getPairedDevices();
				startDiscovery();
				listAvailableDevices.setAdapter(listAdapter);
			}
		});
		
		Button btn_touch_activity = (Button) findViewById(R.id.btn_touch_activity);
	    btn_touch_activity.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					LayoutInflater layoutInflater = LayoutInflater.from(context);
					View promptView = layoutInflater.inflate(R.layout.prompt_input, null);
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					// set prompts.xml to be the layout file of the alertdialog builder
					alertDialogBuilder.setView(promptView);
					final EditText input = (EditText) promptView.findViewById(R.id.userInput);		
					// setup a dialog window
					alertDialogBuilder
							.setCancelable(false)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int id) {
											Intent i = new Intent("delta.bluefi.remote.TouchActivity");
									    	Bundle extras = new Bundle();
											extras.putString("IP", filterInput("" + input.getText()));
											//---attach the Bundle object to the Intent object---
									    	i.putExtras(extras);                
									    	//---start the activity to get a result back---
									    	startActivityForResult(i, 1);
										}
									})
							.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,	int id) {
											dialog.cancel();
											
										}
									});
					// create an alert dialog
					AlertDialog alertD = alertDialogBuilder.create();
					alertD.show();
				}
			});

	    Button btn_send_file = (Button) findViewById(R.id.btn_send_file);
	    btn_send_file.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					LayoutInflater layoutInflater = LayoutInflater.from(context);
					View promptView = layoutInflater.inflate(R.layout.prompt_input, null);
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					// set prompts.xml to be the layout file of the alertdialog builder
					alertDialogBuilder.setView(promptView);
					final EditText input = (EditText) promptView.findViewById(R.id.userInput);		
					// setup a dialog window
					alertDialogBuilder
							.setCancelable(false)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int id) {
											Intent i = new Intent("delta.bluefi.remote.DirectoryActivity");
									    	Bundle extras = new Bundle();
											extras.putString("IP", filterInput("" + input.getText()));
											//---attach the Bundle object to the Intent object---
									    	i.putExtras(extras);                
									    	//---start the activity to get a result back---
									    	startActivityForResult(i, 1);
										}
									})
							.setNegativeButton("Cancel",
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,	int id) {
											dialog.cancel();
											
										}
									});
					// create an alert dialog
					AlertDialog alertD = alertDialogBuilder.create();
					alertD.show();
				}
			});

	    
	}

	protected void turnOnBT() {
		Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		startActivityForResult(i, 1);
	}

	private void init() {
		btnBluetoothEnable = (ImageButton) findViewById(R.id.btn_bluetooth_enable);
		btnWiFiEnable = (ImageButton) findViewById(R.id.btn_wifi_enable);

		btnAvailableDevices = (Button) findViewById(R.id.btn_available_devices);

		listAvailableDevices = (ListView) findViewById(R.id.list_available_devices);

		listAvailableDevices.setOnItemClickListener(this);

		listAdapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_list_item_1, 0);
		listAvailableDevices.setAdapter(listAdapter);

		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		pairedDevices = new ArrayList<String>();
		filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (BluetoothDevice.ACTION_FOUND.equals(action)) {
					BluetoothDevice device = intent
							.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					// check paired devices ************************
					boolean ispaired = false;
					for(int i=0;i<pairedDevices.size(); i++){
						if(pairedDevices.get(i).equals(device.getName()))
							ispaired = true;
					}
					if(ispaired == true)
						listAdapter.add(device.getName() + "\n" + device.getAddress() + "\t\t\t\t\t\t\t\t" + "paired");
					else
						listAdapter.add(device.getName() + "\n" + device.getAddress());
					// ******************************************************
				} else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED
						.equals(action)) {
					// run some fuckin code
				} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
						.equals(action)) {
					// run some fuckin code
				} else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
					if (bluetoothAdapter.getState() == bluetoothAdapter.STATE_OFF) {
						turnOnBT();
					}
				}
			}
		};

		registerReceiver(receiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		registerReceiver(receiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver(receiver, filter);
		filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(receiver, filter);

	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		createMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return menuChoice(item);
	}

	private boolean menuChoice(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			if (bluetoothAdapter.isEnabled()) {
				bluetoothAdapter.disable();
			}
			return true;
		case 1:
			Toast.makeText(this,
					"WiFi has not been\nprogrammed in beta version",
					Toast.LENGTH_LONG).show();
			return true;
		}
		return false;

	}

	private void createMenu(Menu menu) {
		MenuItem mnu1 = menu.add(0, 0, 0, "Disable Bluetooth");
		{
			mnu1.setIcon(R.drawable.offbluetooth);
			mnu1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}
		MenuItem mnu2 = menu.add(0, 1, 1, "Disable WiFi");
		{
			mnu2.setIcon(R.drawable.offwifi);
			mnu2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		}

	}
	
	//******************* Filter Input ***************************************	
		public String filterInput(String input){
			String filterInput = "";
			String regx = "\\\"\'<>:;=";
		    char[] ca = regx.toCharArray();
		    for (char c : ca) {
		        input = input.replace(""+c, "");
		    }
			filterInput = input;
			return filterInput;
		}
	// ***********************************************************************

}
