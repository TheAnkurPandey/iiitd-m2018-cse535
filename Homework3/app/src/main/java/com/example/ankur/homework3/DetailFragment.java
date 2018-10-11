package com.example.ankur.homework3;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailFragment extends Fragment {

    String mID;

    TextView mTextView;
    RadioGroup mRadioGroup;
    Button mButton;
    SQLiteDatabase sqLiteDatabase;
    ProgressDialog mProgressDialog;

    boolean mIsResetButtonClicked;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_detail, container, false);
        sqLiteDatabase = new MySQLiteOpenHelper(getActivity()).getReadableDatabase();

        Bundle bundle = getArguments();
        mTextView = view.findViewById(R.id.df_tv);
        mID = bundle.getString("Question");
        mTextView.setText(mID);
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT Question, Answer FROM Question_Bank WHERE _id = ?", new String[]{mID}
                );

        if (cursor != null) {
            cursor.moveToFirst();
        }
        mTextView.setText(cursor.getString(0));

        mRadioGroup = view.findViewById(R.id.df_rg);

        if (cursor.getString(1).equals("True")) {
            mRadioGroup.check(R.id.df_t_rb);
        } else if (cursor.getString(1).equals("False")) {
            mRadioGroup.check(R.id.df_f_rb);
        }

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);

                /*if (!radioButton.isChecked() && mIsResetButtonClicked) {
                    radioButton.setChecked(true);
                    mIsResetButtonClicked = false;
                }*/

                Toast.makeText(getActivity(), radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        mButton = view.findViewById(R.id.df_bb);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Going to list fragment", Toast.LENGTH_SHORT).show();

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.ma_fl, new ListFragment());
                fragmentTransaction.commit();
            }
        });

        mButton = view.findViewById(R.id.df_rb);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Reset solution", Toast.LENGTH_SHORT).show();

                //RadioGroup radioGroup = view.findViewById(R.id.df_rg);
                //RadioButton trueRadioButton = radioGroup.findViewById(R.id.df_t_rb);
                //RadioButton falseRadioButton = radioGroup.findViewById(R.id.df_f_rb);
                RadioButton radioButton;
                if (mRadioGroup != null && mRadioGroup.getCheckedRadioButtonId() != -1) {
                    radioButton = (RadioButton) mRadioGroup.findViewById(mRadioGroup.getCheckedRadioButtonId());
                    //mIsResetButtonClicked = true;

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("Answer", "unanswered");

                    sqLiteDatabase.update("Question_Bank", contentValues, "_id = ?", new String[]{mID});
                    radioButton.setChecked(false);
                    Toast.makeText(getActivity(), "Reseted solution " + mRadioGroup.getCheckedRadioButtonId(), Toast.LENGTH_SHORT).show();
                }


            }
        });

        mButton = view.findViewById(R.id.df_sb);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Save solution", Toast.LENGTH_SHORT).show();
                RadioButton radioButton;
                if (mRadioGroup != null && mRadioGroup.getCheckedRadioButtonId() != -1) {
                    radioButton = (RadioButton) mRadioGroup.findViewById(mRadioGroup.getCheckedRadioButtonId());

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("Answer", radioButton.getText().toString());

                    sqLiteDatabase.update("Question_Bank", contentValues, "_id = ?", new String[]{mID});
                    Toast.makeText(getActivity(), "Saved solution " + radioButton.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        mButton = view.findViewById(R.id.df_submit);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkInternetConnection()) {
                    Toast.makeText(getActivity(), "Internet is not available", Toast.LENGTH_SHORT).show();
                    return;
                }

                exportDB();
                Toast.makeText(getActivity(), "solution submitted", Toast.LENGTH_SHORT).show();
                /*mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setTitle("Uploading");
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.show();
                */
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        File file  = new File(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() +
                                        "/quizData.csv"
                        );
                        String content_type  = getMimeType(file.getPath());

                        String file_path = file.getAbsolutePath();
                        Log.v("MyLog", file_path +" " + content_type);
                        OkHttpClient client = new OkHttpClient();
                        RequestBody file_body = RequestBody.create(MediaType.parse(content_type),file);

                        RequestBody request_body = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("type",content_type)
                                .addFormDataPart("uploaded_file",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                                .build();

                        Log.v("MyLog", "Request body created");

                        Request request = new Request.Builder()
                                .url("http://192.168.56.1/server.php")
                                .post(request_body)
                                .build();

                        try {
                            Response response = client.newCall(request).execute();

                            if(!response.isSuccessful()){
                                throw new IOException("Error : " + response);
                            }

                            //mProgressDialog.dismiss();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();


            }
        });

        return view;
    }

    private void exportDB() {

        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(getActivity(), "SD card not found", Toast.LENGTH_SHORT).show();
        }

        File exportDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");

        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "quizData.csv");
        Toast.makeText(getActivity(), file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        try {
            if (!file.createNewFile()) {
                Toast.makeText(getActivity(), "File created", Toast.LENGTH_SHORT).show();
            }
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            Cursor cur = sqLiteDatabase.rawQuery("SELECT * FROM Question_Bank",null);
            csvWrite.writeNext(cur.getColumnNames());
            while(cur.moveToNext()) {
                String arrStr[] ={cur.getString(0),cur.getString(1), cur.getString(2)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            cur.close();
        }
        catch(Exception sqlEx) {
            Log.e("Exception", sqlEx.getStackTrace().toString(), sqlEx);
            Toast.makeText(getActivity(), sqlEx.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
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
