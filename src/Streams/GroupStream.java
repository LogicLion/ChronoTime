package Streams;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.google.gson.Gson;

import Streams.TimingRecord.STATUS;

public class GroupStream implements IStream {

private int _runNumber;
private int _bibNumber;
private LocalDateTime groupStart;
private Queue<TimingRecord> runs;
private Queue<TimingRecord> pendingRuns;
private ArrayList<TimingRecord> completedRuns;

public GroupStream(int runNumber)	
{
	_runNumber = runNumber;
	_bibNumber = 0;
	runs = new LinkedList<TimingRecord>();
	pendingRuns = new LinkedList<TimingRecord>();
	completedRuns = new ArrayList<TimingRecord>();
}

public void num(int BIB)
{
	return;
}

public void cancelRecord()
{
	_bibNumber++;
	completedRuns.add(new TimingRecord(_bibNumber, groupStart, TimingRecord.STATUS.CANCEL));
}

public void startRecord(LocalDateTime start)
{
	groupStart = start;
}

public void finishRecord(LocalDateTime finish)
{
	_bibNumber++;
	completedRuns.add(new TimingRecord(_bibNumber, groupStart, finish, TimingRecord.STATUS.FINISH));
}

public void DNFRecord()
{
	_bibNumber++;
	completedRuns.add(new TimingRecord(_bibNumber, groupStart, TimingRecord.STATUS.DNF));
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

public String displayRecords(Clock clock){
	String records = "";
	LocalDateTime now = LocalDateTime.now(clock);
	for(int i = 0; i<completedRuns.size(); i++){
		TimingRecord currentRecord = completedRuns.get(i);
		if(i == completedRuns.size()-1){
		records +=  currentRecord.get_BIB() + "\t" + currentRecord.get_duration().getSeconds() + "." + currentRecord.get_duration().getNano() + "\n";
		}
	}
	return records;
}

public String toJSON(Clock clock){
	Gson g = new Gson();
	ArrayList<ServerExportRecordFile> serfs = new ArrayList<ServerExportRecordFile>();
	for(TimingRecord i : completedRuns)
	{
		serfs.add(new ServerExportRecordFile(_runNumber, i.get_duration() , i.get_BIB(), i.get_eventCode()));
	}	
	String listItOut = g.toJson(serfs);
	System.out.println(listItOut);
	return listItOut;
}


}