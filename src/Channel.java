import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class Channel {
	
	private ISensor s;
	private boolean hasSensor;
	private boolean isArmed;
	
	public Channel()
	{
		isArmed =false;
		hasSensor = false;
		s = null;
	}
	
	public void connect(String sensorType)
	{
		if(sensorType.equalsIgnoreCase("gate"))
		{
			s = new Gate();
			hasSensor=true;
		}
		else if(sensorType.equalsIgnoreCase("eye")){
			s = new Eye();
			hasSensor=true;
		}
		else if(sensorType.equalsIgnoreCase("pad")){
			s = new Pad();
			hasSensor=true;
		}
		else
		{
			throw new UnsupportedOperationException("Sensor Type Not Recognized");
		}
	}
	
	public void disconnect(String sensorType)
	{
		if(hasSensor)
		{
			s=null;
			hasSensor=false;
		}
		else throw new UnsupportedOperationException("Cannot disconnect when nothing is connected");
	}
	
	public void toggle()
	{
		if(isArmed && hasSensor) isArmed=false;
		else if(!isArmed && hasSensor) isArmed=true;
		else throw new UnsupportedOperationException("Must connect a sensor before arming");
		System.out.println(isArmed);

	}
	
	public LocalDateTime trigger(Clock clock)
	{
		if(isArmed && hasSensor)
		{
			return s.trigger(clock);
		}
		else
		{
			throw new UnsupportedOperationException("Channel is not armed");
		}
	}
	

	
}