package com.ebs.rental.uidesigns;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.ebs.rental.general.R;

import java.util.ArrayList;

public class SpinnerDialog {

    @SuppressLint("StaticFieldLeak")
    public static ListView listview;
    public static ArrayList<String> arrayList;
    private static Dialog dialog;
    static ArrayAdapter<String> arrayAdapter;
    private static int position;

    public static void ShowSpinnerDialog(Context context , ArrayList<String> arrayList, final SpinnerInterface spinnerInterface, final int view_id, String header){

        dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_spinner);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_in);

//        int width = (int) (getResources().getDisplayMetrics().widthPixels*0.90);
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels*0.90);
        dialog.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);

        ListView listView = dialog.findViewById(R.id.spinner_list);
        TextView Header_title = dialog.findViewById(R.id.header_title);
        ImageView close_img = dialog.findViewById(R.id.close_img);

        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if(!header.equals("")) {
            assert Header_title != null;
            Header_title.setText(header);
        }



//        arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,arrayList);
//        listView.setAdapter(arrayAdapter);

        Custom_adapter custom_adapter = new Custom_adapter(arrayList,context);

        assert listView != null;
        listView.setAdapter(custom_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                position = pos;
                spinnerInterface.position(pos,view_id);

                view.startAnimation(anim);
                dialog.dismiss();


            }
        });

//        dialog.getWindow().getDecorView().startAnimation(anim);

        dialog.show();


    }





    static class Custom_adapter extends BaseAdapter{

        ArrayList<String> arrayList;
        Context context;

        Custom_adapter(ArrayList<String> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){

                convertView = LayoutInflater.from(context).inflate(R.layout.child_dialog_spinner,null);

            }

            TextView value = convertView.findViewById(R.id.values);

            value.setText(arrayList.get(position));


            return convertView;
        }
    }
}
