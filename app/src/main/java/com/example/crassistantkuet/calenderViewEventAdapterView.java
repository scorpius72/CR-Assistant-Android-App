package com.example.crassistantkuet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class calenderViewEventAdapterView extends RecyclerView.Adapter<calenderViewEventAdapterView.MyViewHolder> {


    Context context;

    ArrayList<calenderEventAllDataModelClass> events;

    public calenderViewEventAdapterView(Context context, ArrayList<calenderEventAllDataModelClass> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.all_event_layout_design,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        calenderEventAllDataModelClass event = events.get(position);
        holder.courseNumber.setText("Course No: "+event.getCourseNumber());
        holder.courseDetails.setText(event.getCourseDetails());

        String result;
        int t = Integer.parseInt(event.getCourseType());
        if (t==1) result = "CT";
        else if (t==2) result = "Assignment";
        else result="Others";

        holder.courseType.setText(result);

        String s = "Date: "+event.getCourseDate()+"/"+event.getCourseMonth()+"/"+event.getCourseYear()+"     Time: "+event.getCourseTime();

        holder.courseTimeAndDate.setText(s);

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView courseType, courseTimeAndDate, courseNumber, courseDetails;

         public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            courseType = itemView.findViewById(R.id.course_type_in_the_event_view);
            courseTimeAndDate = itemView.findViewById(R.id.course_date_and_time_event_view);
            courseNumber = itemView.findViewById(R.id.course_no_in_event_view);
            courseDetails = itemView.findViewById(R.id.course_details_in_event_view);
        }
    }
}
