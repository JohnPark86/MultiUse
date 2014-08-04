// Author: John Park
// FileName ListeningActivity.java
// Date: 3/20/14
// This Android program listens for what kind of
// Intent is sent and begins the proper Activity.

package com.example.homework5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class ListeningActivity extends Activity
{
    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_activity);
        Bundle b = getIntent().getExtras();  

        if(b!=null)
        {
            if(b.containsKey("Phone"))
            {    
                String s = b.getString("Phone");
                dial(s);
            }    
            if(b.containsKey("Web"))
            {    
                String s = b.getString("Web");
                invokeWebBrowser(s);
            }  
            if(b.containsKey("Map"))
            {    
                String s = b.getString("Map"); 
                String[] coordinates = s.split(",");
                double lat = Double.parseDouble(coordinates[0]);
                double lon = Double.parseDouble(coordinates[1]);
                showMap(lat,lon);
            }  
        }
    }
    
    private void dial(String phone)
    {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+phone));
        startActivity(intent);
    }
    
    public void invokeWebBrowser(String s)
    {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(s));
        startActivity(i);     
    }
    
    public void showMap(double lat,double lon)
    {
        Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("geo:"+lat+","+lon));
        startActivity(i);
    }
}
