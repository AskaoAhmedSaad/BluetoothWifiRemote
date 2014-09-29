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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.bluefi.remote.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DirectoryActivity extends ListActivity {
   
    private List<String> items = null;
   
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_directory);
        getFiles(new File("/").listFiles());
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        int selectedRow = (int)id;
        if(selectedRow == 0){
            getFiles(new File("/").listFiles());
        }else{
            File file = new File(items.get(selectedRow));
            
            if(file.isDirectory()){
                getFiles(file.listFiles());
            }else{
            	// get selected file directory
              //  Toast.makeText(getApplicationContext(), ""+file.getPath(), Toast.LENGTH_LONG).show();
                Intent i = new Intent("delta.bluefi.remote.SendFileActivity");
		    	Bundle extras = new Bundle();
		    	Bundle bundle = getIntent().getExtras();
				extras.putString("dir_path", "" + file.getPath());
				extras.putString("IP", bundle.getString("IP"));
				//---attach the Bundle object to the Intent object---
		    	i.putExtras(extras);                
		    	//---start the activity to get a result back---
		    	startActivityForResult(i, 1);
            }
        }
    }
    private void getFiles(File[] files){
        items = new ArrayList<String>();
        for(File file : files){
            items.add(file.getPath());
        }
        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,R.layout.file_list_row, items);
        setListAdapter(fileList);
    }
} 