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