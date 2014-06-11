package com.freakshow.pickadoor.xapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import com.freakshow.pickadoor.game.extras.Round_Details;
import com.freakshow.pickadoor.game.extras.Round_Details_List;
import com.freakshow.pickadoor.game.extras.Stats;

import gov.adlnet.xapi.client.StatementClient;
import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.ActivityDefinition;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.Context;
import gov.adlnet.xapi.model.ContextActivities;
import gov.adlnet.xapi.model.Result;
import gov.adlnet.xapi.model.Score;
import gov.adlnet.xapi.model.Statement;
import gov.adlnet.xapi.model.Verb;
import gov.adlnet.xapi.model.Verbs;
import android.os.AsyncTask;

public class Utilities {
   private StatementClient client;
   private Agent agent;
   private String baseActivityId;
   private String registrationId;
   private Verb started;
   private Verb stopped;
   
   public Utilities(StatementClient client, Agent agent, String baseActId) {
      this.client = client;
      this.agent = agent;
      this.baseActivityId = baseActId;
      
      started = new Verb();
      started.setId("http://tom.example.com/xapi/verbs/started");
      HashMap<String, String> starteddisplay = new HashMap<String, String>();
      starteddisplay.put("en-US", "started");
      started.setDisplay(starteddisplay);
      
      stopped = new Verb();
      stopped.setId("http://tom.example.com/xapi/verbs/started");
      HashMap<String, String> stoppeddisplay = new HashMap<String, String>();
      stoppeddisplay.put("en-US", "stopped");
      stopped.setDisplay(stoppeddisplay);
   }

   public void setAgent(Agent agent) {
      this.agent = agent;
   }
   
   public void setRegistrationId(String registrationId) {
      this.registrationId = registrationId;
   }
   
   public void sendStartStatement() {
      Statement s = new Statement();
      s.setActor(agent);
      s.setVerb(started);
      s.setObject(getBaseActivity());
      
      Context context = new Context();
      context.setRegistration(registrationId);
      s.setContext(context);
      
      new SendTask().execute(s);
   }
   
   public void sendStopStatement(int score, Round_Details_List detail_list) {
      Statement s = new Statement();
      s.setActor(agent);
      s.setVerb(stopped);
      s.setObject(getBaseActivity());
      
      Context context = new Context();
      context.setRegistration(registrationId);
      s.setContext(context);
      
      addFinalResults(s, score, detail_list);
 
      new SendTask().execute(s);
   }

   public void sendRoundFinished(String activityId, int round, Round_Details details) {
      Statement s = new Statement();
      s.setActor(agent);
      s.setVerb(Verbs.completed());
      s.setObject(getRoundActivity(activityId, round));
      
      Context context = new Context();
      context.setRegistration(registrationId);
      
      ContextActivities ca = new ContextActivities();
      ca.setParent(new ArrayList<Activity>());
      ca.getParent().add(getBaseActivity());
      
      context.setContextActivities(ca);
      s.setContext(context);
      
      addRoundDetails(s, details);
      
      new SendTask().execute(s);
   }
   
   private void addFinalResults(Statement s, int score, Round_Details_List detail_list) {
      Stats stats = new Stats(score, detail_list);
      
      Result res = new Result();
      
      Score sc = new Score();
      sc.setMin(0);
      sc.setMax(detail_list.size());
      sc.setRaw(score);
      sc.setScaled(stats.win_ratio);
      res.setScore(sc);
      
      res.setExtensions(new HashMap<String, String>());
      res.getExtensions().put(
            "tag:tom.example.com,2014:pickadoor/stayed-count", stats.stayed_count + "");
      res.getExtensions().put(
            "tag:tom.example.com,2014:pickadoor/stayed-wins", stats.stayed_wins + "");
      res.getExtensions().put(
            "tag:tom.example.com,2014:pickadoor/stayed-win-ratio", stats.stayed_win_ratio + "");
      res.getExtensions().put(
            "tag:tom.example.com,2014:pickadoor/changed-count", stats.changed_count + "");
      res.getExtensions().put(
            "tag:tom.example.com,2014:pickadoor/changed-wins", stats.changed_wins + "");
      res.getExtensions().put(
            "tag:tom.example.com,2014:pickadoor/changed-win-ratio", stats.changed_win_ratio + "");
      res.getExtensions().put(
            "tag:tom.example.com,2014:pickadoor/left-door-count", stats.doorcounts[0] + "");
      res.getExtensions().put(
            "tag:tom.example.com,2014:pickadoor/middle-door-count", stats.doorcounts[1] + "");
      res.getExtensions().put(
            "tag:tom.example.com,2014:pickadoor/right-door-count", stats.doorcounts[2] + "");
      
      s.setResult(res);
   }
   
   private void addRoundDetails(Statement s, Round_Details details) {
      Result res = new Result();
      
      Score score = new Score();
      score.setMin(0);
      score.setMax(1);
      score.setRaw(details.success ? 1 : 0);
      res.setScore(score);
      
      res.setSuccess(details.success);
      
      res.setExtensions(new HashMap<String, String>());
      res.getExtensions().put(
            "tag:tom.example.com,2014:pickadoor/correct-door", details.correct_door + "");
      res.getExtensions().put(
            "tag:tom.example.com,2014:pickadoor/final-pick", details.door_picked + "");
      res.getExtensions().put(
            "tag:tom.example.com,2014:pickadoor/changed-pick", details.changed_pick + "");
      
      s.setResult(res);
   }

   private Activity getBaseActivity(){
      Activity act = new Activity();
      act.setId(baseActivityId);
      
      ActivityDefinition def = new ActivityDefinition();
      def.setName(new HashMap<String, String>());
      def.getName().put("en-US", "PickADoor Android Game");
      def.setDescription(new HashMap<String, String>());
      def.getDescription().put("en-US", "Monty Hall Problem Android Game");
      act.setDefinition(def);
      
      return act;
   }
   
   private Activity getRoundActivity(String activityId, int round) {
      Activity act = new Activity();
      act.setId(activityId);
      
      ActivityDefinition def = new ActivityDefinition();
      def.setName(new HashMap<String, String>());
      def.getName().put("en-US", "PickADoor Android Game Round " + round);
      def.setDescription(new HashMap<String, String>());
      def.getDescription().put("en-US", "A single round of PickADoor");
      act.setDefinition(def);
      
      return act;
   }
   
   public class SendTask extends AsyncTask<Statement, Void, String> {

      @Override
      protected String doInBackground(Statement... params) {
         String response;
         
         if (agent == null) return "no agent defined for this game -- not tracking";
         
         try {
            response = client.publishStatement(params[0]);
         }
         catch (UnsupportedEncodingException e) {
            response = "unsupported encoding: " + e.getLocalizedMessage();
         }
         catch (IOException e) {
            response = "io exception: " + e.getLocalizedMessage();
         }
         return response;
      }
      
   }
}
