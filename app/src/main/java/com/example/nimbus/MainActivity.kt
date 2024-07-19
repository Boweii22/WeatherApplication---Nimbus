package com.example.nimbus

import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val API: String = "06c921750b9a82d8f5d1294e1586276f" // Use API key
    private lateinit var videoView: VideoView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

//        videoView = findViewById(R.id.videoView)
        searchView = findViewById(R.id.searchCity)

        // Set video based on time of day
//        setVideoBasedOnTime()

        // Initialize with a default city
        fetchWeatherData("London")

        // Set up SearchView listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optionally handle text changes here
                return false
            }
        })
    }

    private fun setVideoBasedOnTime() {
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val videoResId = if (currentHour in 6..17) {
            // Morning to evening (6 AM to 6 PM)
            R.raw.day_bg
        } else {
            // Evening to morning (6 PM to 6 AM)
            R.raw.night_bg
        }

        val uri = Uri.parse("android.resource://" + packageName + "/" + videoResId)
        videoView.setVideoURI(uri)

        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            videoView.start()
        }

        // Log the time of day
        if (currentHour in 6..17) {
            Log.d("TimeOfDay", "It is morning.")
        } else {
            Log.d("TimeOfDay", "It is night.")
        }
    }

    private fun fetchWeatherData(city: String) {
        weatherTask(city).execute()
    }

    inner class weatherTask(private val city: String) : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response: String?
            try {
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=$API").readText(Charsets.UTF_8)
            } catch (e: Exception) {
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt: Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt * 1000))
                val temp = main.getString("temp") + "°C"
                val tempMin = "Min Temp: " + main.getString("temp_min") + "°C"
                val tempMax = "Max Temp: " + main.getString("temp_max") + "°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")

                val sunrise: Long = sys.getLong("sunrise")
                val sunset: Long = sys.getLong("sunset")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name") + ", " + sys.getString("country")

                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updated_at).text = updatedAtText
                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.temp_min).text = tempMin
                findViewById<TextView>(R.id.temp_max).text = tempMax
                findViewById<TextView>(R.id.sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))
                findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.humidity).text = humidity

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }
        }
    }
}
