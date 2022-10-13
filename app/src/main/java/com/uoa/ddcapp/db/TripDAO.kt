package com.uoa.ddcapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.uoa.ddcapp.NumberOfBottles
import com.uoa.ddcapp.model.Trip

//import com.uoa.phdapp.model.Trip

@Dao
interface TripDAO {
    @Insert
    suspend fun insertTrip(trip: Trip)

    @Update
    suspend fun updateTrip(trip: Trip)

    @Delete
    suspend fun deleteTrip(trip: Trip)

    @Query("Select * FROM trip")
    fun getAllTrip(): LiveData<List<Trip>>

    /**
     * Updating only amount and price
     * By order id
     */
    @Query("UPDATE trip SET drunk_state = :drunk_state,  noOfDrinks= :noOfBottles, videoURI=:videoURI WHERE tripId =:tripId")
    fun updateTripData(drunk_state: String?, noOfBottles: Int?, videoURI: String?,tripId:String)

    @Query("UPDATE trip SET videoURI = :videoURI WHERE tripId =:tripId")
    fun updateVideoURL(videoURI: String?, tripId: String)
}