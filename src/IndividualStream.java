import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class IndividualStream implements IStream {

private int _runNumber;
private Queue<TimingRecord> runs;
private Queue<TimingRecord> pendingRuns;
private ArrayList<TimingRecord> completedRuns;

public IndividualStream(int runNumber)	
{
	_runNumber = runNumber;
	runs = new LinkedList<TimingRecord>();
	pendingRuns = new LinkedList<TimingRecord>();
	completedRuns = new ArrayList<TimingRecord>();
}

public void num(int BIB)
{
	pendingRuns.add(new TimingRecord(BIB));
}

public void cancelRecord()
{
	runs.peek().cancel();
	completedRuns.add(runs.poll());
	_runNumber++;
	System.out.println("Next Racer");
}

public void startRecord(LocalDateTime start)
{
	TimingRecord current = pendingRuns.poll();
	current.start(start);
	runs.add(current);
}

public void finishRecord(LocalDateTime finish)
{
	runs.peek().finish(finish);
	completedRuns.add(runs.poll());
	_runNumber++;
		
}

public void DNFRecord()
{
	runs.peek().DNF();
	completedRuns.add(runs.poll());
	_runNumber++;
}


@Override
public String toString() 
{
	StringBuilder concat = new StringBuilder("RUN\tBIB\tTIME\n");
	for(TimingRecord i : completedRuns)
	{
		concat.append(_runNumber + "\t" + i.toString() + "\n");
	}
	return concat.toString();
}

public void finishRecord(LocalDateTime finish, int channel)
{
	return;
	
}

public String displayRecords(){
	String records = "";
	LocalDateTime now = LocalDateTime.now();
	for(TimingRecord t : pendingRuns){
		records += t.get_BIB() + "\t" + now + "\n";
	}
	for(TimingRecord t : runs){
		Duration time = Duration.between(t.get_start(), now);
		records +=  t.get_BIB() + "\t" + time.getSeconds() + "." + time.getNano() + "\n";
	}
	if(completedRuns != null && completedRuns.size() != 0){
		TimingRecord lastRun = completedRuns.get(completedRuns.size() - 1);
		records += lastRun.get_BIB() + "\t" + lastRun.get_duration().getSeconds() + "." + lastRun.get_duration().getNano() + "\n";
	}
	return records;
}

}