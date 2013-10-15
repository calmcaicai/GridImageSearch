package com.example.gridimagesearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AdvancedSearchOptionActivity extends Activity {

	EditText etSize, etColor, etType, etSite;
	boolean validatedSize,validatedColor,validatedType,validatedSite;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advanced_search_option);
		etSize = (EditText)findViewById(R.id.etSize);
		etColor = (EditText)findViewById(R.id.etColor);
		etType = (EditText)findViewById(R.id.etType);
		etSite = (EditText)findViewById(R.id.etSite);
		
	}
	/*imgsz=icon restricts results to small images
			imgsz=small|medium|large|xlarge restricts results to medium-sized images
			imgsz=xxlarge restricts results to large images
			imgsz=huge restricts resykts to extra-large images*/
	public void validateSize(){
		etSize.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
				String str = etSize.getText().toString();
				if(str == null||str.isEmpty()){
					etSize.setError("Not a validated Size String !");
				}
				str = str.trim().toLowerCase();
				if(!str.equals("icon") && !str.equals("small") && !str.equals("medium") && !str.equals("large") && !str.equals("xlarge") 
						&& !str.equals("xxlarge") && !str.equals("huge"))
					etSize.setError("Not a validated Size String ! ");
				}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}});
	}
	
	/*imgtype=face restricts results to images of faces.
			imgtype=photo restricts results to photographic images.
			imgtype=clipart restricts results to clipart images.
			imgtype=lineart restricts results to line drawing images.*/
	
	public void validateType(){
		etType.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
				String str = etType.getText().toString();
				if(str == null||str.isEmpty()){
					etType.setError("Not a validated ");
				}
				str = str.trim().toLowerCase();
				if(!str.equals("face") && !str.equals("photo") && !str.equals("clipart") && !str.equals("lineart"))
					etType.setError("");
				}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}});
	}
	
	/*imgcolor=black
			imgcolor=blue
			imgcolor=brown
			imgcolor=gray
			imgcolor=green
			imgcolor=orange
			imgcolor=pink
			imgcolor=purple
			imgcolor=red
			imgcolor=teal
			imgcolor=white
			imgcolor=yellow*/
	public void validateColor(){
		etColor.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
				String str = etColor.getText().toString();
				if(str == null||str.isEmpty()){
					etColor.setError("Not a validated ");
				}
				str = str.trim().toLowerCase();
				if(!str.equals("black") && !str.equals("blue") && !str.equals("brown") && !str.equals("gray")
						&&!str.equals("green") && !str.equals("orange") && !str.equals("pink") && !str.equals("purple")
						&&!str.equals("red") && !str.equals("teal") && !str.equals("white") && !str.equals("yellow"))
					etColor.setError("");
				}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}});
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.advanced_search_option, menu);
		return true;
	}
	
	public void saveOptions(View v){
		Intent data = new Intent();
		  data.putExtra("size", etSize.getText().toString());
		  data.putExtra("color",etColor.getText().toString());
		  data.putExtra("type", etType.getText().toString());
		  data.putExtra("site", etSite.getText().toString());
		  
		  setResult(RESULT_OK, data);
		  super.finish();
	}

}
