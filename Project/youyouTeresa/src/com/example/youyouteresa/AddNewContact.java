package com.example.youyouteresa;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AddNewContact extends Activity {
	private ImageView iv_image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_contact);
		this.iv_image = (ImageView)this.findViewById(R.id.iv_image);
	}
	
	public void load(View view) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, 0);
	}
	
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (data != null) {  
            // Get Image uri 
            Uri uri = data.getData();  
              
            this.iv_image.setImageURI(uri);   
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }
}