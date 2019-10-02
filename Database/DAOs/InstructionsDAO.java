/* Instructions entity DAO in MedicationDatabase
   
   Implemented using Android Room
   
   CS1980 Fall 2019
   @authors Erin Herlihy, David Stropkey, Nicholas West, Ian Patterson
*/

@Dao
public interface InstructionsDAO {
    @Insert
    public void insert(Instructions... instructions);
 
    @Update
    public void update(Instructions... instructions);
 
    @Delete
    public void delete(Instruction instruction);
	
	@Query("SELECT * FROM INSTRUCTIONS")
	public List<Instructions> getAllInstructions();
}