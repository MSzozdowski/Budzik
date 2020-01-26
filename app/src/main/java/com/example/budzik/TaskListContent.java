package com.example.budzik;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskListContent {

    public static final List<Task> ITEMS = new ArrayList<Task>();
    public static final Map<String, Task> ITEM_MAP = new HashMap<String, Task>();


    public static void addItem(Task item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static void removeItem(int position) {
        String itemId=ITEMS.get(position).id;
        ITEMS.remove(position);
        ITEM_MAP.remove(itemId);
    }
    public static void clearList (){
        ITEMS.clear();
        ITEM_MAP.clear();
    }
    public static List<String> makeTimeList(){
        List<String> times=new ArrayList<String>();

        for(int i=0;i<TaskListContent.ITEMS.size();i++){
            String time_m=(TaskListContent.ITEMS.get(i).content).substring(3,5);
            String time_h=(TaskListContent.ITEMS.get(i).content).substring(0,2);
            String time;
            if(time_m.equals("00")){
                time_m="59";
                time_h=Integer.toString(Integer.valueOf(time_h)-1);
            }
            else
            {
                time_m=Integer.toString(Integer.valueOf(time_m)-1);
            }

            Integer currentMinute=Integer.valueOf(time_m);
            Integer currentHour=Integer.valueOf(time_h);

            if(currentMinute<10 && currentHour<10){
                time="0".concat(currentHour.toString()).concat(":0").concat(currentMinute.toString());
            }
            else if(currentMinute<10){
                time= currentHour.toString().concat(":0").concat(currentMinute.toString());
            }
            else if(currentHour<10){
                time="0".concat(currentHour.toString()).concat(":").concat(currentMinute.toString());
            }
            else
            {
                time= currentHour.toString().concat(":").concat(currentMinute.toString());
            }

            times.add(time);
        }
        return times;
    }

    public static class Task implements Parcelable {
        public final String id;
        public final String content;

        public Task(String id, String content) {
            this.id = id;
            this.content = content;
        }

        protected Task(Parcel in) {
            id = in.readString();
            content = in.readString();
        }

        public static final Creator<Task> CREATOR = new Creator<Task>() {
            @Override
            public Task createFromParcel(Parcel in) {
                return new Task(in);
            }

            @Override
            public Task[] newArray(int size) {
                return new Task[size];
            }
        };

        @Override
        public String toString() {
            return content;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(content);
        }
    }
}