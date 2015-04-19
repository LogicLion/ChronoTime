import java.time.Clock;
import java.time.LocalDateTime;


public class Gate implements ISensor {

	@Override
	public LocalDateTime trigger(Clock clock) {
		return LocalDateTime.now(clock);
	}

}