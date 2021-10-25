package com.example.notesapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class NotesDBHelper(context: Context): SQLiteOpenHelper(context,"Notes.db", null, 1) {

    private var sqliteDatabase: SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Note (text text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun saveNote(note: Note): Long {
        val cv = ContentValues()
        cv.put("text", note.text)
        return sqliteDatabase.insert("Note", null, cv)
    }

    @SuppressLint("Range")
    fun retrieveAllNotes(): ArrayList<Note> {
        var notes= ArrayList<Note>()
        val cursor: Cursor = sqliteDatabase.query("Note", null, null, null, null, null, null)
        cursor.moveToFirst()

        do {
            notes.add(Note(cursor.getString(cursor.getColumnIndex("text"))))
        }while (cursor.moveToNext())
        return notes
    }
}