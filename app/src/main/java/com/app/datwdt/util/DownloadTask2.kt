//package com.app.datwdt.util
//
//import android.app.ProgressDialog
//import android.content.Context
//import android.os.Handler
//import android.util.Log
//import com.bluebird.customer.preference.PrefKeys
//import java.io.File
//import java.io.FileOutputStream
//import java.lang.Exception
//import java.net.HttpURLConnection
//import java.net.URL
//
//class DownloadTask2(
//    private val context: Context,
//    downloadUrl: String,
//    sharedPreference: SharedPreference
//) {
//    private val sharedPreference: SharedPreference
//    private val downloadUrl = ""
//    private var downloadFileName = ""
//    private var progressDialog: ProgressDialog? = null
//
//    private inner class DownloadingTask : AsyncTask<Void?, Void?, Void?>() {
//        var apkStorage: File? = null
//        var outputFile: File? = null
//        protected override fun onPreExecute() {
//            super.onPreExecute()
//            progressDialog = ProgressDialog(context)
//            progressDialog!!.setMessage(sharedPreference.getValueString(PrefKeys.MSG_DOWNLOADING))
//            progressDialog!!.setCancelable(false)
//            progressDialog!!.show()
//        }
//
//        protected override fun onPostExecute(result: Void) {
//            try {
//                if (outputFile != null) {
//                    progressDialog!!.dismiss()
//                    GlobalMethods.Dialog(
//                        context,
//                        sharedPreference.getValueString(PrefKeys.MSG_INVOICE_DOWNLOADED_SUCCESSFULLY),
//                        sharedPreference.getValueString(PrefKeys.BTN_GLOBAL_OK)
//                    )
//
//                } else {
//                    Handler().postDelayed({ }, 3000)
//                    LogUtils.Print(TAG, "Download Failed")
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//
//                //Change button text if exception occurs
//                Handler().postDelayed({ }, 3000)
//                LogUtils.Print(TAG, "Download Failed with Exception - " + e.localizedMessage)
//            }
//            super.onPostExecute(result)
//        }
//
//        protected override fun doInBackground(vararg arg0: Void): Void {
//            try {
//                val url = URL(downloadUrl) //Create Download URl
//                val c = url.openConnection() as HttpURLConnection //Open Url Connection
//                c.requestMethod = "GET" //Set Request Method to "GET" since we are grtting data
//                c.connect() //connect the URL Connection
//
//                //If Connection response is not OK then show Logs
//                if (c.responseCode != HttpURLConnection.HTTP_OK) {
//                    LogUtils.Print(
//                        TAG, "Server returned HTTP " + c.responseCode
//                                + " " + c.responseMessage
//                    )
//                }
//
//
//                //Get File if SD card is present
//                if (CheckForSDCard().isSDCardPresent) {
//                    apkStorage = File(
//                        Environment.getExternalStorageDirectory().toString() + "/" + "BlueBird"
//                    )
//                } else Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT)
//                    .show()
//
//                //If File is not present create directory
//                if (!apkStorage!!.exists()) {
//                    apkStorage!!.mkdir()
//                    LogUtils.Print(TAG, "Directory Created.")
//                }
//                outputFile = File(apkStorage, downloadFileName) //Create Output file in Main File
//
//                //Create New File if not present
//                if (!outputFile!!.exists()) {
//                    outputFile!!.createNewFile()
//                    LogUtils.Print(TAG, "File Created")
//                }
//                val fos = FileOutputStream(outputFile) //Get OutputStream for NewFile Location
//                val `is` = c.inputStream //Get InputStream for connection
//                val buffer = ByteArray(1024) //Set buffer type
//                var len1 = 0 //init length
//                while (`is`.read(buffer).also { len1 = it } != -1) {
//                    fos.write(buffer, 0, len1) //Write new file
//                }
//
//                //Close all connection after doing task
//                fos.close()
//                `is`.close()
//            } catch (e: Exception) {
//
//                //Read exception if something went wrong
//                e.printStackTrace()
//                outputFile = null
//                LogUtils.Print(TAG, "Download Error Exception " + e.message)
//            }
//            return null
//        }
//    }
//
//    companion object {
//        private const val TAG = "Download Task"
//    }
//
//    init {
//        this.downloadUrl = downloadUrl
//        this.sharedPreference = sharedPreference
//        downloadFileName = downloadUrl.substring(
//            downloadUrl.lastIndexOf('/'),
//            downloadUrl.length
//        ) //Create file name by picking download file name from URL
//        LogUtils.Print(TAG, downloadFileName)
//
//        //Start Downloading Task
//        DownloadingTask().execute()
//    }
//}