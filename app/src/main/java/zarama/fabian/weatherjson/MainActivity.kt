
package zarama.fabian.weatherjson

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextSwitcher
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private lateinit var txtEntry: String


    // Pass in a string, pass out String
      inner class DownloadTask : AsyncTask<String, Void, String>() {


        override fun doInBackground(vararg urls: String?): String { // is like an array
            var result: String = ""

            try {

                result = URL(urls.get(0)).readText()

            }catch (e: Exception){
                Log.i("error DownloadTask()", e.localizedMessage)
            }
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            // Create a Json object
            try {


                Log.i("String123", result)

                var jsonObject = JSONObject(result)

                var weatherInfo  = jsonObject.get("weather")

                var arr  = JSONArray(weatherInfo.toString())


                var allAnswer = ""
                for (i in 0..arr.length()-1){
                   var jsonPart = arr.getJSONObject(i)

                    allAnswer += jsonPart.getString("main") + "\n"


                }

                txtWeather.text = allAnswer





            }catch (e:Exception){
                Log.i("error creating JSON", e.localizedMessage)
            }finally {
                if (result!!.contains("Warning")){
                    txtWeather.text = "Dont know that city =C"
                }
            }


        }

    }

    fun getData(view: View){
        txtEntry = editText.text.toString()



        var task = DownloadTask()

        try {
            task.execute("https://openweathermap.org/data/2.5/weather?q=$txtEntry&appid=b6907d289e10d714a6e88b30761fae22").get()

            Log.i("asdf","asdf")
        }catch (e: Exception){
            Log.i("error getData", e.printStackTrace().toString())
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





    }

}
