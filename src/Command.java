/**
 * Created by thomas on 2/23/15.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class Command 
{

    private LocalDateTime timeStamp;
    private String cmdName;
    private ArrayList<String> args;

    public Command(String timestamp, String cmdName, ArrayList<String> args) {
    	try{
    	String[] hoursminutes = timestamp.split(":");
    	String[] secondsmillis = hoursminutes[2].split("\\.");
    	timeStamp = LocalDateTime.of(LocalDateTime.now().getYear(), 
    										LocalDateTime.now().getMonth(), 
    										LocalDateTime.now().getDayOfMonth(), 
    										Integer.parseInt(hoursminutes[0]), 
    										Integer.parseInt(hoursminutes[1]), 
    										Integer.parseInt(secondsmillis[0]), 
    										Integer.parseInt(secondsmillis[1]));
    	}catch(Exception e){
    		
    	}
		this.cmdName = cmdName;
		this.args = args;
    }

    public LocalDateTime getTimestamp(){
    	return timeStamp;
    }
    public static Command commandFromString(String str)
    {
		String timeStamp = null;
  		String cmdName = null;
  		ArrayList<String> args = new ArrayList<String>();


	    StringTokenizer multiTokenizer = new StringTokenizer(str);

	    int index = 0;

	    while (multiTokenizer.hasMoreTokens())
	    {
	      //System.out.println(multiTokenizer.nextToken());
	      // USE TOKENS APPROPRIATELY
	      switch (index)
	      {
	        case 0:
	        	cmdName = multiTokenizer.nextToken();
	        break;


	        // CMD ARG
	        default:
	        	args.add(multiTokenizer.nextToken());
	        break;
	      }
	      ++ index;
	    }

		return    new Command(timeStamp, cmdName, args);
    }
    
    public static ArrayList<Command> parseCommandFile(File fin) throws IOException 
    {
    	ArrayList<Command> commandList = new ArrayList<Command>();

    	FileInputStream fis = new FileInputStream(fin);
 
	  //Construct BufferedReader from InputStreamReader
	  BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	 
	  String line = null;
	  while ((line = br.readLine()) != null) 
	   {
	  		String timeStamp = null;
	  		String cmdName = null;
	  		ArrayList<String> args = new ArrayList<String>();


		    StringTokenizer multiTokenizer = new StringTokenizer(line);

		    int index = 0;

		    while (multiTokenizer.hasMoreTokens())
		    {
		      //System.out.println(multiTokenizer.nextToken());
		      // USE TOKENS APPROPRIATELY
		      switch (index)
		      {
		        // TIMESTAMP
		        case 0:
		        	timeStamp = multiTokenizer.nextToken();
		        break;

		        // CMD NAME
		        case 1:
					cmdName = multiTokenizer.nextToken();
		        break;

		        // CMD ARG
		        default:
		        	args.add(multiTokenizer.nextToken());
		        break;
		      }
		      ++ index;
		    }

		    commandList.add(new Command(timeStamp, cmdName, args));
		}
		 
		br.close();

		return commandList;
    }

    public void execute(ChronoTimer timer) throws Exception
    {
    	if (this.cmdName.equals("ON"))
    	{
    		timer.turnOn();
    	}
    	else if (this.cmdName.equals("OFF"))
    	{
    		timer.turnOff();
    	}
    	else if (this.cmdName.equals("NUM"))
    	{
    		timer.num(Integer.parseInt(this.args.get(0)));
    	}    	
    	else if (this.cmdName.equals("CONN"))
    	{	
    		timer.connect(this.args.get(0), Integer.parseInt(this.args.get(1)));
    	}
    	else if (this.cmdName.equals("TOGGLE"))
    	{
    		timer.toggle(Integer.parseInt(this.args.get(0)));
    	}
    	else if (this.cmdName.equals("START"))
    	{
    		timer.start();
    	}
    	else if (this.cmdName.equals("FIN"))
    	{
    		timer.end();
    	}
    	else if (this.cmdName.equals("EXIT"))
    	{
    		System.exit(0);
    	}
    	else if(this.cmdName.equals("DNF")){
    		timer.DNF();
    	}
    	else if(this.cmdName.equals("PRINT")){
    		timer.print();
    	}
    	else if(this.cmdName.equals("TIME")){
    		String[] split = args.get(0).split(":");
    		LocalDateTime set = LocalDateTime.of(LocalDateTime.now().getYear(), 
    											LocalDateTime.now().getMonth(), 
    											LocalDateTime.now().getDayOfMonth(), 
    											Integer.parseInt(split[0]), 
    											Integer.parseInt(split[1]), 
    											Integer.parseInt(split[2]));
    		timer.time(set);
    	}
    	else if(this.cmdName.equals("EVENT")){
    		timer.event(args.get(0));
    	}
    	else if(this.cmdName.equals("EXPORT")){
    		timer.export(Integer.parseInt(args.get(0)));
    	}
    	else if(this.cmdName.equals("TRIG")){
    		timer.trigger(Integer.parseInt(args.get(0)));
    	}
    	else if(this.cmdName.equals("NEWRUN")){
    		timer.newRun();
    	}
    	else if(this.cmdName.equals("ENDRUN")){
    		timer.endRun();
    	}
    	else if(this.cmdName.equals("")){}
    	else{
    		System.out.println(" dpofjpwf :   " + this.cmdName);
    		throw new Exception("Invalid command name");
    	}
    }
}


