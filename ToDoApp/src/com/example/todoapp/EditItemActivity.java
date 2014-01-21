package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	private String oldText;
	private EditText etItemText;
	private int editPos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		oldText = getIntent().getStringExtra("oldText");
		etItemText = (EditText) findViewById(R.id.etItemText);
		etItemText.setText(oldText);
		Selection.setSelection(etItemText.getText(), oldText.length());
		editPos = getIntent().getIntExtra("position", -1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}
	
	public void onSave(View v){
		Intent returnData = new Intent();
		returnData.putExtra("newText", etItemText.getText().toString());
		returnData.putExtra("position", editPos);
		setResult(RESULT_OK,returnData);
		this.finish();
	}

}
