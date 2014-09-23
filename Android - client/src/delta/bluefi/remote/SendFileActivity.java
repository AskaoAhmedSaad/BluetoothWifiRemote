package delta.bluefi.remote;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bluefi.remote.R;

import delta.bluefi.remote.TouchActivity.ServerThread;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendFileActivity extends Activity {
	
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	Thread serverThread = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_file);
		
		TextView tvPath = (TextView) findViewById(R.id.tvPath);
		Bundle bundle = getIntent().getExtras();
		String dir_path = bundle.getString("dir_path");
		tvPath.setText(dir_path);		
		
		Button btn_send = (Button) findViewById(R.id.bSend);
		btn_send.setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					serverThread = new Thread(new ServerThread());
					serverThread.start();
				}
			});
	}
	
	private String dir_path (){
		Bundle bundle = getIntent().getExtras();
		String dir_path = bundle.getString("dir_path");
		return dir_path;
	}
	
	class ServerThread implements Runnable {

		public void run() {
		try {
				Bundle bundle = getIntent().getExtras();
				String IP = bundle.getString("IP");
				socket = new Socket(IP , 15123);
				// transfare file directory ***************************
	            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	            dos.writeUTF(dir_path ());
	            // send data in the file *************************
				System.out.println("Accepted connection : " + socket);
	            File transferFile = new File(dir_path ());
	            byte[] bytearray = new byte[(int) transferFile.length()];
	            FileInputStream fin = new FileInputStream(transferFile);
	            BufferedInputStream bin = new BufferedInputStream(fin);
	            bin.read(bytearray, 0, bytearray.length);
	            OutputStream os = socket.getOutputStream();
	            System.out.println("Sending Files...");
	            os.write(bytearray, 0, bytearray.length);
	            os.flush();
	            socket.close();
	            System.out.println("File transfer complete"); 

			} catch (IOException ex) {
				Logger.getLogger(TouchActivity.class.getName()).log(
						Level.SEVERE, null, ex);
			} 
		}
	}
}
