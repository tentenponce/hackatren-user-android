package com.nasaanka.data.service

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.Completable
import io.reactivex.Observable


/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/24/2018.
 */
class FirebaseService {

    companion object {
        const val LOCATION_TABLE = "LOCATION_TABLE"
        const val TRAIN_LOCATION_TABLE = "TRAIN_LOCATION_TABLE"
    }

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    fun <T> write(table: String, key: String, obj: T): Completable = Completable.create({ observer ->
        databaseReference
                .child(table)
                .child(key)
                .setValue(obj)
                .addOnSuccessListener { observer.onComplete() }
                .addOnFailureListener { observer.onError(it) }
    })

    fun readById(table: String, key: String): Observable<DataSnapshot> = Observable.create {
        databaseReference
                .child(table)
                .child(key)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        it.onNext(dataSnapshot)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        it.onError(error.toException())
                    }
                })
    }

    fun readByTable(table: String): Observable<DataSnapshot> = Observable.create {
        databaseReference
                .child(table)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        it.onNext(dataSnapshot)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        it.onError(error.toException())
                    }
                })
    }
}
