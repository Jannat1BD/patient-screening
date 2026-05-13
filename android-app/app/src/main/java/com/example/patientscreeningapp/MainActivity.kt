package com.example.patientscreeningapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

data class Patient(
    val patientId: Int,
    val studyDate: String,
    val country: String?,
    val cadScore: Double?
)

interface PatientApi {
    @GET("api/patients")
    fun getPatients(): Call<List<Patient>>
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                PatientListScreen()
            }
        }
    }
}

@Composable
fun PatientListScreen() {
    var patients by remember { mutableStateOf<List<Patient>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        // Create OkHttpClient with 60 second timeouts
        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val api = retrofit.create(PatientApi::class.java)

        api.getPatients().enqueue(object : Callback<List<Patient>> {
            override fun onResponse(call: Call<List<Patient>>, response: Response<List<Patient>>) {
                isLoading = false
                if (response.isSuccessful) {
                    patients = response.body() ?: emptyList()
                    println("Loaded ${patients.size} patients")
                } else {
                    error = "HTTP Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<Patient>>, t: Throwable) {
                isLoading = false
                error = "Connection failed: ${t.message}"
                t.printStackTrace()
            }
        })
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Loading ${if (patients.isNotEmpty()) patients.size else ""} patients...")
                }
            }
        }
        error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error: $error")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Make sure backend is running on port 8082")
                }
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(patients) { patient ->
                    PatientCard(patient)
                }
            }
        }
    }
}

@Composable
fun PatientCard(patient: Patient) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Patient ID: ${patient.patientId}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Study Date: ${patient.studyDate}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Country: ${patient.country ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "CAD Score: ${patient.cadScore ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}