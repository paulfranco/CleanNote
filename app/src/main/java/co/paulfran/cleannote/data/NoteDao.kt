package co.paulfran.cleannote.data

import androidx.lifecycle.LiveData
import androidx.room.*
import co.paulfran.cleannote.data.models.NoteData

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<NoteData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(noteData: NoteData)

    @Update
    suspend fun updateData(noteData: NoteData)

    @Delete
    suspend fun deleteItem(noteData: NoteData)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<NoteData>>

    @Query("SELECT * FROM note_table ORDER BY CASE WHEN importance LIKE 'H%' THEN 1 WHEN importance LIKE 'M%' THEN 2 WHEN importance LIKE 'L%' THEN 3 END")
    fun sortByHighImportance(): LiveData<List<NoteData>>

    @Query("SELECT * FROM note_table ORDER BY CASE WHEN importance LIKE 'L%' THEN 1 WHEN importance LIKE 'M%' THEN 2 WHEN importance LIKE 'H%' THEN 3 END")
    fun sortByLowImportance(): LiveData<List<NoteData>>


}