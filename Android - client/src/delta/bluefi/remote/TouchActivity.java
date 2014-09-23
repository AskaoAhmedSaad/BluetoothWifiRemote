package delta.bluefi.remote;

import com.bluefi.remote.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TouchActivity extends Activity {

	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	Thread serverThread = null;

//	Button btnRightClick, btnLeftClick;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_touch);

//		btnLeftClick = (Button) findViewById(R.id.btnLeftClick);
//		btnLeftClick = (Button) findViewById(R.id.btnLeftClick);	
		this.serverThread = new Thread(new ServerThread());
		this.serverThread.start();

	}

	@Override
	protected void onStop() {
		super.onStop();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void rightClickMethod(View v){
		// Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_LONG).show();
		try {
			dos.writeUTF("R");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void leftClickMethod(View v){
		//Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_LONG).show();
		try {
			dos.writeUTF("L");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void forward(View view){
		try {
			dos.writeUTF("F");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void backward(View view){
		try {
			dos.writeUTF("B");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class ServerThread implements Runnable {

		public void run() {
			try {
				Bundle bundle = getIntent().getExtras();
				String IP = bundle.getString("IP");
				socket = new Socket(IP, 8001);
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				
			} catch (IOException ex) {
				Logger.getLogger(TouchActivity.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// get screen width
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		// get x and y
		int x = (int) ((double) event.getX() / (double) width * 100);
		int y = (int) ((double) event.getY() / (double) height * 100);

//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//		case MotionEvent.ACTION_MOVE:
//		case MotionEvent.ACTION_UP:
//		}
		// Toast.makeText(getApplicationContext(), "" + x + " / " + y,
		// Toast.LENGTH_SHORT).show();
		// Toast.makeText(getApplicationContext(), "" + width + " / " + height,
		// Toast.LENGTH_SHORT).show();
		if (socket == null) {
			System.out.println("There is no socket.!");
		} else {
			try {
				// send x and y to server
				String toBeSent = x + ":" + y;
				dos.writeUTF(toBeSent);
			} catch (IOException ex) {
				Logger.getLogger(TouchActivity.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

		return false;
	}

}