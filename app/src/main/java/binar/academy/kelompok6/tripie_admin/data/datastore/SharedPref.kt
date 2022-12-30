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
    private val email = stringPreferencesKey("email")
    private val name = stringPreferencesKey("name")
    private val planeClass = stringPreferencesKey("pclass")

    suspend fun saveData(tokens : String, emails : String, names : String){
        context.dataStore.edit {
            it[token] = tokens
            it[email] = emails
            it[name] = names
        }
    }

    suspend fun savePlane(pclass : String){
        context.dataStore.edit {
            it[planeClass] = pclass
        }
    }

    val getToken : Flow<String> = context.dataStore.data
        .map {
            it[token] ?: ""
        }

    val getEmail : Flow<String> = context.dataStore.data
        .map {
            it[email] ?: "Undefined"
        }

    val getName : Flow<String> = context.dataStore.data
        .map {
            it[name] ?: "Undefined"
        }

    val getPlane : Flow<String> = context.dataStore.data
        .map {
            it[planeClass] ?: "Undefined"
        }

    suspend fun removeToken(){
        context.dataStore.edit {
            it.clear()
        }
    }
}