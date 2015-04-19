import java.time.Clock;
import java.time.LocalDateTime;


public interface ISensor 
{
		public LocalDateTime trigger(Clock clock);
}
