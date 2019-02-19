package tn.igc.projectone.documentList.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.Toast;


import java.util.ArrayList;

import at.markushi.ui.CircleButton;
import tn.igc.projectone.MainActivity;
import tn.igc.projectone.documentList.fragments.PdfViewer;
import tn.igc.projectone.R;
import tn.igc.projectone.documentList.classes.CorrectionDoc;

import static android.content.Context.DOWNLOAD_SERVICE;

public class CorrectionRecyclerViewAdapter extends RecyclerView.Adapter<CorrectionRecyclerViewAdapter.MyViewHolder> {

    Context mContext ;
    private ArrayList<CorrectionDoc> lsDocument;
    private ArrayList<CorrectionDoc> documentListFull;
    DownloadManager dm;
    long queueid;


    public CorrectionRecyclerViewAdapter(Context context, ArrayList<CorrectionDoc> lsDocument) {
        this.mContext = context;
        this.lsDocument= lsDocument;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v=LayoutInflater.from(mContext).inflate(R.layout.cardview,viewGroup,false);
        // ------------------------------- Add final to vHolder declart -------------------------------------------------------
        final MyViewHolder vHolder = new MyViewHolder(v);
        final String path =lsDocument.get(i).getFilePath();
        final String title =lsDocument.get(i).getTitle();
        /*vHolder.circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "fwe", Toast.LENGTH_LONG).show();
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
        if (lsDocument.get(i).getUser().getName().equals("Corrige de l'enseignant ")){
            myViewHolder.userName.setTextColor(Color.parseColor("#008000"));
        }
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
    public void updateList(ArrayList<CorrectionDoc> newList){
        lsDocument =new ArrayList<>();
        lsDocument.addAll(newList);
        notifyDataSetChanged();
    }


}
