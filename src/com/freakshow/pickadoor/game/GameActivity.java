package com.freakshow.pickadoor.game;

import gov.adlnet.xapi.client.StatementClient;
import gov.adlnet.xapi.model.Agent;

import java.net.MalformedURLException;
import java.util.Random;
import java.util.UUID;

import com.freakshow.pickadoor.R;
import com.freakshow.pickadoor.Start;
import com.freakshow.pickadoor.game.extras.Round_Details;
import com.freakshow.pickadoor.game.extras.Round_Details_List;
import com.freakshow.pickadoor.game.extras.StatsActivity;
import com.freakshow.pickadoor.game.parts.Round;
import com.freakshow.pickadoor.xapi.Utilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class GameActivity extends Activity
{
   public static final String SCORE_ID = "com.freakshow.pickadoor.game.score_id";
   private Round_Details_List rounds = new Round_Details_List();
   private Round current_round;
   public static Random r = new Random();
   private FrameLayout game_frame;
   private TextView score_view;
   private int score = 0;
   private Button quit_button;
   private Button next_button;
   private Button stats_button;
   
   //xapi
   private static final String ACTIVITY_ID = "http://tom.example.com/xapi/games/PickADoor";
   private StatementClient xapiclient;
   private Utilities xapiutil;
   
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.game_layout);
      game_frame = ((FrameLayout)findViewById(R.id.game_view_frame));
      game_frame.addView(new GameView(this));
      score_view = (TextView)findViewById(R.id.score_value);
      quit_button = (Button)findViewById(R.id.quit_button);
      quit_button.setOnClickListener(new OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
            finish();
         }
      });
      next_button = (Button)findViewById(R.id.next_button);
      next_button.setOnClickListener(new OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
            // test state
            if (current_round.current_state == Round.STATE.END)
            {
               // store round
               store();
               startNext();
            }
         }

         
      });
      stats_button = (Button)findViewById(R.id.stats_button);
      stats_button.setOnClickListener(new OnClickListener()
      {
         @Override
         public void onClick(View v)
         {
         // test state
            if (current_round.current_state == Round.STATE.END)
            {
               store();
               Intent i = new Intent(GameActivity.this, StatsActivity.class);
               Bundle b = new Bundle();
               b.putParcelable(Round_Details.DETAILS_ID, rounds);
               b.putInt(SCORE_ID, score);
               i.putExtras(b);
               startActivity(i);
            }
         }
      });
      
      // xapi
      try {
         xapiclient = new StatementClient("https://lrs.adlnet.gov/xapi/", "tom", "1234");
      }
      catch (MalformedURLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      String agent_name = getIntent().getStringExtra(Start.NAME_KEY);
      String agent_mbox = getIntent().getStringExtra(Start.MBOX_KEY);
      Agent agent = null;
      if (! ("".equals(agent_name) || "".equals(agent_mbox))) {
         if (! agent_mbox.startsWith("mailto:"))
            agent_mbox = "mailto:" + agent_mbox;
         agent = new Agent();
         agent.setName(agent_name);
         agent.setMbox(agent_mbox);
      }
      xapiutil = new Utilities(xapiclient, agent, ACTIVITY_ID);
   }
   
   protected void onStart()
   {
      super.onStart();
      xapiutil.setRegistrationId(UUID.randomUUID().toString());
      xapiutil.sendStartStatement();
   }

   @Override
   protected void onResume()
   {
      super.onResume();
      startNext();
      score_view.setText("" + score);
   }

   @Override
   protected void onRestoreInstanceState(Bundle savedInstanceState)
   {
      super.onRestoreInstanceState(savedInstanceState);
   }

   @Override
   protected void onSaveInstanceState(Bundle outState)
   {
      super.onSaveInstanceState(outState);
   }
   
   @Override
   protected void onPause()
   {
      super.onPause();
   }

   @Override
   protected void onStop()
   {
      super.onStop();
      // send stop statement
      xapiutil.sendStopStatement(score, rounds);
   }
   
   public Round getRound()
   {
      return current_round;
   }
   
   private void startNext()
   {
      game_frame.removeAllViews();
      game_frame.addView(new GameView(GameActivity.this));
      // start new round
      current_round = new Round(GameActivity.this);
   }
   
   private void store()
   {
      if (current_round.current_state == Round.STATE.END
         && ! rounds.contains(current_round))
      {
         // store round
         rounds.add(current_round.getDetails());
      }
   }

   public void roundFinished()
   {
      Round_Details details = current_round.getDetails();
      if (details.success)incrementScore();
      // get data from current round
      // create statement, add score 
      int round = rounds.size() + 1;
      xapiutil.sendRoundFinished(ACTIVITY_ID+"/round/"+round, round, details);
   }

   private void incrementScore()
   {
      score++;
      score_view.setText("" + score);
   }
}
