package com.uoa.ddcapp

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.raxeltelematics.v2.sdk.TrackingApi
import com.telematicssdk.auth.TelematicsAuth
import com.uoa.ddcapp.R
import com.uoa.ddcapp.databinding.FragmentHomeBinding
import com.uoa.ddcapp.StoredToken
import com.uoa.ddcapp.db.LocalUserDataInterfaceDAO
import com.uoa.ddcapp.db.PhDSafeDriveDB
import com.uoa.ddcapp.model.LocalUserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import com.uoa.ddcapp.PermissionsFragment


class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sf: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var token: String
    private lateinit var btnText:String
    private lateinit var tracking: TrackingApi
    private val INSTANCE_ID="abe10ef5-0cfb-4e3a-9702-859a96ec5abc"
    private val INSTANCE_KEY="839f0179-79b8-48c8-9a75-e55ba7b64aaf"


   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sf = activity?.getSharedPreferences(getString(R.string.deviceToken), AppCompatActivity.MODE_PRIVATE)!!
        tracking=TrackingApi.getInstance()

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//         Retrieve device token from shared Preference of the phone memory
        sf = activity?.getSharedPreferences(
            getString(R.string.deviceToken),
            AppCompatActivity.MODE_PRIVATE
        )!!
        val deviceToken = StoredToken.getStoredDeviceToken(sf, getString(R.string.deviceToken))
        token=deviceToken


        if (deviceToken!=null){
            binding.btncreateToken.visibility = GONE
        }
        else{
            binding.btncreateToken.visibility = VISIBLE
        }

        binding.btncreateToken.setOnClickListener {
            it.findNavController().navigate(R.id.action_home2_to_registerDevice)
        }

        binding.btnStartTrip.setOnClickListener {

//            initialize the Tracking API for this fragment context
            tracking.initialize(this.requireContext())

            btnText = binding.btnStartTrip.text.toString()

            if (btnText == "Start Trip") {
                binding.btnStartTrip.text = "End Trip"
                btnText = "End Trip"

//            ENable if Telematic SDK is if not enabled in the case of launching the app again after
//            the end of a trip.
//
                if (!tracking.isSdkEnabled()) {
                    Toast.makeText(activity, "SDK Not enabled!", Toast.LENGTH_LONG).show()
//                first check is App has a device token already registered before enabling SDK
                    if (deviceToken != null) {
//                     Enable SDK and start tracking
                        tracking.setEnableSdk(true).also {
                            if (tracking.startTracking())
                                Toast.makeText(
                                    activity,
                                    "Enabling SDK....\n SDK Enabled! \n \n Trip Started....\n Safe Trip.",
                                    Toast.LENGTH_LONG
                                ).show()
                        }

                    } else {
//                    User should go back to home fragment to register a device token
                        Toast.makeText(
                            activity,
                            "Please Click on the Register Button \n to generate device token",
                            Toast.LENGTH_LONG
                        ).show()
                        it.findNavController().navigate(R.id.action_home2_self)
                    }

                } else {

//             If deviceToken is not null and SDK is enabled call start tracking
                    tracking.startTracking()
                    Toast.makeText(
                        activity,
                        "Tracking Started....\n\n Safe Trip!!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            else {
//                    binding.btnStartTrip.text="Start Trip"
                binding.btnStartTrip.text = "Start Trip"
                btnText="Start Trip"

           tracking.setEnableSdk(false).also {
                        Toast.makeText(
                            activity,
                            "Disabling SDK.. \n SDK Disabled \n Stopping Trip....\n" +
                                    "\n Thank you for participating in this Project.\n" +
                                    "Please don't forget to take the Post Trip Survey.",
                            Toast.LENGTH_LONG
                        ).show()
                        tracking.stopTracking()
                }
//
//            Toast.makeText(activity,btnText,Toast.LENGTH_LONG).show()

            }
        }
        return binding.root


    }

    override fun onResume() {
        super.onResume()
        if (token!=null){
            binding.btncreateToken.visibility = GONE
        }
        else{
            binding.btncreateToken.visibility = VISIBLE
        }

    }

    //    login function
    fun login(deviceToken:String){


    }

    fun refreshToken(accessToken: String, refreshToken:String){
        TelematicsAuth.refreshToken(
            INSTANCE_ID,
            INSTANCE_ID,
            accessToken,
            refreshToken
        ).onSuccess{
            it.refreshToken
        }
            .onError{

            }
    }


}