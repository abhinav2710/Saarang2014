package com.saarang;

/**
 * This is the Map Activity, duh.  
 * After parsing /mnt/sdcard/mapimage.kmz 
 * it uses classes from the packages com.utils and com.kml 
 * to help with handling the png, panning etc.
 * 
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.xmlpull.v1.XmlPullParserException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.utils.DisplayState;
import com.utils.GeoToImageConverter;
import com.utils.Globals;
import com.utils.ImageToScreenConverter;
import com.utils.InertiaScroller;
import com.utils.MapDisplay;
import com.utils.MapDisplay.MapImageTooLargeException;
import com.adapters.EventAdapter;
import com.database.DatabaseHelper;
import com.kml.GroundOverlay;
import com.kml.KmlFile;
import com.kml.KmlInfo;
import com.kml.KmlParser;
import com.kml.KmzFile;

public class MapActivity extends Activity {
    /** Called when the activity is first created. */
	private DatabaseHelper myDbHelper;
	private Cursor mCursor;
	private MapDisplay mapDisplay;
	private InertiaScroller inertiaScroller;
	private View testView1, testView2, testView3, testView4, testView5, testView6;
	private Globals g = Globals.getInstance();
	private MenuItem[] menuItem;
	private int[] eventID;
	private Cursor mCursor0;
	
    @TargetApi(9)
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.map);
    		Log.e("Map", "Activity Started!");
    	
    	mapDisplay = (MapDisplay) findViewById(R.id.mapDisplay);
    	if(mapDisplay==null) 
    		Log.e("", "R.id.mapDisplay not found!!!");
    	testView1 = findViewById(R.id.gcBtn);
    	testView2 = findViewById(R.id.icsrBtn);
    	testView3 = findViewById(R.id.libBtn);
    	testView4 = findViewById(R.id.oatBtn);
    	testView5 = findViewById(R.id.cltBtn);
    	testView6 = findViewById(R.id.sacBtn);
    	
    	DisplayState displayState = new DisplayState();
    	mapDisplay.setDisplayState(displayState);
    	inertiaScroller = new InertiaScroller(mapDisplay);
    	
    	myDbHelper = new DatabaseHelper(this);
    	try {
			Log.e("Map", "DBHelper Created");
			myDbHelper.createDataBase();
			myDbHelper.openDataBase();
		} catch (IOException ioe) {}

    	ImageButton zoomIn = (ImageButton) findViewById(R.id.zoomIn);
        ImageButton zoomOut = (ImageButton) findViewById(R.id.zoomOut);
        
        zoomIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mapDisplay.zoomMap(2.0f);
				alignButtons(g);
			}
		});

        zoomOut.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mapDisplay.zoomMap(0.5f);
				alignButtons(g);
			}
		});
        
        mapDisplay.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Log.e("Map", "Clicked " + g.getLocation() + ":D");
				alignButtons(g);
				//testView.performClick(); //Doesn't work for some reason. The context menu doesn't open fully
				return false;
			}
		});
        
        testView1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("Map", "Pin clicked");
				registerForContextMenu(testView1);
				//testView.showContextMenu();
				openContextMenu(testView1);
				//unregisterForContextMenu(testView);
				//VenueAdapter venueAdapter = new VenueAdapter();
				//setListAdapter(venueAdapter);
				}
		});
    
        testView2.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				Log.e("Map", "Pin clicked");
    				registerForContextMenu(testView2);
    				openContextMenu(testView2);
    				}
    		});
        
        testView3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("Map", "Pin clicked");
				registerForContextMenu(testView3);
				openContextMenu(testView3);
				}
		});

        testView4.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				Log.e("Map", "Pin clicked");
    				registerForContextMenu(testView4);
    				openContextMenu(testView4);
    				}
    		});
        
        testView5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("Map", "Pin clicked");
				registerForContextMenu(testView5);
				openContextMenu(testView5);
				}
		});
        
        testView6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("Map", "Pin clicked");
				registerForContextMenu(testView6);
				openContextMenu(testView6);
				}
		});

        // The following takes too long for the map to load. Hence use the sdcard way.
