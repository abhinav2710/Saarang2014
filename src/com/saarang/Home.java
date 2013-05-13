package com.saarang;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/*
 * This is supposed to be the home page for the app.
 * Consists of all the rings in different Image views. ( Can be clubbed into a single Image View)
 * Each ring will connect to different page of the app.
 * White - Proshow
 * Blue  - Event
 * 
 *  Animation done using 9oldsandroid, mimics all the view animation available from 3.0+ to all the old Android versions! (Jake Wharton! \m/)
 */
public class Home extends Activity {
    /** Called when the activity is first created. */
	
	ImageView OrangeView, GreenView, BlueView, WhiteView, MapButton, HospiButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_layout);
        
        CopyMap();
        
        OrangeView = (ImageView) findViewById(R.id.imageView3);
        GreenView = (ImageView) findViewById(R.id.imageView2);
        BlueView = (ImageView) findViewById(R.id.imageView5);
        WhiteView = (ImageView) findViewById(R.id.imageView6);
        MapButton = (ImageView) findViewById(R.id.imageView4);
        HospiButton = (ImageView) findViewById(R.id.imageView7);
        
        InitAnim();
        KeyAnim();
        
        
        OrangeView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				
				Display display = getWindowManager().getDefaultDisplay();
				
				int w = display.getWidth();
				int h = display.getHeight();
				
				Log.i("Width",Integer.toString(w));
				Log.i("Height",Integer.toString(h));
				
				Log.i("X ",Float.toString(event.getX()));
				Log.i("Y",Float.toString(event.getY()));
				float X = event.getX() - v.getWidth()/2;
				float Y = event.getY() - v.getHeight()/2;
				float size = OrangeView.getWidth();
				Log.i("Size", Float.toString(size));
				double radius = Math.sqrt(X*X+Y*Y);
				size = size / 2;
				float whitelimit = size / 3;
				float bluelimit = 1.75f * size/3;
				float greenlimit =  size / 1.2f ;
				
				
				Log.i("TOUCH",Double.toString(radius));
				Intent intent;
				if(radius <= whitelimit){
					Log.i("Touch","White");
					
					//White
					Toast toast = Toast.makeText(Home.this,"White",Toast.LENGTH_SHORT);
					toast.setDuration(1);
					//toast.show();
				}
				else if(radius <= bluelimit){
					//Blue
					intent = new Intent(Home.this, SponsActivity.class);
					startActivity(intent);
				}
				else if(radius <= greenlimit){
					//Green
					intent = new Intent(Home.this, ProShowActivity.class);
					startActivity(intent);
				}
				else if(radius <= size){
					//Orange
					intent = new Intent(Home.this, EventsPage.class);
					startActivity(intent);
					
//					intent = new Intent(Home.this,
//							DepartmentActivity.class);
//					intent.putExtra("DEP_NAME", "Events");
//					intent.putExtra("DEP_ID", 2);
//					startActivity(intent);
					
				}
				return false;
			}
									
		});
        
        MapButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Home.this, MapActivity.class);
				startActivity(intent);
				return false;
			}
		});
    
        HospiButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Intent in = new Intent(Home.this, HospiActivity.class);
				startActivity(in);
				return false;
			}
		});
    }
    
      
        
    public void InitAnim(){
    	
    	ObjectAnimator RotatAnim = ObjectAnimator.ofFloat(OrangeView, "rotation", 150);
    	RotatAnim.setDuration(1);
    	RotatAnim.start();
    	
    	RotatAnim = ObjectAnimator.ofFloat(GreenView, "rotation", 200);
    	RotatAnim.setDuration(1);
    	RotatAnim.start();
    	
    	RotatAnim = ObjectAnimator.ofFloat(BlueView, "rotation", 300);
    	RotatAnim.setDuration(1);
    	RotatAnim.start();
    	
    	ObjectAnimator FadeAnim = ObjectAnimator.ofFloat(WhiteView, "alpha", 0);
    	FadeAnim.setDuration(1);
    	FadeAnim.start();
    	
    	AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    	am.playSoundEffect(AudioManager.FX_KEY_CLICK);
    }
    
    public void KeyAnim()
    {	
    	ObjectAnimator RotatAnim1 = ObjectAnimator.ofFloat(OrangeView, "rotation", 0);
    	RotatAnim1.setDuration(1500);
    	RotatAnim1.setInterpolator(new DecelerateInterpolator());
    	
    	ObjectAnimator RotatAnim2 = ObjectAnimator.ofFloat(GreenView, "rotation", 360);
    	RotatAnim2.setDuration(1500);
    	RotatAnim2.setInterpolator(new DecelerateInterpolator());
    	
    	ObjectAnimator RotatAnim3 = ObjectAnimator.ofFloat(BlueView, "rotation", 0);
    	RotatAnim3.setDuration(1500);
    	RotatAnim3.setInterpolator(new DecelerateInterpolator());
    	
    	ObjectAnimator FadeAnim = ObjectAnimator.ofFloat(WhiteView, "alpha", 100);
    	FadeAnim.setDuration(1000);
    	
    	AnimatorSet LockAnim = new AnimatorSet();
    	
    	LockAnim.play(RotatAnim1);
    	LockAnim.play(RotatAnim2).after(500);
    	LockAnim.play(RotatAnim3).after(1200);
    	LockAnim.play(FadeAnim).after(1500);
    	
    	ObjectAnimator Scalex1 = ObjectAnimator.ofFloat(OrangeView, "scaleX", 2);
    	ObjectAnimator Scalex2 = ObjectAnimator.ofFloat(GreenView, "scaleX", 2);
    	ObjectAnimator Scalex3 = ObjectAnimator.ofFloat(BlueView, "scaleX", 2);
    	ObjectAnimator Scalex4 = ObjectAnimator.ofFloat(WhiteView, "scaleX", 2);
    	ObjectAnimator Scaley1 = ObjectAnimator.ofFloat(OrangeView, "scaleY", 2);
    	ObjectAnimator Scaley2 = ObjectAnimator.ofFloat(GreenView, "scaleY", 2);
    	ObjectAnimator Scaley3 = ObjectAnimator.ofFloat(BlueView, "scaleY", 2);
    	ObjectAnimator Scaley4 = ObjectAnimator.ofFloat(WhiteView, "scaleY", 2);
    	
    	AnimatorSet ScaleAnim = new AnimatorSet();
    	ScaleAnim.play(Scalex1).with(Scalex2).with(Scalex3).with(Scalex4).with(Scaley4).with(Scaley1).with(Scaley2).with(Scaley3);
    	ScaleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
    	ScaleAnim.setDuration(1);
    	
    	ScaleAnim.start();
    	LockAnim.setStartDelay(100);
    	LockAnim.start();    	

    }   


    private void CopyMap() {
        AssetManager assetManager = getAssets();
        String filename = "mapimage.kmz";
        
        //System.out.println("File name => "+filename);
        InputStream in = null;
        OutputStream out = null;
        try {
          in = assetManager.open(filename);   // if files resides inside the "Files" directory itself
          out = new FileOutputStream(Environment.getExternalStorageDirectory().toString() +"/" + filename);
          copyFile(in, out);
          in.close();
          in = null;
          out.flush();
          out.close();
          out = null;
        } catch(Exception e) {
            Log.e("tag", e.getMessage());
        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	
    	Toast toast = Toast.makeText(Home.this, "Credits \n Saarang MobOps 2013 \n \t Abhinav Garlapati \n \t Srisehan S \n \t Vivek Kumar Bagaria.", Toast.LENGTH_LONG);
    	toast.setGravity(Gravity.CENTER_HORIZONTAL	, 0	, 0);
		toast.setDuration(50);
		toast.show();
    	return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		menu.add("Credits");
		
		return true;
	}
}