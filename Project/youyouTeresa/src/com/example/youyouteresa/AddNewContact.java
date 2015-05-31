package com.example.youyouteresa;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddNewContact extends Activity {
	private ImageView iv_image;
	private String mUriString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_contact);
		this.iv_image = (ImageView)this.findViewById(R.id.iv_image);
		this.mUriString = "NULL";
	}
	
	public void load(View view) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, 0);
	}
	
	public void returnToMain(View view){
		EditText edit_name = (EditText)findViewById(R.id.add_name);
		EditText edit_symbol = (EditText)findViewById(R.id.add_symbol);
		EditText edit_phonenum = (EditText)findViewById(R.id.add_phonenum);
		
		Intent intent = new Intent();
		intent.putExtra("uri", this.mUriString);
		intent.putExtra("name", edit_name.getText().toString());
		intent.putExtra("symbol", edit_symbol.getText().toString());
		intent.putExtra("phonenum", edit_phonenum.getText().toString());
		setResult(1001, intent);
		finish();
	}
	
	@Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (data != null) {  
            // Get Image uri 
            Uri uri = data.getData();  
            this.mUriString = uri.toString();
            this.iv_image.setImageURI(uri);
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }
}
