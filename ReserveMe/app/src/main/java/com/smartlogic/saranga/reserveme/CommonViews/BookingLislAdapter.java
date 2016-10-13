package com.smartlogic.saranga.reserveme.CommonViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smartlogic.saranga.reserveme.R;

/**
 * Created by sranga on 6/13/2016.
 */
public class BookingLislAdapter extends BaseAdapter {

    String[] startDates;
    String[] endDates;
    String[] names;
    String[] pnoneNumbers;
    String[] counts;
    String[] charges;
    String[] roomNumbers;
    Context context;
    private static LayoutInflater inflater=null;

    public BookingLislAdapter(String[] stareDates, String[] endDates, String[] names, String[] pnoneNumbers, String[] counts, String[] charge, String[] roomNumbers, Context context) {
        this.startDates = stareDates;
        this.endDates = endDates;
        this.names = names;
        this.pnoneNumbers = pnoneNumbers;
        this.counts = counts;
        this.charges = charge;
        this.roomNumbers = roomNumbers;
        this.context = context;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = inflater.inflate(R.layout.booking_list, null);
        TextView startDateTextView = (TextView) rowView.findViewById(R.id.startDateTextView);
        TextView endDateTextView = (TextView) rowView.findViewById(R.id.endDateTextView);
        TextView customerNameTextView = (TextView) rowView.findViewById(R.id.customerNameTextView);
        TextView customerPhoneTextView = (TextView) rowView.findViewById(R.id.customerPhoneTextView);
        TextView countTextView = (TextView) rowView.findViewById(R.id.countTextView);
        TextView chargeTextView = (TextView) rowView.findViewById(R.id.chargeTextView);
        TextView roomNumberTextView = (TextView) rowView.findViewById(R.id.roomNumberTextView);
        
        startDateTextView.setText(this.startDates[position]);
        endDateTextView.setText(this.endDates[position]);
        customerNameTextView.setText(this.names[position]);
        customerPhoneTextView.setText(this.pnoneNumbers[position]);
        countTextView.setText(this.counts[position]);
        chargeTextView.setText(this.charges[position]);
        roomNumberTextView.setText(this.roomNumbers[position]);
        
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });
        return rowView;
    }
}
