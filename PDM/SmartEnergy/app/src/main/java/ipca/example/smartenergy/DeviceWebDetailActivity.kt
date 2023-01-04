package ipca.example.smartenergy

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import org.json.JSONObject

class DeviceWebDetailActivity : AppCompatActivity() {
    var device                      : Device? = null
    var devicesLog                  = arrayListOf<DeviceLog>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_web_detail)
        device = Device.fromJSON(JSONObject(intent.getStringExtra(MainActivity.EXTRA_ARTICLE).toString()))
        //println("#### DeviceWebDetailActivity | device: "+device?.iddevices)
        title = "Poste de iluminação com n.º "+ device?.iddevices
        /*
        Backend.fetchDeviceLog(lifecycleScope, "select","devicestatus",device?.iddevices.toString()){
            println("#### DeviceWebDetailActivity | Backend.fetchDeviceLog ")
            devicesLog = it
            adapterLog.notifyDataSetChanged()
        }*/

        // dados relativos ao Device
        findViewById<TextView>(R.id.textViewDeviceID).text          =  device?.iddevices
        findViewById<TextView>(R.id.textViewDeviceMacAddress).text  =  device?.macaddress
        findViewById<TextView>(R.id.textViewDeviceDetail).text      =  device?.detail
        findViewById<TextView>(R.id.textViewDeviceCoord).text       =  device?.coordinatex+","+device?.coordinatey
        findViewById<TextView>(R.id.textViewDeviceLogsStatus).text  =  device?.status
        if (device?.status=="offline") {
            // cor do texto vermelho para o estado offline
            findViewById<TextView>(R.id.textViewDeviceLogsStatus).setTextColor(Color.parseColor("#E91E63"));
        } else {
            // cor do texto verde para o estado online
            findViewById<TextView>(R.id.textViewDeviceLogsStatus).setTextColor(Color.parseColor("#228B22"));
        }

        // dados relativos ao Logs do Device
        findViewById<TextView>(R.id.textViewDeviceLogsID).text          = device?.idlogs
        findViewById<TextView>(R.id.textViewDeviceLogsDateTime).text    = device?.datetime
        findViewById<TextView>(R.id.textViewDeviceLogsIpAddress).text   = device?.ipaddress
        findViewById<TextView>(R.id.textViewDeviceLogsValLed).text      = device?.valled
        if (device?.stateled == "0")
            findViewById<TextView>(R.id.textViewDeviceLogsStateLed).text    = "off"
        else
            findViewById<TextView>(R.id.textViewDeviceLogsStateLed).text    = "on"
        findViewById<TextView>(R.id.textViewDeviceLogsValLdr).text      = device?.valldr
        findViewById<TextView>(R.id.textViewDeviceLogsValLdrNew).text   = device?.valldrnew+" %"
        if (device?.valpir == "0")
            findViewById<TextView>(R.id.textViewDeviceLogsValPir).text      = "no"
        else
            findViewById<TextView>(R.id.textViewDeviceLogsValPir).text      = "yes"
        if (device?.statepir == "0")
            findViewById<TextView>(R.id.textViewDeviceLogsStatePir).text    = "disable"
        else
            findViewById<TextView>(R.id.textViewDeviceLogsStatePir).text    = "enable"
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_device,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, device?.coordinatey)
                intent.type = "text/plain"
                val intentChooser = Intent.createChooser(intent, device?.iddevices)
                startActivity(intentChooser)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
