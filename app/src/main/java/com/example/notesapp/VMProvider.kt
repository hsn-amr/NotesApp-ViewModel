package com.example.notesapp

import android.app.Application
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class VMProvider(application: Application) : AndroidViewModel(application) {

    private val noteDatabase by lazy { NoteDatabase.getInstance(application) }
    var allNotes = ArrayList<Note>()
    var size: MutableLiveData<Int> = MutableLiveData()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            allNotes = noteDatabase.getNoteDao().getAllNotes() as ArrayList<Note>
            size.postValue(allNotes.size)
        }
    }

    fun getSize(): LiveData<Int>{
        return size
    }


    fun getAllNewNotes(): ArrayList<Note>{
        CoroutineScope(Dispatchers.IO).launch {
            allNotes = noteDatabase.getNoteDao().getAllNotes() as ArrayList<Note>
            size.postValue(allNotes.size)
            delay(2000)

        }
        return allNotes
    }

    fun addNote(et: EditText){
        val noteText = et.text.toString()
        if(noteText.isNotEmpty()){
            CoroutineScope(Dispatchers.IO).launch {
                noteDatabase.getNoteDao().addNote(Note(0,noteText))
                allNotes = getAllNewNotes()
                size.postValue(allNotes.size)
            }
        }else{
            et.hint = "Please, Type a note"
        }
    }
}