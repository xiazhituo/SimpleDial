package com.example.youyouteresa;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import youyoucontact.YouYouContack;

public class MainActivity extends Activity {

	public ViewPager viewPager;
	MyPagerAdapter myPagerAdapter;
	public static String PACKAGE_NAME;
	
	public static List<YouYouContack> mContactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				             WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		mContactList = new ArrayList<YouYouContack>();
				
		dbInitial();
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.myviewpager);
		myPagerAdapter = new MyPagerAdapter();
		viewPager.setAdapter(myPagerAdapter);
		PACKAGE_NAME = getApplicationContext().getPackageName();
		
		
	}
	
	protected void onResume() {
		super.onResume();
		onCreate(null);
	};
	
	public void dbInitial(){
		SQLiteDatabase db = openOrCreateDatabase("contact.db", 0, null);
		
		db.execSQL("CREATE TABLE IF NOT EXISTS People(Id INTEGER PRIMARY KEY, Name TEXT, PhoneNum TEXT, Symbol TEXT, ImageFlag INT, ImageUri TEXT)");
		Cursor cur = db.rawQuery("SELECT * FROM People", null);
		if (cur.getCount() > 0){
		}else{
			db.execSQL("INSERT INTO People VALUES (1, 'swan', '13524290043', 'X', 0, 'NULL')");
		};
		cur = db.rawQuery("SELECT * FROM People", null);
		
		if(cur != null){
			while (cur.moveToNext()){
				YouYouContack people = new YouYouContack(cur.getString(cur.getColumnIndex("Name")), cur.getString(cur.getColumnIndex("PhoneNum")));
				people.setSymbol(cur.getString(cur.getColumnIndex("Symbol")));
				people.setImageUri(cur.getString(cur.getColumnIndex("ImageUri")), cur.getInt(cur.getColumnIndex("ImageFlag")));
				MainActivity.mContactList.add(people);
			}
		}
		
		cur.close();
		db.close();
		return;
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Toast.makeText(MainActivity.this,
    				"Setting!", Toast.LENGTH_LONG)
    				.show();
            return true;
        }else if (id == R.id.action_add_new){
        	Intent intent = new Intent(this, AddNewContact.class);
            startActivityForResult(intent, 1000);
        }
        
        
        return super.onOptionsItemSelected(item);
    }
	
    public void makeCall(View view) {
    	int index = viewPager.getCurrentItem();
    	
    	YouYouContack people = (YouYouContack)MainActivity.mContactList.get(index);
    	
        String mobile = people.getPhoneNum();  
        Intent intent = new Intent("android.intent.action.CALL", Uri  
                .parse("tel:" + mobile));             
        startActivity(intent);

    }
    
    @Override
    protected void onActivityResult(int requsetCode, int resultCode, Intent data){
    	super.onActivityResult(requsetCode, resultCode, data);
    	if(requsetCode == 1000 && resultCode == 1001){
    		String imageUriStr = data.getStringExtra("uri"); 		
    		String new_name = data.getStringExtra("name");
    		String new_symbol = data.getStringExtra("symbol");
    		String new_phonenum = data.getStringExtra("phonenum");
    		int image_flag = 1;
    		
    		if (imageUriStr.equals("NULL")){
    			image_flag = 0;
    		}
    				
    		SQLiteDatabase db = openOrCreateDatabase("contact.db", 0, null);
    		Cursor cur = db.rawQuery("SELECT * FROM People", null);
    		int id = cur.getCount() + 1;
    		cur.close();
    		
    		String insert_cmd = String.format("INSERT INTO People VALUES (%d, '%s', '%s', '%s', %d, '%s')", id, new_name,
    				new_phonenum, new_symbol, image_flag, imageUriStr);
    		db.execSQL(insert_cmd);
    		db.close();
    	}
    		
    }
    

	public class MyPagerAdapter extends PagerAdapter {
		private YouYouContack contactForView;

		@Override
		public int getCount() {
			//return NumberOfPages;
			return MainActivity.mContactList.size();
		}
		
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {			
			View dispView = getDisplayView(position);			
			LayoutParams imageParams = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			dispView.setLayoutParams(imageParams);

			LinearLayout layout = new LinearLayout(MainActivity.this);
			layout.setOrientation(LinearLayout.VERTICAL);
			LayoutParams layoutParams = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			layout.setLayoutParams(layoutParams);
			layout.addView(dispView);
			
			//Add Tag For View
			String key = "viewTag" + position;
			dispView.setTag(key);
			
			layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
						
				}
			});

			container.addView(layout);
			return layout;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((LinearLayout) object);
		}
		
	    public View getDisplayView(int pos){
	    	View view;
	    	contactForView = (YouYouContack)MainActivity.mContactList.get(pos);
	    	
	    	if(1 == contactForView.getIsHaveImage()){
	    		Uri imgUri = Uri.parse(contactForView.getImageUri());
	    		Log.d("Main_getDisplayView", contactForView.getImageUri());
	    		ImageView imageView = new ImageView(MainActivity.this);
				imageView.setImageURI(imgUri);
				imageView.setBackgroundColor(Color.WHITE);
				imageView.setScaleType(ScaleType.FIT_CENTER);
	    		view = imageView;
	    	}else{
	    		TextView textView = new TextView(MainActivity.this);
				textView.setTextColor(Color.BLACK);
				textView.setTextSize(120);
				textView.setTypeface(Typeface.DEFAULT_BOLD);
				textView.setText(contactForView.getSymbol());
				textView.setGravity(Gravity.CENTER);
				textView.setBackgroundColor(Color.WHITE);
				view = textView;
	    	}
	    	
	    	return view;
	    }

	}

}