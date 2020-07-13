package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<News> {

    private Activity context;
    private ArrayList<News> list;

    public MyListAdapter(Activity context, ArrayList<News> list) {
        super(context, R.layout.item_list_news, list);

        this.context = context;
        this.list = list;

    }

    static  class ViewHolder
    {
        TextView title,description,date, readCount;
        ImageView newsItemImage;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pozisyon = position;
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            LayoutInflater inflator = context.getLayoutInflater();
            convertView = inflator.inflate(R.layout.item_list_news, null);
            viewHolder = new ViewHolder();

            viewHolder.date = convertView.findViewById(R.id.newsItemTarih);
            viewHolder.title = convertView.findViewById(R.id.newsItemTitle);
            viewHolder.newsItemImage =convertView.findViewById(R.id.newsItemImage);


            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (list != null && list.size() > 0)
        {

            final News news = list.get(position);

            if(news!=null){
                if(news.getImg_url()!=null)
                    Picasso.with(context).load(news.getImg_url()).into(viewHolder.newsItemImage);
                viewHolder.title.setText(news.getTitle());
                viewHolder.date.setText(news.getDate());
            }



//            String myTag = new.getHaberTag();
//
//            if (myTag.length() > 0)
//            {
//                myTag = myTag.substring(0, myTag.length() - 7);
//            }
//
//            viewHolder.newsItemTag.setText(Html.fromHtml(myTag));



//            String dtStart = ""+Html.fromHtml(h.getHaberTarih());
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String sonTarihim = "";
//            try {
//                Date date = format.parse(dtStart);
//                //System.out.println(date);
//                Date dateson = new Date();
//                sonTarihim = printDifference(date,dateson);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }



        }
        return convertView;
    }
//
//    //1 minute = 60 seconds
////1 hour = 60 x 60 = 3600
////1 day = 3600 x 24 = 86400
//    public String printDifference(Date startDate, Date endDate) {
//
//        String sonucTarih = "";
//
//        //milliseconds
//        long different = endDate.getTime() - startDate.getTime();
//
//        System.out.println("startDate : " + startDate);
//        System.out.println("endDate : "+ endDate);
//        System.out.println("different : " + different);
//
//        long secondsInMilli = 1000;
//        long minutesInMilli = secondsInMilli * 60;
//        long hoursInMilli = minutesInMilli * 60;
//        long daysInMilli = hoursInMilli * 24;
//
//        long elapsedDays = different / daysInMilli;
//        different = different % daysInMilli;
//
//        long elapsedHours = different / hoursInMilli;
//        different = different % hoursInMilli;
//
//        long elapsedMinutes = different / minutesInMilli;
//        different = different % minutesInMilli;
//
//        long elapsedSeconds = different / secondsInMilli;
//
//        System.out.printf(
//                "%d days, %d hours, %d minutes, %d seconds%n",
//                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
//
//        if (elapsedDays < 1)
//        {
//            if (elapsedHours < 1)
//            {
//                if (dil.equals("TR"))
//                {
//                    sonucTarih = "şimdi";
//                }
//                else
//                {
//                    sonucTarih = "ئێستا";
//                }
//            }
//            else
//            {
//                if (dil.equals("TR"))
//                {
//                    sonucTarih = elapsedHours + " saat önce";
//                }
//                else
//                {
//                    //sonucTarih = "ئەمڕۆ"; //buraya bugün yazdık
//                    sonucTarih = elapsedHours + " کاتژمێر بەر لەئێستا";
//                }
//
//            }
//        }
//        else if (elapsedDays >= 1 && elapsedDays < 2)
//        {
//            if (dil.equals("TR"))
//            {
//                sonucTarih = "Dün";
//            }
//            else
//            {
//                sonucTarih = "دوێنێ";
//            }
//
//        }
//        else
//        {
//            sonucTarih = (String) android.text.format.DateFormat.format("dd.MM.yyyy", startDate);
//        }
//
//        return sonucTarih;
//    }
}

