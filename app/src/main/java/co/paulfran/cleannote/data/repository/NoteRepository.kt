package co.paulfran.cleannote.data.repository

import androidx.lifecycle.LiveData
import co.paulfran.cleannote.data.NoteDao
import co.paulfran.cleannote.data.models.NoteData

class NoteRepository(private val noteDao: NoteDao) {

    val getAllData: LiveData<List<NoteData>> = noteDao.getAllData()
    val sortByHighImportance: LiveData<List<NoteData>> = noteDao.sortByHighImportance()
    val sortByLowImportance: LiveData<List<NoteData>> = noteDao.sortByLowImportance()

    suspend fun insertData(noteData: NoteData) {
        noteDao.insertData(noteData)
    }

    suspend fun updateData(noteData: NoteData) {
        noteDao.updateData(noteData)
    }

    suspend fun deleteItem(noteData: NoteData) {
        noteDao.deleteItem(noteData)
    }

    suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<NoteData>> {
        return noteDao.searchDatabase(searchQuery)
    }
}