//        File localFile = null;
//		try {
//			localFile = File.createTempFile("map", "kmz");
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		Log.e("Map", "TempFile");
//		localFile.setWritable(true);
//		FileOutputStream fos = null;
//		try {
//			fos = new FileOutputStream(localFile, true);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//        InputStream is = null;
//        is = getResources().openRawResource(R.raw.mapimage);
//        int tmp = 0;
//        try {
//			tmp = is.read();
//		} catch (IOException e2) {
//			e2.printStackTrace();
//		}
//        while (tmp != -1) {
//        	try {
//        		fos.write(tmp);
//        		tmp = is.read();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//        }
//        try {
//			fos.close();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//        Log.e("Map", "Wrote");        
         
        // Ensure this file is present in your phone/emulator
        String localPath = "/mnt/sdcard/mapimage.kmz";
        File localFile = new File(localPath);
		try {
			launchSelectMap(localFile);
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MapImageTooLargeException e) {
			e.printStackTrace();
		}
		mapDisplay.centerOnMapCenterLocation();
		mapDisplay.zoomMap(0.5f);
		alignButtons(g);
    }
    
    private void alignButtons(Globals g) {
    	alignButtons(g, testView1);
    	alignButtons(g, testView2);
    	alignButtons(g, testView3);
    	alignButtons(g, testView4);
    	alignButtons(g, testView5);
    	alignButtons(g, testView6);
	}

	// Caution: works only with length three
    private int[] tokenize(String now) {
    	String[] temp = new String[3];
		temp = now.split(" ");
		
		int[] timeInfo = new int[3];
		for (int i = 0; i < 3; i++)
			timeInfo[i] = Integer.parseInt(temp[i]);
		
		return timeInfo;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    	super.onCreateContextMenu(menu, v, menuInfo);
		
		// Assuming max 10 locations
		menuItem = new MenuItem[30];
		eventID = new int[20];
		Arrays.fill(eventID, -1);
		
		// The first two arguements of menu.add are used to encode stuff, which gets decoded in onContextI....
		int row;
    	if(v == testView1) {
    		menu.setHeaderTitle("Classroom Complex (CRC)");
    		mCursor0 = myDbHelper.fetchDescription("CRC");
    		if (mCursor0 == null)
    			Log.e("Map", "Whoops!");
//    		Log.e("Map", now + " " + mCursor0.getColumnCount() + " " + mCursor0.getCount());
    		String event;
    		for (row = 0; row < mCursor0.getCount(); row++) {
    			eventID[row] = mCursor0.getInt(0);
    			event = mCursor0.getString(1);
    					menuItem[row] = menu.add(row, v.getId(), 0, event);
    			mCursor0.moveToNext();
    		}
    	}
    	else if(v == testView2) {
    		menu.setHeaderTitle("ICSR");
    		mCursor0 = myDbHelper.fetchDescription("ICSR");
    		if (mCursor0 == null)
    			Log.e("Map", "Whoops!");
    		String event;
    		for (row = 0; row < mCursor0.getCount(); row++) {
    			eventID[2 + row] = mCursor0.getInt(0);
    			event = mCursor0.getString(1);
				menuItem[2 + row] = menu.add(row, v.getId(), 0, event);
    			mCursor0.moveToNext();
    		}
    	}
    	else if(v == testView3) {
    		menu.setHeaderTitle("Media Resources Centre, Library");  
    		mCursor0 = myDbHelper.fetchDescription("MRC");
    		if (mCursor0 == null)
    			Log.e("Map", "Whoops!");
    		String event;
    		for (row = 0; row < mCursor0.getCount(); row++) {
    			eventID[4 + row] = mCursor0.getInt(0);
    			event = mCursor0.getString(1);
				menuItem[4 + row] = menu.add(row, v.getId(), 0, event);
    			mCursor0.moveToNext();
    		}
    	}
    	else if(v == testView5) {
    		menu.setHeaderTitle("Open Air Theatre (OAT)");
    		mCursor0 = myDbHelper.fetchDescription("OAT");
    		if (mCursor0 == null)
    			Log.e("Map", "Whoops!");
    		String event;
    		for (row = 0; row < mCursor0.getCount(); row++) {
    			eventID[6 + row] = mCursor0.getInt(0);
    			event = mCursor0.getString(1);
    					menuItem[6 + row] = menu.add(row, v.getId(), 0, event);
    			mCursor0.moveToNext();
    		}

    	}
    	else if(v == testView4) {
    		menu.setHeaderTitle("Central Lecture Theatre (CLT)");
    		mCursor0 = myDbHelper.fetchDescription("CLT");
    		if (mCursor0 == null)
    			Log.e("Map", "Whoops!");
    		String event;
    		for (row = 0; row < mCursor0.getCount(); row++) {
    			eventID[8 + row] = mCursor0.getInt(0);
    			event = mCursor0.getString(1);
    					menuItem[8 + row] = menu.add(row, v.getId(), 0, event);
    			mCursor0.moveToNext();
    		}

    	}
    	else if(v == testView6) {
    		menu.setHeaderTitle("SAC");
    		mCursor0 = myDbHelper.fetchDescription("SAC");
    		if (mCursor0 == null)
    			Log.e("Map", "Whoops!");
    		String event;
    		for (row = 0; row < mCursor0.getCount(); row++) {
    			eventID[10 + row] = mCursor0.getInt(0);
    			event = mCursor0.getString(1);
    					menuItem[10 + row] = menu.add(row, v.getId(), 0, event);
    			mCursor0.moveToNext();
    		}
    	}
    }  
  
    @Override  
    public boolean onContextItemSelected(MenuItem item) {
    	int id = item.getItemId();
//    	Log.e("Map", "id = " + id + " grpid = " + item.getGroupId());
    	for (int i = 0; i < 30; i++) {
    		if (menuItem[i] == null)
    			continue;
    		if (menuItem[i].getItemId() == id && menuItem[i].getGroupId() == item.getGroupId()) {
    			Log.e("Map", "Peace");
//    			if ((i + 1)%3 == 0) {
//    					switch (i + 1) {
//						case 3: Log.e("Map", "Go to GC");
//								break;
//						case 6: Log.e("Map", "Go to ICSR");
//								break;
//						case 9: Log.e("Map", "Go to LIB");
//								break;
//						case 12: Log.e("Map", "Go to CLT");
//						break;
//						case 15: Log.e("Map", "Go to OAT");
//						break;
//						case 18: Log.e("Map", "Go to SAC");
//						break;
//						default: break;
//    					}
//    					break;
//    			}
    			Intent intent = new Intent(MapActivity.this, EventInfoActivity.class);
				intent.putExtra("EventId", eventID[i]);
				startActivity(intent);
    			Log.e("Map", "event_id = " + eventID[(int) i]);
    			break;
    		}
    	}
    	return true;  
    }  
  
    private void alignButtons(Globals g, View v) {
    	float screen[] = new float[2];
    	float image[] = new float[2];
    	float geo[] = new float[2];        
    	
    	Log.e("Map", "Aligning buttons");
    	DisplayMetrics metrics = new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ViewGroup.LayoutParams vg_lp = v.getLayoutParams();
        RelativeLayout.LayoutParams rl_lp = new RelativeLayout.LayoutParams(vg_lp);
    	
        //This copying is required because of the way the convert functions work
        if(v == testView1) {
        	geo[0] = g.GC[0];
        	geo[1] = g.GC[1];
        }
        else if(v == testView2) {
        	geo[0] = g.ICSR[0];
        	geo[1] = g.ICSR[1];
        }
        else if(v == testView3) {
        	geo[0] = g.LIB[0];
        	geo[1] = g.LIB[1];
        }
        else if(v == testView4) {
        	geo[0] = g.CLT[0];
        	geo[1] = g.CLT[1];
        }
        else if(v == testView5) {
        	geo[0] = g.OAT[0];
        	geo[1] = g.OAT[1];
        }
        else if(v == testView6) {
        	geo[0] = g.SAC[0];
        	geo[1] = g.SAC[1];
        }
    	image = GeoToImageConverter.convertGeoToImageCoordinates(geo);
    	screen = ImageToScreenConverter.convertImageToScreenCoordinates(image);
//    	rl_lp.height = (int) (30*metrics.density);
//		rl_lp.width = (int) (35*metrics.density);
    	rl_lp.height = (int) (15*metrics.density);
		rl_lp.width = (int) (15*metrics.density);
    	rl_lp.leftMargin = (int) (screen[0] - 0.25*rl_lp.width);
		rl_lp.topMargin = (int) (screen[1] - 0.75*rl_lp.height);
		v.requestLayout();
        v.setLayoutParams(rl_lp);		
	}

    // Launches map from that file after parsing it
    private void launchSelectMap(File localFile) throws XmlPullParserException, IOException, MapImageTooLargeException  {
    	Log.e("", "launchSelectMap");
    	GroundOverlay selected = null;
		selected = parseLocalFile(localFile);
		if(selected != null) Log.e("", "selected.getImage" + selected.getImage()); 
		mapDisplay.setMap(selected);
		Log.e("MapActivity", "Map set");
    }

    // Parses that file into a map
	private GroundOverlay parseLocalFile(File mapFile) throws XmlPullParserException, IOException {
		Collection<KmlInfo> siblings = findKmlData(mapFile.getParentFile());
		Log.e("", "siblings.size" + siblings.size());
		KmlParser parser = new KmlParser();
		for( KmlInfo sibling : siblings ) {
//			Log.e("", "Check!!! " + sibling.getFile().getName());
			if( sibling.getFile().getName().equals(mapFile.getName()) ) {
				Log.e("", "" + mapFile.getName());
				Iterable<GroundOverlay> overlays = parser.readFile(sibling.getKmlReader());
				Iterator<GroundOverlay> iter = overlays.iterator();
				if( iter.hasNext() ) {
					GroundOverlay map = iter.next();
					map.setKmlInfo(sibling);
					Log.e("", "parseLocalFile returns map");
					return map;
				}
			}
		}
		return null;
	}

	private Collection<KmlInfo> findKmlData(File directory) throws ZipException, IOException {
		Collection<KmlInfo> kmlData = new ArrayList<KmlInfo>();
		if( directory == null || !directory.exists() || !directory.isDirectory() ) {
			return kmlData;
		}
		File[] files = directory.listFiles();
		for( File file : files) {
			if( file.getName().endsWith(".kml") ) {
				kmlData.add(new KmlFile(file));	
			} else if( file.getName().endsWith(".kmz") ) {
				Log.e("", "Check " + file.getName());
				ZipFile kmzFile;
				kmzFile = new ZipFile(file);
				Enumeration<? extends ZipEntry> kmzContents = kmzFile.entries();
				while( kmzContents.hasMoreElements() ) {
					ZipEntry kmzItem = kmzContents.nextElement();
					Log.e("", "Check!" + kmzItem.getName());
					if( kmzItem.getName().endsWith(".kml") ) {
						kmlData.add(new KmzFile(kmzFile, kmzItem));
//						Log.e("", "" + kmlData.size());
					}
				}
	
			}
		}
		return kmlData;
	}
	
	@Override
	  protected void onResume() {
		Log.e("", "Le resumed");
		super.onResume();
		Log.e("", "Le super resumed");
		mapDisplay.centerOnMapCenterLocation();
		mapDisplay.zoomMap(0.5f);
		alignButtons(g);
	}
}