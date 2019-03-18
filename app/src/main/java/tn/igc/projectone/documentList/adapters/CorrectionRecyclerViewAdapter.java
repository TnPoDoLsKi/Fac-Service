package tn.igc.projectone.documentList.adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import tn.igc.projectone.MainActivity;
import tn.igc.projectone.R;
import tn.igc.projectone.documentList.classes.CorrectionDoc;
import tn.igc.projectone.documentList.fragments.PdfViewer;

import static android.content.Context.DOWNLOAD_SERVICE;

public class CorrectionRecyclerViewAdapter extends RecyclerView.Adapter<CorrectionRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    private ArrayList<CorrectionDoc> lsDocument;
    private ArrayList<CorrectionDoc> documentListFull;
    DownloadManager dm;
    long queueid;


    public CorrectionRecyclerViewAdapter(Context context, ArrayList<CorrectionDoc> lsDocument) {
        this.mContext = context;
        this.lsDocument = lsDocument;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.cardview, viewGroup, false);
        // ------------------------------- Add final to vHolder declart -------------------------------------------------------
        final MyViewHolder vHolder = new MyViewHolder(v);
        final String path = lsDocument.get(i).getFilePath();
        final String title = lsDocument.get(i).getTitle();
        vHolder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "fwe", Toast.LENGTH_LONG).show();
                dm = (DownloadManager) v.getContext().getSystemService(DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(path));
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setTitle(title);
                queueid = dm.enqueue(request);
            }
        });
        vHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String b_filePath = lsDocument.get(vHolder.getAdapterPosition()).getFilePath();


                Bundle bundle = new Bundle();

                bundle.putString("b_filePath", b_filePath);


                Fragment PdfViewer = new PdfViewer();
                PdfViewer.setArguments(bundle);
                FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
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
        if (lsDocument.get(i).getUser().getName().equals("Corrige de l'enseignant ")) {
            myViewHolder.userName.setTextColor(Color.parseColor("#008000"));
        }
        myViewHolder.verifiedImage.setImageResource(lsDocument.get(i).isVerifiedByProf());
        Picasso.get().load(lsDocument.get(i).getUser().getAvatar()).placeholder(R.drawable.index).into(myViewHolder.avatar);

    }

    @Override
    public int getItemCount() {
        return lsDocument.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View cardView;
        public Button downloadButton;

        private TextView docTitle;
        private ImageView verifiedImage;
        private TextView userName;
        private ImageView avatar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.doc_card);
            downloadButton = (Button) itemView.findViewById(R.id.download);
            docTitle = (TextView) itemView.findViewById(R.id.docTitle);
            verifiedImage = (ImageView) itemView.findViewById(R.id.verifiedImage);
            userName = (TextView) itemView.findViewById(R.id.userName);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);

        }
    }

    public void updateList(ArrayList<CorrectionDoc> newList) {
        lsDocument = new ArrayList<>();
        lsDocument.addAll(newList);
        notifyDataSetChanged();
    }


}
