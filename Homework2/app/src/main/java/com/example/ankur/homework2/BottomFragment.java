package com.example.ankur.homework2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class BottomFragment extends Fragment {

    private ProgressDialog mProgressDialog;

    // Fragment creates its View object hierarchy.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom, parent, false);
    }

    // onViewCreated() callback is triggered soon after onCreateView().
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Event listener for check connection button.
        Button mButton = view.findViewById(R.id.bf_chck_b);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInternetConnection())
                    Toast.makeText(getActivity(), "Internet is available", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Internet is not available", Toast.LENGTH_SHORT).show();
            }
        });

        // Event listener for download button.
        mButton = view.findViewById(R.id.bf_dwn_b);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Step 1: Check the Internet connection.
                if(!checkInternetConnection()) {
                    Toast.makeText(getActivity(), "Internet is not available", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getActivity(), "Internet is available", Toast.LENGTH_SHORT).show();

                // Instantiating the progress dialog.
                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage("Downloading...");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(true);

                // Instantiating the AsyncTask subclass.
                final Downloader download = new Downloader(getActivity());
                download.execute("http://faculty.iiitd.ac.in/~mukulika/s1.mp3");

                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        download.cancel(true);
                    }
                });
            }
        });
    }

    // Check the status of internet.
    private boolean checkInternetConnection() {
        // Safety check.
        if (getActivity() == null)
            return false;

        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Safety check.
        if (connectivityManager == null)
            return false;

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo!=null && activeNetworkInfo.isConnected())
            Log.i("Output", "Internet is available.");
        else
            Log.i("Output", "Internet is not available.");

        return activeNetworkInfo!=null && activeNetworkInfo.isConnected();
    }

    // Inner class.
    private class Downloader extends AsyncTask<String, Integer, String> {

        private Context mcontext;
        private PowerManager.WakeLock mWakeLock;

        public Downloader(Context context) {
            this.mcontext = context;
        }

        // doInBackground() is executing heavy task in separate background thread.
        @Override
        protected String doInBackground(String... Url) {

            // Creating placeholder object for input, output, HttpURLConnection.
            InputStream inputStream = null;
            OutputStream outputStream = null;
            HttpURLConnection connection = null;

            try {

                // Step 2:  Establish the connection.
                URL url = new URL(Url[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                Log.i("Output", "Connection is established.");

                // Step 3: Check the status code.
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.i("Output", "Server responded with HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage());
                    return "Server responded with HTTP " + connection.getResponseCode() + " " + connection.getResponseMessage();

                }

                // Store the size for percentage calculation.
                int contentLength = connection.getContentLength();

                // download the file
                inputStream = connection.getInputStream();

                // Safety check.
                if (getActivity() == null)
                    return null;

                outputStream = getActivity().openFileOutput("mc.mp3", Context.MODE_PRIVATE);

                byte data[] = new byte[4096];
                long total = 0;
                int count;

                // Step 4: Read the data in inputStream, publish the progress and write the data to outputStream.
                while ((count = inputStream.read(data)) != -1) {

                    // allow canceling with back button
                    if (isCancelled()) {
                        inputStream.close();
                        return null;
                    }

                    total += count;

                    // publish the progress, work only when total length is known.
                    if (contentLength > 0)
                        publishProgress((int) (total * 100 / contentLength));

                    outputStream.write(data, 0, count);
                }
                Log.i("Output", "Reading the data in inputStream, " +
                        "publishing the progress and writing the data to outputStream is completed.");

            } catch (Exception e) {
                Log.i("Output", "Exception is caught." + e.toString());
                return e.toString();

            } finally {

                // Step 5: Cleanup task.
                try {
                    if (outputStream != null)
                        outputStream.close();
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException ioException) {
                    Toast.makeText(getActivity(), ioException.toString(), Toast.LENGTH_LONG).show();
                }

                if (connection != null)
                    connection.disconnect();

                Log.i("Output", "Cleanup task is done.");
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Take CPU lock.
            PowerManager powerManager = (PowerManager) mcontext.getSystemService(Context.POWER_SERVICE);

            // Safety check.
            if (powerManager == null)
                return ;

            mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());

            mWakeLock.acquire(10000);
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            // Release the lock and dismiss the progress dialog.
            mWakeLock.release();
            mProgressDialog.dismiss();

            if (result != null) {
                Log.i("Output", "Download error: "+result);
                Toast.makeText(mcontext,"Download error: "+result, Toast.LENGTH_LONG).show();
            }
            else {
                Log.i("Output", "File is downloaded.");
                Toast.makeText(mcontext,"File downloaded", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
