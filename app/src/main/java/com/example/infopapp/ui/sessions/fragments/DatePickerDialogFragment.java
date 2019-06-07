package com.example.infopapp.ui.sessions.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static com.example.infopapp.utils.Constants.DATE_PICKED;
import static com.example.infopapp.utils.Constants.DAY_PICKED;
import static com.example.infopapp.utils.Constants.MONTH_PICKED;

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


//=======================================ATTRIBUTES=================================================
    private MyDatePickerDialogListener mListener;


//=======================================ON CREATE DIALOG===========================================

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),
                this, year, month, day);
    }
//=======================================ON DATE SET METHOD=================================================

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Bundle bundle = new Bundle();
        //GET CURRENT CALENDAR INSTANCE
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat month_format = new SimpleDateFormat("MMMM");
        String monthInString = month_format.format(calendar.getTime());

        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        bundle.putString(DATE_PICKED,currentDate);
        bundle.putInt(DAY_PICKED,dayOfMonth );
        bundle.putString(MONTH_PICKED,monthInString );
        mListener.getDatePicked(bundle);
    }

//=======================================ON ATTACH=================================================

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof MyDatePickerDialogListener){
            mListener = (MyDatePickerDialogListener) context;
        }else{
            throw new RuntimeException(context.toString() +
                    " must implement MyDatePickerDialogListener");
        }

    }

//=======================================ON DETACH==================================================
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//==================================INTERFACE CALLBACK LISTENER=====================================
    public interface MyDatePickerDialogListener {
        void getDatePicked(Bundle bundle);
    }

}
