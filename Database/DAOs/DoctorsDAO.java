/* Doctors entity DAO in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

@Dao
public interface DoctorsDAO {
    @Insert
    public void insert(Doctors... doctors);
 
    @Update
    public void update(Doctors... doctors);
 
    @Delete
    public void delete(Doctors doctor);

	@Query("SELECT * FROM DOCTORS")
	public List<Doctors> getAllDoctors();
}