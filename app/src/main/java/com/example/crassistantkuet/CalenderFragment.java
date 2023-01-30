package com.example.crassistantkuet;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class CalenderFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CalenderFragment() {
    }

    public static CalenderFragment newInstance(String param1, String param2) {
        CalenderFragment fragment = new CalenderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    CalendarView calendarView;
    RadioButton ctButton , assignmentButton, othersButton;
    EditText courseNumber, courseTime, courseDetails;
    Button submitButton;

    View bottomSheetView;
    Button allEventButton;

    DatabaseReference calenderRootRef = FirebaseDatabase.getInstance().getReference().child("Calender");



    int a =0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_calender, container, false);

        initialization();

        // for current calender information..
        Calendar calendar = Calendar.getInstance();
        List<EventDay> events = new ArrayList<>();


        // for firebase data check in and update the event in the calender view

        calenderRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot main: snapshot.getChildren()){
                    String date = main.child("courseDate").getValue(String.class);

                    int currentDate = calendar.get(Calendar.DATE);
                    int newDate = Integer.parseInt(date);

                    if (currentDate<=newDate){
                        String month = main.child("courseMonth").getValue(String.class);
                        String year = main.child("courseYear").getValue(String.class);

                        Calendar newCalendar = Calendar.getInstance();

                        newCalendar.set(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(date));
                        // add this new day in the calender event
                        events.add(new EventDay(newCalendar, R.drawable.ic_event_calender));
                        calendarView.setEvents(events);

                        int l = events.size();
                        //Toast.makeText(getActivity(), Integer.toString(l), Toast.LENGTH_SHORT).show();

                    }
                    else {
                        DatabaseReference newRef = main.getRef();
                        newRef.removeValue();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        calendarView.setEvents(events);

        // test on click listener in the calender view
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();

                String date = Integer.toString(clickedDayCalendar.get(Calendar.DATE));
                //Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
                String month = Integer.toString(clickedDayCalendar.get(Calendar.MONTH));
                String year = Integer.toString(clickedDayCalendar.get(Calendar.YEAR));


                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(),R.style.BottomSheetDialogueTheme
                );
                bottomSheetView = LayoutInflater.from(getContext()).inflate(
                        R.layout.layout_bottom_sheet,
                        (LinearLayout)view.findViewById(R.id.bottomSheetContainer)
                );



                bottomSheetView.findViewById(R.id.submit_button_calender_view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        initializationBottomSheet();

                        String courseNumberString = courseNumber.getText().toString().trim() , courseTimeString = courseTime.getText().toString().trim(), courseDetailsString = courseDetails.getText().toString().trim();

                        setEventInTheFirebaseCalenderRoot(courseNumberString,courseTimeString,courseDetailsString,date,month,year);

                        //Toast.makeText(getContext(), "I am clicked", Toast.LENGTH_SHORT).show();
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            }
        });

        // for all event button in the calendar view
        allEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allEventIntent = new Intent(getActivity(),allEventView.class);
                startActivity(allEventIntent);
            }
        });

        return view;
    }

    private void initializationBottomSheet() {
        submitButton = bottomSheetView.findViewById(R.id.submit_button_calender_view);

        courseNumber = bottomSheetView.findViewById(R.id.course_number_edit_text);
        courseTime = bottomSheetView.findViewById(R.id.course_time_edit_text);
        courseDetails = bottomSheetView.findViewById(R.id.course_details_edit_text);

        ctButton = bottomSheetView.findViewById(R.id.radio_button_for_ct);
        assignmentButton = bottomSheetView.findViewById(R.id.radio_button_for_assignment);
        othersButton = bottomSheetView.findViewById(R.id.radio_button_for_others);
    }

    private void setEventInTheFirebaseCalenderRoot(String courseNumberString, String courseTimeString, String courseDetailsString, String date, String month, String year) {


        if(ctButton.isChecked()) a = 1;
        if(assignmentButton.isChecked()) a = 2;
        if (othersButton.isChecked()) a=3;



        calenderEventAllDataModelClass eventClass = new calenderEventAllDataModelClass(courseDetailsString,courseNumberString,courseTimeString,Integer.toString(a),year,month,date);


        calenderRootRef.push().setValue(eventClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                Toast.makeText(getActivity(), "Successfully Added To The Database", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void initialization() {
        calendarView = view.findViewById(R.id.calendarView);
        allEventButton = view.findViewById(R.id.all_event_view_button_in_calendar_view);
    }
}