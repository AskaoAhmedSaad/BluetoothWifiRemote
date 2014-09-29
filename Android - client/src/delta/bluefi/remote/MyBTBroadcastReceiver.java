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

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBTBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		int state = intent.getExtras().getInt(BluetoothAdapter.EXTRA_STATE);
		switch (state) {
		case BluetoothAdapter.STATE_OFF:
			Toast.makeText(context, "Off", Toast.LENGTH_SHORT).show();
			break;
		case BluetoothAdapter.STATE_TURNING_OFF:
			Toast.makeText(context, "Turning Off", Toast.LENGTH_SHORT).show();
			break;
		case BluetoothAdapter.STATE_ON:
			Toast.makeText(context, "On", Toast.LENGTH_SHORT).show();
			break;
		case BluetoothAdapter.STATE_TURNING_ON:
			Toast.makeText(context, "Turning On", Toast.LENGTH_SHORT).show();
			break;
		}

	}

}
