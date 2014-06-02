package com.freakshow.pickadoor.game.extras;

import com.freakshow.pickadoor.R;
import com.freakshow.pickadoor.game.GameActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class StatsActivity extends Activity
{
   private Round_Details_List rounds;
   private int score;
   
   private TableLayout table;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.stats_layout);
      table = (TableLayout)findViewById(R.id.stats_table_layout);
      parseBundle(getIntent().getExtras());
   }

   private void parseBundle(Bundle b)
   {
      rounds = b.getParcelable(Round_Details.DETAILS_ID);
      score = b.getInt(GameActivity.SCORE_ID);
   }
   
   protected void onResume()
   {
      super.onResume();
      displayStats(figureStats());
   }
   
   private void displayStats(Stats stats)
   {
      table.setStretchAllColumns(true);
      table.removeAllViews();
      
      TableRow score_row = new TableRow(StatsActivity.this);
      TextView score_text = new TextView(StatsActivity.this); 
      score_text.setText(R.string.stats_score);
      score_row.addView(score_text);
      TextView score_value = new TextView(StatsActivity.this);
      score_value.setText("" + stats.score);
      score_row.addView(score_value);
      table.addView(score_row);
      
      TableRow times_played_row = new TableRow(StatsActivity.this);
      TextView times_played_text = new TextView(StatsActivity.this); 
      times_played_text.setText(R.string.stats_times_played);
      times_played_row.addView(times_played_text);
      TextView times_played_value = new TextView(StatsActivity.this);
      times_played_value.setText("" + rounds.size());
      times_played_row.addView(times_played_value);
      table.addView(times_played_row);
      
      TableRow win_ratio_row = new TableRow(StatsActivity.this);
      TextView win_ratio_text = new TextView(StatsActivity.this); 
      win_ratio_text.setText(R.string.stats_win_ratio);
      win_ratio_row.addView(win_ratio_text);
      TextView win_ratio_value = new TextView(StatsActivity.this);
      win_ratio_value.setText("" + stats.win_ratio);
      win_ratio_row.addView(win_ratio_value);
      table.addView(win_ratio_row);
      
      TableRow stayed_count_row = new TableRow(StatsActivity.this);
      TextView stayed_count_text = new TextView(StatsActivity.this);
      stayed_count_text.setText(R.string.stayed_count);
      stayed_count_row.addView(stayed_count_text);
      TextView stayed_count_value = new TextView(StatsActivity.this);
      stayed_count_value.setText("" + stats.stayed_count);
      stayed_count_row.addView(stayed_count_value);
      table.addView(stayed_count_row);
      
      TableRow stayed_wins_row = new TableRow(StatsActivity.this);
      TextView stayed_wins_text = new TextView(StatsActivity.this);
      stayed_wins_text.setText(R.string.stayed_wins);
      stayed_wins_row.addView(stayed_wins_text);
      TextView stayed_wins_value = new TextView(StatsActivity.this);
      stayed_wins_value.setText("" + stats.stayed_wins);
      stayed_wins_row.addView(stayed_wins_value);
      table.addView(stayed_wins_row);
      
      TableRow stayed_win_ratio_row = new TableRow(StatsActivity.this);
      TextView stayed_win_ratio_text = new TextView(StatsActivity.this);
      stayed_win_ratio_text.setText(R.string.stayed_win_ratio);
      stayed_win_ratio_row.addView(stayed_win_ratio_text);
      TextView stayed_win_ratio_value = new TextView(StatsActivity.this);
      stayed_win_ratio_value.setText("" + stats.stayed_win_ratio);
      stayed_win_ratio_row.addView(stayed_win_ratio_value);
      table.addView(stayed_win_ratio_row);
      
      TableRow changed_count_row = new TableRow(StatsActivity.this);
      TextView changed_count_text = new TextView(StatsActivity.this);
      changed_count_text.setText(R.string.changed_count);
      changed_count_row.addView(changed_count_text);
      TextView changed_count_value = new TextView(StatsActivity.this);
      changed_count_value.setText("" + stats.changed_count);
      changed_count_row.addView(changed_count_value);
      table.addView(changed_count_row);
      
      TableRow changed_wins_row = new TableRow(StatsActivity.this);
      TextView changed_wins_text = new TextView(StatsActivity.this);
      changed_wins_text.setText(R.string.changed_wins);
      changed_wins_row.addView(changed_wins_text);
      TextView changed_wins_value = new TextView(StatsActivity.this);
      changed_wins_value.setText("" + stats.changed_wins);
      changed_wins_row.addView(changed_wins_value);
      table.addView(changed_wins_row);
      
      TableRow changed_win_ratio_row = new TableRow(StatsActivity.this);
      TextView changed_win_ratio_text = new TextView(StatsActivity.this);
      changed_win_ratio_text.setText(R.string.changed_win_ratio);
      changed_win_ratio_row.addView(changed_win_ratio_text);
      TextView changed_win_ratio_value = new TextView(StatsActivity.this);
      changed_win_ratio_value.setText("" + stats.changed_win_ratio);
      changed_win_ratio_row.addView(changed_win_ratio_value);
      table.addView(changed_win_ratio_row);
      
      TableRow first_pick_right_count_row = new TableRow(StatsActivity.this);
      TextView first_pick_right_count_text = new TextView(StatsActivity.this);
      first_pick_right_count_text.setText(R.string.first_pick_right_count);
      first_pick_right_count_row.addView(first_pick_right_count_text);
      TextView first_pick_right_count_value = new TextView(StatsActivity.this);
      first_pick_right_count_value.setText("" + stats.first_pick_right_count);
      first_pick_right_count_row.addView(first_pick_right_count_value);
      table.addView(first_pick_right_count_row);
      
      TableRow left_door_count_row = new TableRow(StatsActivity.this);
      TextView left_door_count_text = new TextView(StatsActivity.this);
      left_door_count_text.setText(R.string.left_door_count);
      left_door_count_row.addView(left_door_count_text);
      TextView left_door_count_value = new TextView(StatsActivity.this);
      left_door_count_value.setText("" + stats.doorcounts[0]);
      left_door_count_row.addView(left_door_count_value);
      table.addView(left_door_count_row);
      
      TableRow middle_door_count_row = new TableRow(StatsActivity.this);
      TextView middle_door_count_text = new TextView(StatsActivity.this);
      middle_door_count_text.setText(R.string.middle_door_count);
      middle_door_count_row.addView(middle_door_count_text);
      TextView middle_door_count_value = new TextView(StatsActivity.this);
      middle_door_count_value.setText("" + stats.doorcounts[1]);
      middle_door_count_row.addView(middle_door_count_value);
      table.addView(middle_door_count_row);
      
      TableRow right_door_count_row = new TableRow(StatsActivity.this);
      TextView right_door_count_text = new TextView(StatsActivity.this);
      right_door_count_text.setText(R.string.right_door_count);
      right_door_count_row.addView(right_door_count_text);
      TextView right_door_count_value = new TextView(StatsActivity.this);
      right_door_count_value.setText("" + stats.doorcounts[2]);
      right_door_count_row.addView(right_door_count_value);
      table.addView(right_door_count_row);
   }
   
   private Stats figureStats()
   {
      return new Stats(score, rounds).compute();
   }
}
