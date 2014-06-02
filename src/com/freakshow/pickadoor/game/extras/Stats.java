package com.freakshow.pickadoor.game.extras;

class Stats
{
   int score;
   Round_Details_List rounds;
   
   float win_ratio;
   int stayed_count;
   int stayed_wins;
   float stayed_win_ratio;
   int changed_count;
   int changed_wins;
   float changed_win_ratio;
   int first_pick_right_count;
   int[] doorcounts;
   
   
   Stats(int score, Round_Details_List rounds)
   {
      this.score = score;
      this.rounds = rounds;
   }

   Stats compute()
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