package com.freakshow.pickadoor;

import com.freakshow.pickadoor.game.GameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Start extends Activity  {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button start_button = (Button)findViewById(R.id.start_button);
        start_button.setOnClickListener(new OnClickListener()
         {
            @Override
            public void onClick(View v)
            {
               Intent intent = new Intent(getApplicationContext(), GameActivity.class);
               startActivity(intent);
            }
         });
    }
}