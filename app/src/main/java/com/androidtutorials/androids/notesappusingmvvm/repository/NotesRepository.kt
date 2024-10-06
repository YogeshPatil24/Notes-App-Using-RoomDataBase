package com.androidtutorials.androids.notesappusingmvvm.repository

import androidx.lifecycle.LiveData
import com.androidtutorials.androids.notesappusingmvvm.room.dao.NotesDAO
import com.androidtutorials.androids.notesappusingmvvm.room.entity.Notes

class NotesRepository(private var notesDao: NotesDAO) {
    fun getAllNotes(): LiveData<List<Notes>> = notesDao.getAllNotes()

    fun getHighNotes(): LiveData<List<Notes>> = notesDao.getHighNotes()
    fun getMediumNotes(): LiveData<List<Notes>> = notesDao.getMediumNotes()
    fun getLowNotes(): LiveData<List<Notes>> = notesDao.getLowNotes()

    fun insertNotes(notes: Notes) = notesDao.insertNotes(notes = notes)


    fun deleteNotes(id: Int) = notesDao.deleteNotes(id = id)


    fun updateNotes(notes: Notes) = notesDao.updateNotes(notes = notes)

}