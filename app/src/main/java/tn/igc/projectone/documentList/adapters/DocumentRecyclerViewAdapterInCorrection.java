package tn.igc.projectone.documentList.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import at.markushi.ui.CircleButton;
import tn.igc.projectone.MainActivity;
import tn.igc.projectone.documentList.fragments.PdfViewer;
import tn.igc.projectone.R;
import tn.igc.projectone.documentList.classes.Document;

import static android.content.Context.DOWNLOAD_SERVICE;

public class DocumentRecyclerViewAdapterInCorrection extends RecyclerView.Adapter<DocumentRecyclerViewAdapterInCorrection.MyViewHolder> {

    Context mContext ;
    private ArrayList<Document> lsDocument;
    private ArrayList<Document> documentListFull;
    DownloadManager dm;
    long queueid;


    public DocumentRecyclerViewAdapterInCorrection(Context context, ArrayList<Document> lsDocument) {
        this.mContext = context;
        this.lsDocument= lsDocument;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,  int i) {
        View v;
        v=LayoutInflater.from(mContext).inflate(R.layout.cardview,viewGroup,false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        final String path =lsDocument.get(i).getFilePath();
        final String title =lsDocument.get(i).getTitle();
        /*vHolder.circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dm = (DownloadManager) v.getContext().getSystemService(DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(path));

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setTitle(title);
                queueid = dm.enqueue(request);


            }
        });*/



        vHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String b_filePath=lsDocument.get(vHolder.getAdapterPosition()).getFilePath();






                Bundle bundle = new Bundle();

                bundle.putString("b_filePath",b_filePath);




                Fragment PdfViewer = new PdfViewer();
                PdfViewer.setArguments(bundle);
                FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null).replace(R.id.container, PdfViewer);
                fragmentTransaction.commit();
            }
        });



        return vHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.docTitle.setText(lsDocument.get(i).getTitle());
        myViewHolder.userName.setText(lsDocument.get(i).getUser().getName());
        myViewHolder.verifiedImage.setImageResource(lsDocument.get(i).isVerifiedByProf());
        myViewHolder.avatar.setImageResource(lsDocument.get(i).getUser().getAvatar());


    }

    @Override
    public int getItemCount() {
        return lsDocument.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public View cardView;
        public CircleButton circleButton;

        private TextView docTitle;
        private ImageView verifiedImage ;
        private TextView userName;
        private ImageView avatar ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView =(CardView) itemView.findViewById(R.id.doc_card);

            docTitle = (TextView) itemView.findViewById(R.id.docTitle) ;
            verifiedImage = (ImageView) itemView.findViewById(R.id.verifiedImage);
            userName = (TextView) itemView.findViewById(R.id.userName) ;
            avatar = (ImageView) itemView.findViewById(R.id.avatar);

        }
    }
    public void updateList(ArrayList<Document> newList){
        lsDocument =new ArrayList<>();
        lsDocument.addAll(newList);
        notifyDataSetChanged();
    }


}
