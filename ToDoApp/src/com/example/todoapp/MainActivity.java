package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> aTodoAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private final int REQUEST_CODE = 86;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //populateArrayItems();
        readItems();
        aTodoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems = (ListView) findViewById(R.id.lvItems);
        
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        
        lvItems.setAdapter(aTodoAdapter);
        setupListViewListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    
    
    public void onAddItem(View v){
    	String itemText = etNewItem.getText().toString();
    	aTodoAdapter.add(itemText);
    	etNewItem.setText("");
    	writeItems();
    	
    }
    
    public void setupListViewListener(){
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
				todoItems.remove(pos);
				aTodoAdapter.notifyDataSetChanged();
				writeItems();
				return false;
			}
			
			
		});
    	
    	lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int pos,long id) {
				// TODO Auto-generated method stub
				Intent editIntent = new Intent(MainActivity.this,EditItemActivity.class);
				editIntent.putExtra("oldText", todoItems.get(pos));
				editIntent.putExtra("position", pos);
				startActivityForResult(editIntent,REQUEST_CODE);
			}
		});
    }
    
    
    private void readItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try{
    		todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch(IOException e){
    		todoItems = new ArrayList<String>();
    	}
    }
    
    private void writeItems(){
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, todoItems);
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    	
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
    		String newText = data.getExtras().getString("newText");
    		int editPos = data.getExtras().getInt("position");
    		todoItems.set(editPos, newText);
    		aTodoAdapter.notifyDataSetChanged();
    		writeItems();
    		Toast.makeText(this, newText, Toast.LENGTH_SHORT).show();
    	}
    }
}
