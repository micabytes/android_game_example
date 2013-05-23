/*
 * Copyright 2013 MicaByte Systems
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.micabyte.android.example;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.micabyte.android.graphics.BitmapSurfaceRenderer;
import com.micabyte.android.graphics.MicaSurfaceView;

public class MainActivity extends Activity {
    private static final String TAG = "WorldMapActivity";
    private static final String KEY_X = "X";
    private static final String KEY_Y = "Y";
    private static final String KEY_FN = "FN";

    private GameController controller = new GameController();
    
    private MicaSurfaceView worldView;
    String filename;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        this.worldView = (MicaSurfaceView) findViewById(R.id.worldview);
        this.worldView.setListener(this.controller);
        BitmapSurfaceRenderer renderer = new BitmapSurfaceRenderer(this);
        InputStream iStream = getResources().openRawResource(R.drawable.world_map);
        try {
			renderer.setBitmap(iStream);
		}
		catch (IOException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
        try {
			iStream.close();
		}
		catch (IOException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
        this.worldView.setRenderer(renderer);
        // Centering the map to start
        this.worldView.centerViewPosition();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState() filename " + this.filename);
        Point p = this.worldView.getViewPosition();
        outState.putInt(KEY_X, p.x);
        outState.putInt(KEY_Y, p.y);
        outState.putString(KEY_FN, this.filename);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
