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
