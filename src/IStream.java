import java.time.LocalDateTime;


public interface IStream {

	public void num(int _runNumber);
	public void cancelRecord();
	public void startRecord(LocalDateTime start);
	public void finishRecord(LocalDateTime finish);
	public void finishRecord(LocalDateTime finish, int channel);
	public void DNFRecord();
	public String toString();
	public String displayRecords();
}