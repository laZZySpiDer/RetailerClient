package com.example.aditya.retailerclient.Adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.aditya.retailerclient.ConstValues;
import com.example.aditya.retailerclient.Model.BrochureDisplay;
import com.example.aditya.retailerclient.R;

import java.util.List;

/**
 * Created by Aditya on 8/10/2018.
 */

public class BrochureRecyclerAdapter extends RecyclerView.Adapter<BrochureRecyclerAdapter.MyViewHolder> {

    Context mContext;
    List<BrochureDisplay> mData;
    DownloadManager downloadManager;

    public BrochureRecyclerAdapter(Context mContext, List<BrochureDisplay> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public BrochureRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_brochure,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(BrochureRecyclerAdapter.MyViewHolder holder, final int position) {
        holder.pam_title.setText(mData.get(position).getP_Title());
        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(mData.get(position).getP_Image());
            }
        });
    }


    private void downloadFile(String file) {
        downloadManager = (DownloadManager)mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(ConstValues.BroImageLink+file);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView pam_title;
        private ImageView down;

        public MyViewHolder(View itemView) {
            super(itemView);
            pam_title = (TextView)itemView.findViewById(R.id.pampTitle);
            down = (ImageView)itemView.findViewById(R.id.downloadBtn);
        }
    }
}
