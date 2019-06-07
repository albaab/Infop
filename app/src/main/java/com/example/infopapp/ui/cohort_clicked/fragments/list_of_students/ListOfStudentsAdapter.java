package com.example.infopapp.ui.cohort_clicked.fragments.list_of_students;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infopapp.R;
import com.example.infopapp.entities.Student;
import com.example.infopapp.utils.RoundPicasso;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ListOfStudentsAdapter extends ListAdapter<Student, ListOfStudentsAdapter.StudentViewHolder> {

//==================================PRIVATE ATTRIBUTES==============================================

    private static final String TAG = "LIST OF STUDENT ADAPTER" ;
    private List<Student> students = new ArrayList<>();
    private OnStudentClickListener mListener;
    private Context context;

//==================================ADAPTER CONSTRUCTOR=============================================
    ListOfStudentsAdapter() {
        super(DIFF_CALLBACK);
    }


//=====================================VIEW HOLDER==================================================
    class StudentViewHolder extends RecyclerView.ViewHolder {

        private TextView studentFullNameTv;
        private ImageView studentProfileImage;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentFullNameTv = itemView.findViewById(R.id.student_full_name);
            studentProfileImage = itemView.findViewById(R.id.student_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && mListener != null) {
                        mListener.onItemClicked(students.get(position));
                    }
                }
            });

        }
    }


//==================================ADAPTER METHOD==============================================
    Student getStudentAt(int position){
        notifyDataSetChanged();
        return students.get(position);
    }
//    void deleteStudentAt(int position){
//        students.remove(position);
//    }

//==================================ON CREATE VIEWHOLDER============================================
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item,
                parent, false);
        return new StudentViewHolder(v);
    }

//==================================ON BIND VIEW HOLDER=============================================
    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student currentStudent = students.get(position);
        String studentFullNameString = currentStudent.getFirstName()
                + " " + currentStudent.getLastName();
        holder.studentFullNameTv.setText(studentFullNameString);
        if(currentStudent.getProfileImageurl()!=null){
            Picasso.with(context)
                    .load(currentStudent.getProfileImageurl())
                    .resize(722,722)
                    .centerCrop()
                    .transform(new RoundPicasso(holder.studentProfileImage.getMaxHeight(), 0))
                    .into(holder.studentProfileImage);
        }else{
            Picasso.with(context)
                    .load(R.drawable.talent)
                    .resize(722,722)
                    .centerCrop()
                    .transform(new RoundPicasso(holder.studentProfileImage.getMaxHeight(), 0))
                    .into(holder.studentProfileImage);
        }

    }


//==================================GET ITEM COUNT METHOD===========================================
    @Override
    public int getItemCount() {
        return students.size();
    }

    void setListOfStudents(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
//        notifyDataSetChanged();
    }


//===============================DIFFUTIL OBJECT TO COMPARE ITEMS===================================
    private static final DiffUtil.ItemCallback<Student> DIFF_CALLBACK = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return oldItem.getId().equals(newItem.getId()) &&
                    oldItem.getFirstName().equals(newItem.getFirstName()) &&
                    oldItem.getLastName().equals(newItem.getLastName()) &&
                    oldItem.getSpecialization().equals(newItem.getSpecialization()) &&
                    oldItem.getDisplayName().equals(newItem.getDisplayName()) &&
                    oldItem.getProfileImageurl().equals(newItem.getProfileImageurl()) &&
                    oldItem.getPhone().equals(newItem.getPhone()) &&
                    oldItem.getCohort() == newItem.getCohort() &&
                    oldItem.getEmail().equals(newItem.getEmail());
        }
    };



//==================================INTERFACE CALLBACK LISTENER=====================================
    public interface OnStudentClickListener {
        void onItemClicked(Student student);
    }


//==================================SET ONCLIK LISTENER CALLBACK====================================
    void setOnItemClickListener(OnStudentClickListener mListener) {
        this.mListener = mListener;
    }


}