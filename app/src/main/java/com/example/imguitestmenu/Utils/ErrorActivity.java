package com.example.imguitestmenu.Utils;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.imguitestmenu.R;


public class ErrorActivity extends AppCompatActivity {
    
    public static final String TAG = "ErrorActivity";
	public TextView tv1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     setContentView();
         setContentView(R.layout.error_view);

		//Toolbar toolbar=(Toolbar)findViewById(R.id.errortoolbar);
		
		//setSupportActionBar(toolbar);
		tv1=findViewById(R.id.error_view_text);
		Intent Intent=this.getIntent();
		
		tv1.setText(Intent.getStringExtra("Errmsg"));
    }
    
}
