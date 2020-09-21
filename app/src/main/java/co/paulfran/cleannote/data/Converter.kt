package co.paulfran.cleannote.data

import androidx.room.TypeConverter
import co.paulfran.cleannote.data.models.Importance

class Converter {

    @TypeConverter
    fun fromImportance(importance: Importance): String {
        return importance.name
    }

    @TypeConverter
    fun toImportance(importance: String): Importance {
        return Importance.valueOf(importance)
    }

}