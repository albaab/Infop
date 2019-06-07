package com.example.infopapp.ui.home_screens.fragments.home_fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.infopapp.R;
import com.example.infopapp.entities.Cohort;
import com.example.infopapp.entities.PostItem;

import com.example.infopapp.entities.User;
import com.example.infopapp.ui.home_screens.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.infopapp.ui.account.fragments.SignInFragment.THIS_USERNAME;
import static com.example.infopapp.utils.Constants.EDACY_COMMUNITY;

public class HomeFragment extends Fragment {


//===================================PRIVATE ATTRIBUTES=============================================

    private EditText postEdit;
    private RecyclerView postRv;
    private PostAdapter postAdapter;

    private HomeViewModel homeViewModel;

//===================================PUBLIC EMPTY CONSTRUCTOR=======================================
    public HomeFragment() {
        // Required empty public constructor
    }


//===================================ON CREATE VIEW=================================================
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


//===================================ON VIEW CREATED=================================================
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //FIREBASE USER OBJECT
        FirebaseUser currenUser = FirebaseAuth.getInstance().getCurrentUser();

        //RETRIVE VIEW ITEMS IN LAYOUT
        TextView channelTitle = view.findViewById(R.id.home_channel_title_text_view);
        ImageButton postButton = view.findViewById(R.id.post_button);
        RecyclerView.LayoutManager rvManager = new LinearLayoutManager(getContext());

        //HOME VIEW MODEL INSTANCE
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        postEdit = view.findViewById(R.id.new_post_edit);//TEXT FIELD TO ADD COMMENT
        postRv = view.findViewById(R.id.post_recycler_view);// RECYCLER VIEW TO DISPLAY POSTS
        postAdapter = new PostAdapter();// ADAPTER TO ARRANGE THE POSTS
        postRv.setHasFixedSize(true);// STABILIZE THE RECYCLER VIEW
        postRv.setLayoutManager(rvManager);// SET THE LINEAR LAYOUT MANAGER OF THE RECYCLER VIEW

        //NEW POST ITEM
        final PostItem currentPostItem = new PostItem();

        //SET THE CHANNEL TITLE OF THE POST ITEM
        currentPostItem.setChannel(EDACY_COMMUNITY);
        channelTitle.setText(EDACY_COMMUNITY);

        //SET THE USERNAME OF THE POST ITEM
        currentPostItem.setUsername(THIS_USERNAME);

        //STORE THE LINK OF THE PHOTO OF THE USER IN THE POST ITEM
        if (currenUser != null) {
            if (currenUser.getPhotoUrl() != null) {
                currentPostItem.setUserPhotoUrl(currenUser.getPhotoUrl().toString());
            }
        }

        //POST BUTTON ONCLICK LISTENER
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: DEBUG-> POST BUTTON CLICKED");

                //PREPARE CALENDER TO RETRIEVE CURRENT TIME OF THE POST
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);
    
//                SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
//                String monthString = monthFormat.format(c.getTime());
                String currenDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
                String timeString = String.valueOf(hour)+":"+String.valueOf(minute);

                //IF THE TEXT FIELD IS NOT EMPTY-> PUSH THE POST TO FIREBASE DATABSE
                if (!postEdit.getText().toString().trim().isEmpty()){
                    currentPostItem.setPost(postEdit.getText().toString());
                    currentPostItem.setDate(currenDate);
                    currentPostItem.setTime(timeString);
                    homeViewModel.uploadPostToChannel(currentPostItem);
                    hideKeyboard();
                    postEdit.getText().clear();
                }else{
                    postEdit.setError(getText(R.string.field_empty));
                }


            }
        });

        //--------------------HOME VIEWMODEL -> GET ALL POSTS FOR THIS CHANNEL----------------------
        homeViewModel.getAllPostsForTHisChannel(EDACY_COMMUNITY, new HomeViewModel.HomeViewModelCallbackListener() {

            @Override
            public void getListOfPosts(List<PostItem> postItems) {
//                Log.d(TAG, "getListOfPosts: GETTING ALL LIST OF POSTS " + postItems.get(0));
                postAdapter.setListOfPostItems(getContext(), postItems);
                postRv.setAdapter(postAdapter);
                postRv.scrollToPosition(postItems.size() -1);
            }

            @Override
            public void getListOfcohorts(List<Cohort> cohorts) {

            }

        });

    }

//======================================UTILITY METHOD==============================================
    private void hideKeyboard(){
        View v = getActivity().getCurrentFocus();
        if (v != null){
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }

}
