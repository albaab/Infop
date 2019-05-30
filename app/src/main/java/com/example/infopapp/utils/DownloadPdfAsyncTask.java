package com.example.infopapp.utils;

import android.os.AsyncTask;
import android.view.View;

import com.example.infopapp.ui.cohort_clicked.fragments.StudentResumeFragment;
import com.example.infopapp.ui.dashboard.fragments.thisStudentResumeFragment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadPdfAsyncTask extends AsyncTask<String, Void, InputStream> {
    private WeakReference<thisStudentResumeFragment> fragmentWeakReference;
    private WeakReference<StudentResumeFragment> stdFragmentWeakReference;

    public DownloadPdfAsyncTask(thisStudentResumeFragment thisStudentResumeFragment) {
        fragmentWeakReference = new WeakReference<thisStudentResumeFragment>(thisStudentResumeFragment);
    }

    public DownloadPdfAsyncTask(StudentResumeFragment stdProfileFragment) {
        stdFragmentWeakReference = new WeakReference<>(stdProfileFragment);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (fragmentWeakReference!= null) {
            thisStudentResumeFragment thisStudentResumeFragment = fragmentWeakReference.get();
            thisStudentResumeFragment.progressBar.setVisibility(View.VISIBLE);
        } else if (stdFragmentWeakReference != null) {
            StudentResumeFragment studentResumeFragment = stdFragmentWeakReference.get();
            studentResumeFragment.progressBar.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected InputStream doInBackground(String... strings) {


        InputStream inputStream = null;
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;

    }

    @Override
    protected void onPostExecute(InputStream inputStream) {
        super.onPostExecute(inputStream);
//
        if (fragmentWeakReference != null) {
            thisStudentResumeFragment thisStudentResumeFragment = fragmentWeakReference.get();
            thisStudentResumeFragment.progressBar.setVisibility(View.GONE);
            thisStudentResumeFragment.resumePdfView.fromStream(inputStream).load();

        } else if (stdFragmentWeakReference != null) {
            StudentResumeFragment studentResumeFragment = stdFragmentWeakReference.get();
            studentResumeFragment.progressBar.setVisibility(View.GONE);
            studentResumeFragment.resumePdfView.fromStream(inputStream).load();

        }
    }
}
