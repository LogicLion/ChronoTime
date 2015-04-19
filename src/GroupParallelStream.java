import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GroupParallelStream implements IStream {

private int _runNumber;
private LocalDateTime globalStart;
private ArrayList<TimingRecord> runs;
private Queue<TimingRecord> pendingRuns;
private ArrayList<TimingRecord> completedRuns;

public GroupParallelStream(int runNumber)	
{
	_runNumber = runNumber;
	runs = new ArrayList<TimingRecord>();
	pendingRuns = new LinkedList<TimingRecord>();
	completedRuns = new ArrayList<TimingRecord>();
}

public void num(int BIB)
{
	pendingRuns.add(new TimingRecord(BIB));
}

public void cancelRecord()
{
	return;
}

public void startRecord(LocalDateTime start)
{
	globalStart=start;
	for(int i = 0; i < pendingRuns.size(); i++){
		runs.add(pendingRuns.poll());
	}
}


public void finishRecord(LocalDateTime finish, int channel)
{
	int index = channel/2 - 1;
	runs.get(index).setFinish(globalStart, finish);;
}

public void DNFRecord()
{
	return;
}

public String toString() 
{
	StringBuilder concat = new StringBuilder("RUN\tBIB\tTIME\n");
	for(TimingRecord i : completedRuns)
	{
		concat.append(_runNumber + "\t" + i.toString() + "\n");
	}
	return concat.toString();
}

@Override
public void finishRecord(LocalDateTime finish) {
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
	if(completedRuns != null){
		TimingRecord lastRun = completedRuns.get(completedRuns.size() - 1);
		records += lastRun.get_BIB() + "\t" + lastRun.get_duration().getSeconds() + "." + lastRun.get_duration().getNano() + "\n";
	}
	return records;
}

}