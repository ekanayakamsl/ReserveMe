package com.smartlogic.saranga.reserveme.CommonViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smartlogic.saranga.reserveme.R;

/**
 * Created by sranga on 6/15/2016.
 */
public class HotelRoomAdapter extends BaseAdapter {

    String[] availabilities;
    String[] capacities;
    String[] charges;
    String[] roomNumbers;
    Context context;
    private static LayoutInflater inflater = null;


    public HotelRoomAdapter(String[] availabilities, String[] capacities, String[] charges, String[] roomNumbers, Context context) {
        this.availabilities = availabilities;
        this.capacities = capacities;
        this.charges = charges;
        this.roomNumbers = roomNumbers;
        this.context = context;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return roomNumbers.length;
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
        final View rowView;
        rowView = inflater.inflate(R.layout.room_list1, null);
        final TextView roomNumberTextView = (TextView) rowView.findViewById(R.id.roomNumberTextView);
        final TextView capacityTextView = (TextView) rowView.findViewById(R.id.capacityTextView);
        TextView chargeTextView = (TextView) rowView.findViewById(R.id.chargeTextView);
        TextView availabilityTextView = (TextView) rowView.findViewById(R.id.availabilityTextView);
        final LinearLayout row = (LinearLayout) rowView.findViewById(R.id.row);

        roomNumberTextView.setText(this.roomNumbers[position]);
        capacityTextView.setText(this.capacities[position]);
        chargeTextView.setText(this.charges[position]);
        availabilityTextView.setText(this.availabilities[position]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setPositiveButton("Reserve", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        row.setBackgroundColor(65536);
//                        if (!checkBox.isChecked()) {
//                            double charge = Double.parseDouble(ViewHotelActivity.chargeEditText.getText().toString()) + Double.parseDouble(charges[position]);
//                            int count = Integer.parseInt(ViewHotelActivity.countEditText.getText().toString()) + Integer.parseInt(capacities[position]);
//                            ViewHotelActivity.chargeEditText.setText(Double.toString(charge));
//                            ViewHotelActivity.countEditText.setText(Integer.toString(count));
//                            ViewHotelActivity.bookedRooms.put(roomNumbers[position], roomNumbers[position]);
//                            checkBox.setChecked(true);
//                        }
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        row.setBackgroundColor(65536);
//                        if (checkBox.isChecked()) {
//                            double charge = Double.parseDouble(ViewHotelActivity.chargeEditText.getText().toString()) - Double.parseDouble(charges[position]);
//                            int count = Integer.parseInt(ViewHotelActivity.countEditText.getText().toString()) - Integer.parseInt(capacities[position]);
//                            ViewHotelActivity.chargeEditText.setText(Double.toString(charge));
//                            ViewHotelActivity.countEditText.setText(Integer.toString(count));
//                            checkBox.setChecked(false);
//
//                        }
//                    }
//                });
//                builder.setMessage(charges[position])
//                        .setTitle(roomNumbers[position]);
//                AlertDialog dialog = builder.create();
//                dialog.show();
            }
        });
        return rowView;
    }
}

