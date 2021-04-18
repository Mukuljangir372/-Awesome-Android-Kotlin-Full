package com.mu.jan.problems.architecture

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.*

/**
 * Room is a modern way to work with SQLite
 *
 * Part of Room
 * 1. Entity
 * 2. Dao
 * 3. Database
 */
@Entity(tableName = "profile")
data class Profile(
    @ColumnInfo(name = "name")
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    val uid: Int? = null
}
@Dao
interface MyDao{
    @Insert
    fun insert(profile: Profile)
    @Update
    fun update(profile: Profile)
    @Delete
    fun delete(profile: Profile)
    @Query("SELECT * FROM profile")
    fun getProfiles(): List<Profile>
    @Query("SELECT * FROM profile WHERE uid LIKE :uid")
    fun getProfile(uid: Int): Profile
}
@Database(entities = [Profile::class],exportSchema = false,version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun profileDao() : MyDao
    companion object{
        const val DB_NAME = "myRoomDb1"

        @Volatile
        private var instance: AppDatabase? = null

        fun getDb(mContext: Context): AppDatabase{
            if(instance!=null) return instance!!
            synchronized(this){
                instance = Room.databaseBuilder(
                    mContext,AppDatabase::class.java, DB_NAME
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}
class MyRoomActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //..
        val db = AppDatabase.getDb(this)

    }
}
