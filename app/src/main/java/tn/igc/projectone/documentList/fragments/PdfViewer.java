package tn.igc.projectone.documentList.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import tn.igc.projectone.R;

public class PdfViewer extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pdf_fragment,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        Bundle bundle = getArguments();
        String b_filePath=bundle.getString("b_filePath");
        WebView webView = (WebView) view.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String url = "https://docs.google.com/viewer?embedded=true&url="+b_filePath;
        webView.loadUrl(url);
        return view;
    }
}
