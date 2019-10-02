/* MedicationLog entity DAO in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

@Dao
public interface MedicationLogDAO {
    @Insert
    public void insert(MedicationLog... medicationLogs);
 
    @Update
    public void update(MedicationLog... medicationLogs);
 
    @Delete
    public void delete(MedicationLog medicationLog);

	@Query("SELECT * FROM MEDICATIONLOG")
	public List<MedicationLog> getAllMedicationLogs();
}