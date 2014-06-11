package com.freakshow.pickadoor;

import com.freakshow.pickadoor.game.GameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Start extends Activity  {
    /** Called when the activity is first created. */
   public static final String NAME_KEY = "player_name";
   public static final String MBOX_KEY = "player_mbox";
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final TextView player_name = (TextView)findViewById(R.id.player_name);
        final TextView player_mbox = (TextView)findViewById(R.id.player_mbox);
        
        Button start_button = (Button)findViewById(R.id.start_button);
        start_button.setOnClickListener(new OnClickListener()
         {
            @Override
            public void onClick(View v)
            {
               Intent intent = new Intent(getApplicationContext(), GameActivity.class);
               intent.putExtra(NAME_KEY, player_name.getText().toString().trim());
               intent.putExtra(MBOX_KEY, player_mbox.getText().toString().trim());
               startActivity(intent);
            }
         });
    }
}