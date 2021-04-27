package ipvc.estg.projetofinal.database

import androidx.lifecycle.LiveData
import ipvc.estg.projetofinal.dao.NoteDao
import ipvc.estg.projetofinal.entities.Notes
import java.util.concurrent.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Notes>> = noteDao.getAlphabetizedNotes()

    suspend fun insert(note: Notes){
        noteDao.insert(note)

    }

    suspend fun deleteAll(){
        noteDao.deleteAll()
    }

    suspend fun update(id: Int?, titulo: String, descricao: String){
        noteDao.update(id, titulo, descricao)
    }

    suspend fun delete(id: Int?){
        noteDao.delete(id)
    }

}