package co.paulfran.cleannote.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.paulfran.cleannote.data.models.Importance

@Entity(tableName = "note_table")
data class NoteData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var importance: Importance,
    var description: String
)