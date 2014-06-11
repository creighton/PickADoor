package com.freakshow.pickadoor.game.extras;

public class Stats
{
   int score;
   Round_Details_List rounds;
   
   public float win_ratio;
   public int stayed_count;
   public int stayed_wins;
   public float stayed_win_ratio;
   public int changed_count;
   public int changed_wins;
   public float changed_win_ratio;
   public int first_pick_right_count;
   public int[] doorcounts;
   
   
   public Stats(int score, Round_Details_List rounds)
   {
      this.score = score;
      this.rounds = rounds;
   }

   public Stats compute()
   {
      win_ratio = (float) score / rounds.size();
      
      stayed_count = rounds.timesStayed();
      stayed_wins = rounds.timesStayingWon();
      stayed_win_ratio = rounds.stayingWinRatio();
      
      changed_count = rounds.timesChanged();
      changed_wins = rounds.timesChangingWon();
      changed_win_ratio = rounds.changingWinRatio();
      
      first_pick_right_count = rounds.timesFirstPickRight();
      doorcounts = rounds.doorFrequency();
      return this;
   }
}