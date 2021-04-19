package ipvc.estg.projetofinal.database

import androidx.lifecycle.LiveData
import ipvc.estg.projetofinal.dao.NoteDao
import ipvc.estg.projetofinal.entities.Notes

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Notes>> = noteDao.getAlphabetizedNotes()

    suspend fun insert(note: Notes){
        noteDao.insert(note)
    }
}