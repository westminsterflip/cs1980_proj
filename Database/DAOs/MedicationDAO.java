/* Medication entity DAO in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

@Dao
public interface MedicationDAO {
    @Insert
    public void insert(Medication... medications);
 
    @Update
    public void update(Medication... medications);
 
    @Delete
    public void delete(Medication medication);
	
	@Query("SELECT * FROM MEDICATION")
	public List<Medication> getAllMedications();
}