package ChronoTimers;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import Command.Command;


public class Driver {
public static void main(String[] args) throws Exception
{
	//Create command reader here.
	
	ChronoTimer t = new ChronoTimer();
	Scanner input = new Scanner(System.in);
	
	
	
	//while(r.parse(input.nextLine()) != null);
	
	while(true){
		String line = input.nextLine();
		String lineSplits[] = line.split(" ");
		if(lineSplits[0].equals("TEST")){
		ArrayList<Command>commands = Command.parseCommandFile(new File(lineSplits[1]));
		for(int i=0; i<commands.size(); ++i){
			commands.get(i).execute(t);
			//System.out.println(commands.get(i).getTimestamp());
			if(i<commands.size()){
				Duration wait = Duration.between(LocalDateTime.now(t.getClock()), commands.get(i+1).getTimestamp());
				Thread.sleep(wait.toMillis());
			}
			
		}
			}else{
				Command c = Command.commandFromString(line);
				c.execute(t);
			}
		}
	}
}


