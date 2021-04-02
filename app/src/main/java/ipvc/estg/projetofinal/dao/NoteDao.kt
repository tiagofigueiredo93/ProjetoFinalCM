package ipvc.estg.projetofinal.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ipvc.estg.projetofinal.entities.Notes

@Dao
interface NoteDao {
    @get:Query("SELECT * FROM notes ORDER BY id DESC")
    val allNotes: LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(note:Notes)

    @Delete
    fun deleteNote(note: Notes)

}