package youyoucontact;

import com.example.youyouteresa.MainActivity;

import android.R;
import android.net.Uri;

public class YouYouContack {
	String mName;
	String mPhoneNum;
	String mSymbolString;
	int isHaveImage;
	String mImageUri;
	
	public YouYouContack(String name, String phoneNum){
		mName = name;
		mPhoneNum = phoneNum;
		mSymbolString = "0";
		isHaveImage = 0;
		Uri imgUri = Uri.parse("android.resource://" + MainActivity.PACKAGE_NAME+"/" + R.drawable.screen_background_light);
		mImageUri = imgUri.toString();
	}
	
	public void setName(String name){
		this.mName = name;
		return;
	}
	
	public void setPhoneNum(String phoneNum){
		this.mPhoneNum = phoneNum;
		return;
	}
	
	public void setSymbol(String sysbol){
		this.mSymbolString = sysbol;
		return;
	}
	
	public void setImageUri(String imageUri){
		this.mImageUri = imageUri;
		this.isHaveImage = 1;
		return;
	}
	
	public String getName(){
		return this.mName;
	}
	
	public String getPhoneNum(){
		return this.mPhoneNum;
	}
	
	public String getImageUri(){
		return this.mImageUri;
	}
	
	public String getSymbol(){
		return this.mSymbolString;
	}
}
