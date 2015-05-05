package ChronoTimers;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.google.gson.Gson;

import Channels.Channel;
import Streams.GroupParallelStream;
import Streams.GroupStream;
import Streams.IStream;
import Streams.IndividualParallelStream;
import Streams.IndividualStream;
import Streams.ServerExportRecordFile;
import Streams.TimingRecord;
public class ChronoTimer {
	

	
	
	public enum Event
	{
		IND, GRP, PARIND, PARGRP
	}
	public JTextPane printer;
	IStream _stream;
	ArrayList<IStream> _streams;
	int _currentRun;
	Channel _channels[];
	boolean _isOn;
	Clock clock;
	ChronoTimer(){
		_currentRun = 1;
		_stream = new IndividualStream(_currentRun);
		_streams = new ArrayList<IStream>();
		_streams.add(_stream);

		_channels = new Channel[8];
		_channels[0] = new Channel();
		_channels[1] = new Channel();
		_channels[2] = new Channel();
		_channels[3] = new Channel();
		_channels[4] = new Channel();
		_channels[5] = new Channel();
		_channels[6] = new Channel();
		_channels[7] = new Channel();
		clock=Clock.systemUTC();
	}
	
	public Clock getClock(){
		return clock;
	}
	
	public void turnOn(){
		System.out.println("ON");
		_isOn=true;
	}
	
	public void turnOff(){
		System.out.println("OFF");
		_isOn=false;
	}
	
	public void time(LocalDateTime set){
		Clock systemClock = Clock.systemDefaultZone();
		clock=Clock.offset(systemClock, Duration.between(LocalDateTime.now(), set));
		//LocalDateTime check = LocalDateTime.now(clock);
		//System.out.println(check.toString());
	}
	
	public void event(String eventType){
		if(eventType.equalsIgnoreCase("IND")){
			_stream = new IndividualStream(_currentRun);
			_streams.set(_currentRun-1, _stream);
		}
		else if(eventType.equalsIgnoreCase("GRP")){
			_stream = new GroupStream(_currentRun);
			_streams.set(_currentRun-1, _stream);
		}
		else if(eventType.equalsIgnoreCase("PARIND")){
			_stream = new IndividualParallelStream(_currentRun);
			_streams.set(_currentRun-1, _stream);
		}
		else if(eventType.equalsIgnoreCase("PARGRP")){
			_stream= new GroupParallelStream(_currentRun);
			_streams.set(_currentRun-1, _stream);
		}
	}

	public void connect(String sensorType, int channel){
		if(_isOn)
		{
		_channels[channel-1].connect(sensorType);
		}
	}
	
	public void disconnect(int channel){
		if(_isOn){
			_channels[channel-1] = null;
		}
	}
	
	public void toggle(int channel){
		if(_isOn)
		{
		_channels[channel-1].toggle();
		}
	}
	
	public void start(){
		if(_isOn)
		{
			_stream.startRecord(_channels[0].trigger(clock));
		}
	}
	
	public void end(){
		if(_isOn)
		{
			_stream.finishRecord(_channels[1].trigger(clock));	
		}
		
	}
	
	public void trigger(int channel)
	{
		if(_isOn && !(_stream instanceof GroupParallelStream)){
			if(channel % 2 == 1){
			_stream.startRecord(_channels[channel-1].trigger(clock));
			}
			else if(channel % 2 == 0){
			_stream.finishRecord(_channels[channel-1].trigger(clock));
			}
		}
		else if(_isOn && _stream instanceof GroupParallelStream){
			if(channel % 2 == 1){
				_stream.startRecord(_channels[channel-1].trigger(clock));
			}
				else if(channel % 2 == 0){
				_stream.finishRecord(_channels[channel-1].trigger(clock), channel);
			}
		}
	}
	
	public void num(int BIBNumber)
	{
		if(_isOn)
		{
			_stream.num(BIBNumber);
		}
	}
	
	
	public void print(){
		if(_isOn)
		{
			if(printer == null)
				System.out.println(_stream.toString());
			else{
				printer.setText("");
				printer.setText(_stream.toString());
			}
		}
	}

	public void DNF() {
		if(_isOn)
		{
			_stream.DNFRecord();
		}
	}

	public void cancel() {
		if(_isOn)
		{
			_stream.cancelRecord();
		}
	}
	
	public void newRun(){
		if(_isOn)
		{
			_stream = new IndividualStream(_currentRun);
			_streams.add(_stream);
		}
	}
	
	public void endRun(){
		if(_isOn){
			_stream = null;
			_currentRun++;
		}
	}
	
	public void export(int runNumber) throws FileNotFoundException{
		Integer runNumberUp = new Integer(runNumber);
		File file = new File("C:/Users/Noah/Documents/ChronoTimerOut/RunNumber" + runNumberUp.toString() + ".txt");
		file.getParentFile().mkdirs();
		PrintWriter writer = new PrintWriter(file);
		writer.println(_streams.get(runNumber-1).toString());
		writer.close();
		try{
			String jsonList = _streams.get(runNumber-1).toJSON(clock);
			URL site = new URL("http://1-dot-chronotimerserverth.appspot.com/chronotimerserver");
			
			HttpURLConnection conn = (HttpURLConnection) site.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes("data=" + jsonList);
			out.close();
			new InputStreamReader(conn.getInputStream()); 
			}
			catch (Exception e){
				e.printStackTrace();
			}
	}
	
	public String updateDisplay(){
		return _stream.displayRecords(clock);
	}

	

	
}