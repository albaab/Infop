package com.example.infopapp.ui.home_screens.fragments.home_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infopapp.R;
import com.example.infopapp.entities.PostItem;
import com.example.infopapp.utils.RoundPicasso;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends ListAdapter<PostItem, PostAdapter.PostItemViewHolder> {


//===================================PRIVATE ATTRIBUTES=============================================
    private List<PostItem> postItems = new ArrayList<>();
    private OnPostItemClickListener mListener;
    private Context context;


//===================================ADAPTER CONSTRUCTOR=============================================
    PostAdapter() {
        super(DIFF_CALLBACK);
    }


//==========================================VIEW HOLDER=============================================
    class PostItemViewHolder extends RecyclerView.ViewHolder {

        //VIEW ITEMS
        private TextView postItemText, postItemUsername, postItemTimeAndDate;
        private ImageView userImage;

        //VIEW HOLDER CONSTRUCTOR
        PostItemViewHolder(@NonNull View itemView) {
            super(itemView);
            //RETRIEVE THE ITEMS
            userImage = itemView.findViewById(R.id.user_profile_picture);
            postItemText = itemView.findViewById(R.id.post_text);
            postItemUsername = itemView.findViewById(R.id.post_username);
            postItemTimeAndDate = itemView.findViewById(R.id.post_date_text_view);

            //ON POST CLICK LISTENER
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && mListener != null) {
                        //todo OPEN A NEW ACTIVITY AND VIEW THE DETAILS OF THE POST
                        mListener.onItemClicked(postItems.get(position));
                    }
                }
            });
        }
    }


//===================================ON CREATE VIEWHOLDER===========================================
    @NonNull
    @Override
    public PostItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,
                parent, false);
        return new PostItemViewHolder(v);
    }

//===================================ON BIND VIEWHOLDER=============================================
    @Override
    public void onBindViewHolder(@NonNull PostItemViewHolder holder, int position) {
        PostItem currentPostItem = postItems.get(position);
        //DISPLAY PROFILE PIC OF THE USER WHO POSTED THAT POST ITEM
        if (currentPostItem.getUserPhotoUrl() != null) {
            Picasso.with(context)
                    .load(currentPostItem.getUserPhotoUrl())
                    .resize(722, 722)
                    .centerCrop()
                    .transform(new RoundPicasso(holder.userImage.getMaxHeight(), 0))
                    .into(holder.userImage);
        } else {
            Picasso.with(context)
                    .load(R.drawable.ic_person)
                    .resize(722, 722)
                    .centerCrop()
                    .transform(new RoundPicasso(holder.userImage.getMaxHeight(), 0))
                    .into(holder.userImage);
        }

        //SET THE DETAILS OF THE POST
        holder.postItemText.setText(currentPostItem.getPost());
        holder.postItemUsername.setText(currentPostItem.getUsername());
        String fullTimeAndDate = currentPostItem.getDate() + " @ " +
                currentPostItem.getTime();
        holder.postItemTimeAndDate.setText(fullTimeAndDate);
    }


//===================================GET ITEM COUNT METHOD==========================================
    @Override
    public int getItemCount() {
        return postItems.size();
    }


//=======================================ADAPTER METHOD=============================================
    void setListOfPostItems(Context context, List<PostItem> postItems) {
        this.context = context;
        this.postItems = postItems;
        notifyDataSetChanged();
    }


//==============================DIFF UTIL OBJECT TO COMPARE ITEM====================================
    private static final DiffUtil.ItemCallback<PostItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<PostItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull PostItem oldItem, @NonNull PostItem newItem) {
            return oldItem.getPost().equals(newItem.getPost());
        }

        @Override
        public boolean areContentsTheSame(@NonNull PostItem oldItem, @NonNull PostItem newItem) {
            return oldItem.getUsername().equals(newItem.getUsername()) &&
                    oldItem.getDate().equals(newItem.getDate()) &&
                    oldItem.getUserPhotoUrl().equals(newItem.getUserPhotoUrl()) &&
                    oldItem.getChannel().equals(newItem.getChannel()) &&
                    oldItem.getPost().equals(newItem.getPost()) &&
                    oldItem.getTime().equals(newItem.getTime());
        }
    };



//=======================================INTERFACE CALLBACK LISTENER================================
    public interface OnPostItemClickListener {
        void onItemClicked(PostItem postItem);
    }

//=======================================SET LISTENER METHOD=============================================
    public void setOnItemClickListener(OnPostItemClickListener mListener) {
        this.mListener = mListener;
    }


}
