/* Schedule entity DAO in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

@Dao
public interface ScheduleDAO {
    @Insert
    public void insert(Schedule... schedules);
 
    @Update
    public void update(Schedule... schedules);
 
    @Delete
    public void delete(Schedule schedule);
	
	@Query("SELECT * FROM SCHEDULE")
	public List<Schedule> getAllSchedules();
}