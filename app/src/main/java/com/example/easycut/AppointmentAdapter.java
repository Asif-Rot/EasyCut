/**
 * this class is used for adapter for Recyclerview
 */

package com.example.easycut;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easycut.objects.Appointment;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {

    private Context context;
    private ArrayList<Appointment> appointmentArrayList;

    public AppointmentAdapter(Context context, ArrayList<Appointment> appointmentArrayList){
        this.context = context;
        this.appointmentArrayList = appointmentArrayList;
    }

    @NonNull
    @Override
    public AppointmentAdapter.AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_layout, parent, false);
        return new AppointmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.AppointmentHolder holder, @SuppressLint("RecyclerView")  int position) {
        Appointment appointment = appointmentArrayList.get(position);
        holder.setDetails(appointment);
        holder.btnCancel.setOnClickListener(v -> new AlertDialog.Builder(context)
                .setTitle("Cancel appointment")
                .setMessage("Are you sure you want to cancel this appointment?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    FireBaseService.deleteAppointment(appointment);
                    appointmentArrayList.remove(position);
                    notifyItemRemoved(position);
                })
                .setNegativeButton(android.R.string.no, null).show());
    }

    @Override
    public int getItemCount() {
        return appointmentArrayList.size();
    }

    static class AppointmentHolder extends  RecyclerView.ViewHolder{
        private TextView txtDate,txtTime, txtType;
        private Button btnCancel;
        public AppointmentHolder(View itemView){
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtType = itemView.findViewById(R.id.txtType);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }

        @SuppressLint("SetTextI18n")
        void setDetails(Appointment appointment){
            txtDate.setText("Date: "+appointment.getDate());
            txtTime.setText("Time: "+appointment.getStartTime());
            txtType.setText("Type: "+Integer.toString(appointment.getType()));
        }
    }
}
