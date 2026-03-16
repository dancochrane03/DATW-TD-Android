package com.app.datwdt.util

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import com.app.datwdt.util.DownloadTask
import com.app.datwdt.util.CheckForSDCard
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.ContextThemeWrapper
import android.widget.Toast
import com.app.datwdt.DatwdtApplication
import com.app.datwdt.R
import com.bumptech.glide.Glide
import kotlin.Throws
import com.app.datwdt.util.DownloadTask.DownloadingTask
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.target.Target
import java.io.*
import java.lang.Exception
import java.net.URL
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.util.concurrent.ExecutionException
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory

class DownloadTask(private val context: Context, downloadUrl: String) {
    private var downloadUrl = ""
    private var downloadFileName = ""
    private var progressDialog: ProgressDialog? = null

    private inner class DownloadingTask : AsyncTask<Void?, Void?, Void?>() {
        var apkStorage: File? = null
        var outputFile: File? = null
        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(context)
            progressDialog!!.setMessage("Downloading")
            progressDialog!!.setCancelable(true)
            progressDialog!!.show()
        }

        override fun onPostExecute(result: Void?) {
            try {
                if (outputFile != null) {
                    progressDialog!!.dismiss()

                    GlobalMethods.Dialog(context, "Downloaded Successfully", "ok");

//                    Toast.makeText(context, "Image Downloaded Successfully", Toast.LENGTH_SHORT)
//                        .show();
                } else {
                    Handler().postDelayed({ }, 3000)
                    LogUtils.Print(TAG, "Download Failed")
                }
            } catch (e: Exception) {
                e.printStackTrace()

                //Change button text if exception occurs
                Handler().postDelayed({ }, 3000)
                LogUtils.Print(TAG, "Download Failed with Exception - " + e.localizedMessage)
            }
            super.onPostExecute(result)
        }

        protected override fun doInBackground(vararg p0: Void?): Void? {
            try {
//                val url = URL(downloadUrl) //Create Download URL
//                val c = url.openConnection() as HttpsURLConnection //Open Url Connection
//                c.requestMethod = "GET" //Set Request Method to "GET" since we are grtting data
//                                configureHttps(c);
//                c.connect() //connect the URL Connection
//
//                If Connection response is not OK then show Logs
//                if (c.responseCode != HttpsURLConnection.HTTP_OK) {
//                    LogUtils.Print(
//                        TAG, "Server returned HTTP " + c.responseCode
//                                + " " + c.responseMessage
//                    )
//                }


                //Get File if SD card is present
                if (CheckForSDCard().isSDCardPresent) {
                    apkStorage =
                        File(Environment.getExternalStorageDirectory().toString() + "/" + "DATW")
                } else Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT)
                    .show()

                //If File is not present create directory
                if (!apkStorage!!.exists()) {
                    apkStorage!!.mkdir()
                    LogUtils.Print(TAG, "Directory Created.")
                }

//                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File
//
//                //Create New File if not present
//                if (!outputFile.exists()) {
//                    outputFile.createNewFile();
//                    LogUtils.Print(TAG, "File Created");
//                }
//
//                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location
//
//                InputStream is = c.getInputStream();//Get InputStream for connection
//
//                byte[] buffer = new byte[1024];//Set buffer type
//                int len1 = 0;//init length
//                while ((len1 = is.read(buffer)) != -1) {
//                    fos.write(buffer, 0, len1);//Write new file
//                }
//
//                //Close all connection after doing task
//                fos.close();
//                is.close();
                try {
                    val okHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient
                    Glide.get(DatwdtApplication.context).registry.replace(
                        GlideUrl::class.java,
                        InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient)
                    )

                    val file = Glide.with(context)
                        .load(downloadUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get()
                    outputFile = File(apkStorage, downloadFileName)
                    if (!outputFile!!.exists()) {
                        val success = outputFile!!.createNewFile()
                        LogUtils.Print(TAG, "File Created  $success")
                        /* if (!success) {
                            return null;
                        }*/
                    }
                    var `in`: InputStream? = null
                    var out: OutputStream? = null
                    try {
                        `in` = BufferedInputStream(FileInputStream(file))
                        out = BufferedOutputStream(FileOutputStream(outputFile))
                        val buf = ByteArray(1024)
                        var len: Int
                        while (`in`.read(buf).also { len = it } > 0) {
                            out.write(buf, 0, len)
                        }
                        out.flush()
                    } finally {
                        if (`in` != null) {
                            try {
                                `in`.close()
                            } catch (e1: IOException) {
                                e1.printStackTrace()
                            }
                        }
                        if (out != null) {
                            try {
                                out.close()
                            } catch (e1: IOException) {
                                e1.printStackTrace()
                            }
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: ExecutionException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } catch (e: Exception) {

                //Read exception if something went wrong
                e.printStackTrace()
                outputFile = null
                LogUtils.Print(TAG, "Download Error Exception " + e.message)
            }
            return null
        }
    }

    @Throws(GeneralSecurityException::class)
    protected fun configureHttps(connection: HttpsURLConnection) {
        // Configure SSL
        val algorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(algorithm)
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, tmf.trustManagers, null)
        connection.sslSocketFactory = sslContext.socketFactory
    }

    companion object {
        private const val TAG = "Download Task"
        fun getSSLSocketFactory(trustKey: KeyStore?, SSLAlgorithm: String?): SSLSocketFactory? {
            try {
                val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                tmf.init(trustKey)
                val context = SSLContext.getInstance(SSLAlgorithm) //"SSL" "TLS"
                context.init(
                    null,
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).trustManagers,
                    null
                )
                return context.socketFactory
            } catch (e: Exception) {
            }
            return null
        }
    }

    init {
        this.downloadUrl = downloadUrl
        downloadFileName = downloadUrl.substring(
            downloadUrl.lastIndexOf('/'),
            downloadUrl.length
        ) //Create file name by picking download file name from URL
        LogUtils.Print(TAG, downloadFileName)

        //Start Downloading Task
        DownloadingTask().execute()
    }

}