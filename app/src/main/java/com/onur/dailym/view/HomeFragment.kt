@file:Suppress("UNREACHABLE_CODE")

package com.onur.dailym.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.*
import com.onur.dailym.R
import com.onur.dailym.Utils.GpsUtils
import com.onur.dailym.model.WeatherModel
import com.onur.dailym.servies.LocationLiveData
import com.onur.dailym.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.random.Random
import kotlin.io.println as println

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var isGPSEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


       
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)




    }

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)






       activity?.let {
            GpsUtils(it).turnGPSOn(object : GpsUtils.OnGpsListener {

                override fun gpsStatus(isGPSEnable: Boolean) {
                    this@HomeFragment.isGPSEnabled = isGPSEnable
                }
            })
        }



        activity?.let {
            homeViewModel.getLocationData().observe(it, Observer {
                homeViewModel.getDataFromAPI(it.latitude.toString(),it.longitude.toString())

            })
        }

        homeViewModel.refreshQuotes()
        homeViewModel.getMoneyFromAPI()
        writeText()








    }


    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPSEnabled = true
                invokeLocationAction()
            }
        }
    }

    private fun invokeLocationAction() {
        when {
            !isGPSEnabled -> textView.text = getString(R.string.enable_gps)

            isPermissionsGranted() -> startLocationUpdate()

            shouldShowRequestPermissionRationale() -> textView.text = getString(R.string.permission_request)

            else -> activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    LOCATION_REQUEST
                )
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun startLocationUpdate() {
        homeViewModel.getLocationData().observe(this, Observer {

        })
    }

    private fun isPermissionsGranted() =
        activity?.let {
            ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        activity?.let {
            ActivityCompat.shouldShowRequestPermissionRationale(
                it,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } == true && ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun writeText(){
        homeViewModel.weather.observe(viewLifecycleOwner, Observer { weather->
            weather?.let {

                val temp: String = weather.main?.temp?.minus(273.15).toString().substring(0,4)
                val country : String = weather.sys?.country.toString()
                val city: String = weather.cityname
                val feels: String = weather.main?.feels_like?.minus(273.15).toString().substring(0,4)
                val humidity: String = weather.main?.humidity.toString()


                textView.setText("Merhaba, Bugün Hava "+ country + " " + city +"'de" + " " + temp +" °C, " +" Hissedilen Sıcaklık : " +feels+" °C"+" ve Nem %"+humidity )

            }


        })
        homeViewModel.quotes.observe(viewLifecycleOwner,{ quotes->
            quotes?.let {

                textView2.setText(quotes.get(0).quote + "\n" + quotes.get(0).author)

                }

        })
    }
}

const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101