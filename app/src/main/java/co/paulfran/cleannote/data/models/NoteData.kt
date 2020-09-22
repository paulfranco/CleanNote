package co.paulfran.cleannote.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import co.paulfran.cleannote.data.models.Importance
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "note_table")
@Parcelize
data class NoteData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var importance: Importance,
    var description: String
): Parcelable