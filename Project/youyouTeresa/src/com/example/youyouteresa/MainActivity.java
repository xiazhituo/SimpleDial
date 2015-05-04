package com.example.youyouteresa;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
	String[] mPhoneNumber = {"13524290043", "13916520460"};
	public static String PACKAGE_NAME;
	
	public static List<YouYouContack> mContactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				             WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		mContactList = new ArrayList<YouYouContack>();
		
		YouYouContack frog = new YouYouContack("frog", "13524290043");
		YouYouContack swan = new YouYouContack("swan", "13916520460");
		frog.setSymbol("F");
		swan.setSymbol("S");
		mContactList.add(frog);
		mContactList.add(swan);
		
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.myviewpager);
		myPagerAdapter = new MyPagerAdapter();
		viewPager.setAdapter(myPagerAdapter);
		PACKAGE_NAME = getApplicationContext().getPackageName();
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
    				"Seting!", Toast.LENGTH_LONG)
    				.show();
            return true;
        }else if (id == R.id.action_search){
        	Intent intent = new Intent(this, AddNewContact.class);
            //startActivity(intent);
            startActivityForResult(intent, 1000);
        }
        
        
        return super.onOptionsItemSelected(item);
    }
	
    public void makeCall(View view) {
    	int index = viewPager.getCurrentItem();
    	
        String mobile = mPhoneNumber[index];  
        Intent intent = new Intent("android.intent.action.CALL", Uri  
                .parse("tel:" + mobile));             
        startActivity(intent);

    }
    
    @Override
    protected void onActivityResult(int requsetCode, int resultCode, Intent data){
    	super.onActivityResult(requsetCode, resultCode, data);
    	if(requsetCode == 1000 && resultCode == 1001){
    		String resultValue = data.getStringExtra("uri");
    		Uri newImageUri = Uri.parse(resultValue);
    		View newView = viewPager.findViewWithTag("viewTag" + viewPager.getCurrentItem());
    		//if (newView instanceof ImageView){
    			((ImageView) newView).setImageURI(newImageUri);
    		//}
    		
    		Toast.makeText(MainActivity.this,
					resultValue, Toast.LENGTH_LONG)
					.show();
    	}
    		
    }
    

	public class MyPagerAdapter extends PagerAdapter {

		int NumberOfPages = 2;
		public int imageindex;

		int[] res = { R.drawable.longzhu, R.drawable.swan};
		int[] backgroundcolor = { 0xA1887F, 0x776655};
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
			View dispView = getDisplayView(1, position);
			
			LayoutParams imageParams = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			dispView.setLayoutParams(imageParams);

			LinearLayout layout = new LinearLayout(MainActivity.this);
			layout.setOrientation(LinearLayout.VERTICAL);
			LayoutParams layoutParams = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			layout.setBackgroundColor(backgroundcolor[position]);
			layout.setLayoutParams(layoutParams);
			layout.addView(dispView);
			imageindex = position;
			
			//Add Tag For View
			String key = "viewTag" + position;
			dispView.setTag(key);
			
			final int page = position;
			layout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(MainActivity.this,
							"mImageIndex " + page + " clicked", Toast.LENGTH_LONG)
							.show();
				}
			});

			container.addView(layout);
			return layout;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((LinearLayout) object);
		}
		
	    public View getDisplayView(int isImage, int pos){
	    	View view;
	    	contactForView = (YouYouContack)MainActivity.mContactList.get(pos);
	    	
	    	if(1 == contactForView.getIsHaveImage()){
				//Uri imgUri = Uri.parse("android.resource://" + MainActivity.PACKAGE_NAME+"/" + res[pos]);
	    		Uri imgUri = Uri.parse(contactForView.getImageUri());
	    		ImageView imageView = new ImageView(MainActivity.this);
				imageView.setImageURI(imgUri);
				imageView.setColorFilter(Color.argb(25, 33, 49, 87));
				imageView.setScaleType(ScaleType.FIT_XY);
	    		view = imageView;
	    	}else{
	    		TextView textView = new TextView(MainActivity.this);
				textView.setTextColor(Color.BLACK);
				textView.setTextSize(120);
				textView.setTypeface(Typeface.DEFAULT_BOLD);
				//textView.setText(String.valueOf(pos));
				textView.setText(contactForView.getSymbol());
				textView.setGravity(Gravity.CENTER);
				view = textView;
	    	}
	    	
	    	return view;
	    }

	}

}