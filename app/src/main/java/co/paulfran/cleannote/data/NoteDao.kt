package co.paulfran.cleannote.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.paulfran.cleannote.data.models.NoteData

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<NoteData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(noteData: NoteData)

}