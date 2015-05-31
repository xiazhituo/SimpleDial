package youyoucontact;

import com.example.youyouteresa.MainActivity;

import android.net.Uri;

public class YouYouContack {
	int mId;
	String mName;
	String mPhoneNum;
	String mSymbolString;
	int isHaveImage;
	String mImageUri;
	
	public YouYouContack(String name, String phoneNum){
		java.util.Random rand = new java.util.Random();
		mId = rand.nextInt();
		mName = name;
		mPhoneNum = phoneNum;
		mSymbolString = "0";
		isHaveImage = 0;
		Uri imgUri = Uri.parse("android.resource://" + MainActivity.PACKAGE_NAME+"/" + android.R.drawable.screen_background_light);
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
	
	public void setImageUri(String imageUri, int imageFlag){
		this.mImageUri = imageUri;
		this.isHaveImage = imageFlag;
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
	
	public int getIsHaveImage(){
		return this.isHaveImage;
	}
	
	public int getId(){
		return this.mId;
	}
}
