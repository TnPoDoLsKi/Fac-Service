package tn.igc.projectone.documentList.adapters;

import android.app.DownloadManager;
import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import tn.igc.projectone.MainActivity;
import tn.igc.projectone.R;
import tn.igc.projectone.documentList.classes.Document;
import tn.igc.projectone.documentList.fragments.CorrectionList;

import static android.content.Context.DOWNLOAD_SERVICE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    private ArrayList<Document> lsDocument;
    private ArrayList<Document> documentListFull;
    DownloadManager dm;
    long queueid;

    public RecyclerViewAdapter(Context context, ArrayList<Document> lsDocument) {
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
                dm = (DownloadManager) v.getContext().getSystemService(DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(path));
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setTitle(title);
                queueid = dm.enqueue(request);

            }
        });
        //ADD -----------------------------------------------------------------------------------------------------------------
        //Begin
        vHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String b_id = lsDocument.get(vHolder.getAdapterPosition()).get_id();
                String b_avatar = lsDocument.get(vHolder.getAdapterPosition()).getUser().getAvatar();
                Boolean b_verifiedByProf = lsDocument.get(vHolder.getAdapterPosition()).getVerifiedByProf();
                String b_title = lsDocument.get(vHolder.getAdapterPosition()).getTitle();
                String b_firstName = lsDocument.get(vHolder.getAdapterPosition()).getUser().getFirstName();
                String b_lastName = lsDocument.get(vHolder.getAdapterPosition()).getUser().getLastName();
                String b_filePath = lsDocument.get(vHolder.getAdapterPosition()).getFilePath();
                String b_description = lsDocument.get(vHolder.getAdapterPosition()).getDescription();
                String b_major = lsDocument.get(vHolder.getAdapterPosition()).getMajor();


                Bundle bundle = new Bundle();
                bundle.putString("b_id", b_id);
                bundle.putString("b_avatar", b_avatar);
                bundle.putBoolean("b_verifiedByProf", b_verifiedByProf);
                bundle.putString("b_title", b_title);
                bundle.putString("b_firstName", b_firstName);
                bundle.putString("b_lastName", b_lastName);
                bundle.putString("b_filePath", b_filePath);
                bundle.putString("b_description", b_description);
                bundle.putString("b_major", b_major);


                Fragment corrFragment = new CorrectionList();
                corrFragment.setArguments(bundle);
                FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null).replace(R.id.container, corrFragment);
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
        Picasso.get().load(lsDocument.get(i).getUser().getAvatar()).placeholder(R.drawable.index).into(myViewHolder.avatar);
        myViewHolder.major.setText(lsDocument.get(i).getMajor());

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
        private TextView major;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.doc_card);
            downloadButton = itemView.findViewById(R.id.download);
            docTitle = itemView.findViewById(R.id.docTitle);
            verifiedImage = itemView.findViewById(R.id.verifiedImage);
            userName = itemView.findViewById(R.id.userName);
            avatar = itemView.findViewById(R.id.avatar);
            major = itemView.findViewById(R.id.majorName);
        }
    }

    public void updateList(ArrayList<Document> newList) {
        lsDocument = new ArrayList<>();
        lsDocument.addAll(newList);
        notifyDataSetChanged();
    }


}
