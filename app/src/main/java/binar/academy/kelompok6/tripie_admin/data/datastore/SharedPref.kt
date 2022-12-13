package binar.academy.kelompok6.tripie_admin.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "user")
class SharedPref(private val context: Context) {

    private val token = stringPreferencesKey("token")

    suspend fun saveToken(tokens : String){
        context.dataStore.edit {
            it[token] = tokens
        }
    }

    val getToken : Flow<String> = context.dataStore.data
        .map {
            it[token] ?: "Undefined"
        }

    suspend fun removeToken(){
        context.dataStore.edit {
            it.clear()
        }
    }
}