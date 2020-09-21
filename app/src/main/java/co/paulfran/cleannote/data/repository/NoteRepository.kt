package co.paulfran.cleannote.data.repository

import androidx.lifecycle.LiveData
import co.paulfran.cleannote.data.NoteDao
import co.paulfran.cleannote.data.models.NoteData

class NoteRepository(private val noteDao: NoteDao) {

    val getAllData: LiveData<List<NoteData>> = noteDao.getAllData()

    suspend fun insertData(noteData: NoteData) {
        noteDao.insertData(noteData)
    }
}