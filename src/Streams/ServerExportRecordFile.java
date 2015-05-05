package Streams;

import java.time.Duration;
import Streams.TimingRecord.STATUS;
import java.time.LocalDateTime;

import Streams.TimingRecord.STATUS;

public class ServerExportRecordFile {
	private int runNumber;
	private String duration;
	private int bib;

	
	public ServerExportRecordFile(int rn, Duration dur, int bib, Streams.TimingRecord.STATUS status) {
		// TODO Auto-generated constructor stub
		this.runNumber = rn;
		this.bib = bib;
		if(status==STATUS.CANCEL) this.duration = "CANCEL";
		else if(status==STATUS.START) {
			this.duration = "START";
		}
		else if(status==STATUS.DNF) {
			this.duration = "DNF";
		}
		else{
			this.duration =  dur.getSeconds() + "." +dur.getNano();
		}
		
	}
	
}
