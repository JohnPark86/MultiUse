// Author: John Park
// FileName ListeningActivity.java
// Date: 3/20/14
// This Android program listens for what kind of
// Intent is sent and begins the proper Activity.

package com.example.homework5;

import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MultiUse extends Activity implements RadioGroup.OnCheckedChangeListener, OnClickListener
{
    RadioGroup  group1;
    RadioButton rb1,rb2,rb3;
    EditText et;
    String input;
    ImageButton ib;
    boolean phoneBool,mapBool, webBool;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_use);
       
        group1=(RadioGroup)findViewById(R.id.group);
        group1.setOnCheckedChangeListener(this);
        rb1=(RadioButton)findViewById(R.id.Phone);
        rb2=(RadioButton)findViewById(R.id.Web);
        rb3=(RadioButton)findViewById(R.id.maps);
        ib = (ImageButton)findViewById(R.id.btn1);
        ib.setOnClickListener(this);
        et=(EditText)findViewById(R.id.editText);
        et.setOnKeyListener(new View.OnKeyListener() 
        {
             public boolean onKey(View v, int keyCode, KeyEvent event) 
             {
             if((event.getAction()==KeyEvent.ACTION_DOWN)&& 
                     keyCode==KeyEvent.KEYCODE_ENTER)
             {  
               input = et.getText().toString().trim();
             }
            return false;
             }
    });
    }
    
    public void onClick(View view) 
    {
        Intent i = new Intent(this, ListeningActivity.class);
        Bundle b = new Bundle();
        String s = et.getText().toString().trim();
        if(phoneBool)
        {
            b.putString("Phone",s);
            phoneBool=false;
        }
        if(webBool)
        {
            String url = makeUrl(s);
            b.putString("Web",url);
            webBool=false;
            
        }
        if(mapBool)
        {
            b.putString("Map",s);
            mapBool=false;
        }    
        i.putExtras(b);
        startActivity(i);
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent x) 
    {
        if(resultCode==1)
        {
            if(requestCode==RESULT_OK)
            {
                String result = x.getStringExtra("result"); 
            }    
            else if(requestCode==RESULT_CANCELED)
            {
                
            }
            else
                super.onActivityResult(requestCode, resultCode, x);
        }
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.multi_use, menu);
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup rg, int id) 
    {
        Intent i = new Intent(this, ListeningActivity.class);
        Bundle b = new Bundle();
        switch(id)
        {
        case R.id.Phone:
            et.setHint("Ex: 555 555 5555");
            Toast.makeText(getApplicationContext(), 
                    "Please enter a 10 digit phone number", Toast.LENGTH_LONG).show();
             et.setInputType(InputType.TYPE_CLASS_NUMBER);
             phoneBool=true;
             break;
        case R.id.Web:
            et.setHint("Ex: www.google.com");
            Toast.makeText(getApplicationContext(), 
                    "Please enter a web address", Toast.LENGTH_LONG).show();
            et.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
            webBool=true;
            break;        
        case R.id.maps:
            et.setHint("Ex: 12.3,4.56");
            Toast.makeText(getApplicationContext(), 
                    "Please enter a location in latitude and longitude coordinates" +
                        " spaced by a comma.",Toast.LENGTH_LONG).show();
             mapBool=true;
             break;
        }
    }    
        
    public String makeUrl(String u)
    {
        StringTokenizer st = new StringTokenizer(u,".");
        if(st.countTokens()==2)
        {
            String url = "http://www.";
            String url1 = url.concat(u);
            return url1;
        }   
        else if(st.nextToken().equals("www"))
        {
            String url = "http://";
            String url1 = url.concat(u);
            return url1;
        }
        else
            return u;    
    }
    
}
