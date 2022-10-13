package com.uoa.ddcapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.raxeltelematics.v2.sdk.Settings
import com.raxeltelematics.v2.sdk.TrackingApi
import com.raxeltelematics.v2.sdk.utils.permissions.PermissionsWizardActivity
import com.raxeltelematics.v2.sdk.utils.permissions.PermissionsWizardActivity.Companion.WIZARD_PERMISSIONS_CODE
import com.raxeltelematics.v2.sdk.utils.permissions.PermissionsWizardActivity.Companion.WIZARD_RESULT_ALL_GRANTED
import com.raxeltelematics.v2.sdk.utils.permissions.PermissionsWizardActivity.Companion.WIZARD_RESULT_CANCELED
import com.raxeltelematics.v2.sdk.utils.permissions.PermissionsWizardActivity.Companion.WIZARD_RESULT_NOT_ALL_GRANTED
import com.uoa.ddcapp.databinding.FragmentPermissionsBinding

//import com.uoa.phdapp.databinding.FragmentPermissionsBinding


class PermissionsFragment : Fragment() {
    /**
     * Default Setting constructor
     * Stop tracking time is 5 minute.
     * Parking radius is 100 meters.
     * Auto start tracking is true.
     * hfOn - true if HIGH FREQUENCY data recording from sensors (acc, gyro) is ON and false otherwise.
     * isElmOn - true if data recording from ELM327 devices is ON and false otherwise.
     */
    private lateinit var tracking: TrackingApi
    val settings = Settings(Settings.stopTrackingTimeHigh, Settings.accuracyHigh, true, true, false)
    private lateinit var binding: FragmentPermissionsBinding


    private lateinit var bun:Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val deviceToken=requireArguments().getString("deviceToken")
        binding=FragmentPermissionsBinding.inflate(inflater,container,false)
        bun= bundleOf("deviceToken" to deviceToken)
        Log.i("DeviceToken-Permissions",deviceToken.toString())

        if (container != null) {
            initTracking(deviceToken.toString())
            findNavController().navigate(R.id.action_permissionsFragment_to_home2,bun)
        }
        initTracking(deviceToken.toString())
        return binding.root

    }

    fun initTracking(deviceToken: String){
        tracking= TrackingApi.getInstance()
        activity?.let { tracking.initialize(it, settings) }
        val activityResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result->
            if (result.resultCode== WIZARD_RESULT_ALL_GRANTED){

                enableTracking(deviceToken)
            }
            else{
                Toast.makeText(activity, "Unable to Activate SDK", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        if(!tracking.isAllRequiredPermissionsAndSensorsGranted()){
            val intent= activity?.let {
                PermissionsWizardActivity.getStartWizardIntent(
                    it,
                    enableAggressivePermissionsWizard = true,
                    enableAggressivePermissionsWizardPage = true,
                )
            }
            activityResultLauncher.launch(intent)
        }
        else {
            val intent=activity?.let{
                Toast.makeText(it, "All permissions are already granted", Toast.LENGTH_SHORT)
                    .show()
                enableTracking(deviceToken)
            }

        }

    }

    private fun enableTracking(deviceToken:String){

        if (tracking.isAllRequiredPermissionsAndSensorsGranted()) {

            tracking.setDeviceID(deviceToken) // User DeviceToken
//            binding.tvDeviceToken.text=deviceToken.toString()
            Log.i("CreatedToken",deviceToken)
            if (activity?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            tracking.setEnableSdk(true) //false to disable SDK

        }

    }
    fun disableSDK(deviceToken: String){
        tracking.setDeviceID(deviceToken) // User DeviceToken
//            binding.tvDeviceToken.text=deviceToken.toString()
        Log.i("CreatedToken",deviceToken)
        if (activity?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        tracking.setEnableSdk(false) //false to disable SDK
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // don’t forget to call here and in all base activity/fragments
        if (requestCode == WIZARD_PERMISSIONS_CODE) {
            when (resultCode) {
                WIZARD_RESULT_ALL_GRANTED -> {
                    /* when user finished wizard with all required permissions
                    granted - enable SDK if Device ID is exist*/
                    val deviceToken=requireArguments().getString("deviceToken")
                    enableTracking(deviceToken.toString())
                }
                WIZARD_RESULT_CANCELED -> {
                    // when user canceled wizard
                    Toast.makeText(activity, "Wizard closed!",
                        Toast.LENGTH_SHORT).show()
                }
                WIZARD_RESULT_NOT_ALL_GRANTED -> {
                    /* when user finished wizard with not all required permissions
     granted*/
                    Toast.makeText(activity, "NOT All Required Permissions Granted!",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }




    }





