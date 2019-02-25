package tn.igc.projectone.documentList.fragments;


import android.app.DownloadManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import tn.igc.projectone.R;



public class PdfViewer extends Fragment {

    PDFView pdfView;
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.pdf_fragment,container,false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        Bundle bundle = getArguments();

        String b_filePath=bundle.getString("b_filePath");

        String b_title=bundle.getString("b_title");

        pdfView =(PDFView) view.findViewById(R.id.pdfView1) ;
textView=(TextView) view.findViewById(R.id.TextviewPdf) ;
        new RetreivePDFstream().execute(b_filePath);
        if (isOnline()==false){
            textView.setText("Aucune connexion trouvee");
            textView.setTextSize(20);
            pdfView.setVisibility(View.INVISIBLE);
        }else {
            textView.setText("");
        }

        return view;


    }
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); return false; }
        catch (InterruptedException e) { e.printStackTrace();  return false;}


    }

        class RetreivePDFstream extends AsyncTask<String,Void,InputStream>{

            @Override
            protected InputStream doInBackground(String... strings) {
                InputStream inputStream=null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    if (urlConnection.getResponseCode() == 200) {
                        inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    }
                }
                catch (IOException e){
                    return null;
                }
                return inputStream;
            }

            @Override
            protected void onPostExecute(InputStream inputStream) {
                super.onPostExecute(inputStream);
                pdfView.fromStream(inputStream).load();
            }
        }

}
