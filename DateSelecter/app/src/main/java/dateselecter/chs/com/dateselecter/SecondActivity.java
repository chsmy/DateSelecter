package dateselecter.chs.com.dateselecter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import dateselecter.chs.com.dateselecter.util.GetTimeUtil;
import dateselecter.chs.com.dateselecter.wheelview.OnWheelChangedListener;
import dateselecter.chs.com.dateselecter.wheelview.WheelView;
import dateselecter.chs.com.dateselecter.wheelview.adapter.ArrayWheelAdapter;
import dateselecter.chs.com.dateselecter.wheelview.adapter.NumericWheelAdapter;

/**
 * 作者：chs on 2016/3/26 11:12
 * 邮箱：657083984@qq.com
 */
public class SecondActivity extends AppCompatActivity {
    private String ymdData[] = new String[720];
    private PopupWindow popupWindow;

    private WheelView wl_ymd;
    private WheelView wl_week;
    private WheelView wl_hour;
    private WheelView wl_min;
    private RelativeLayout rl_main;
    private TextView tv_end_time;
    String[] week_str =
            { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
    String[] xiaoshi_start =
            { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18",
                    "19", "20", "21", "22", "23" };

    String[] fenzhong_start =
            { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18",
                    "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36",
                    "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54",
                    "55", "56", "57", "58", "59" };
    String lastweek = "周一";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        rl_main = (RelativeLayout) findViewById(R.id.rl_main);
        initData();
        initEvent();
    }

    private void initData() {
        long currentTime = System.currentTimeMillis();
        long day = 24 * 60 * 60 * 1000;
        long lastYear = currentTime - day * 360;
        for (int i = 0; i < 720; i++) {
            long time = lastYear + day * i;
            ymdData[i] = GetTimeUtil.getYMDTime(time);
        }
    }

    private void initEvent() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop();
            }
        });
    }

    private void showPop() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupWindowView = inflater.inflate(R.layout.pop_item, null);
        tv_end_time = (TextView) popupWindowView.findViewById(R.id.tv_end_time);
        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        initWheelView(popupWindowView);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                makeWindowLight();
            }
        });
        popupWindow.showAtLocation(rl_main, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
    }

    private void initWheelView(View view) {
        Calendar c = Calendar.getInstance();
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
        int curDate = c.get(Calendar.DATE);
        wl_ymd = (WheelView) view.findViewById(R.id.wl_ymd);
        wl_week = (WheelView) view.findViewById(R.id.wl_week);
        wl_hour = (WheelView) view.findViewById(R.id.wl_hour);
        wl_min = (WheelView) view.findViewById(R.id.wl_min);

        ArrayWheelAdapter<String> weekAdapter = new ArrayWheelAdapter<>(SecondActivity.this, ymdData);
        List<String> ymdList = Arrays.asList(ymdData);
        wl_ymd.setViewAdapter(weekAdapter);
        weekAdapter.setTextSize(18);
        wl_ymd.setCyclic(true);
        wl_ymd.setCurrentItem(ymdList.indexOf(GetTimeUtil.getYMDTime(System.currentTimeMillis())));
        wl_ymd.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String value = ymdData[newValue];
                int year = Integer.parseInt(value.substring(0, value.indexOf("-")));
                int month = Integer.parseInt(value.substring(value.indexOf("-") + 1, value.lastIndexOf("-")));
                int day = Integer.parseInt(value.substring(value.lastIndexOf("-") + 1, value.length()));
                changeWheelWeek(year, month, day);
                tv_end_time.setText(value);
            }
        });

        ArrayWheelAdapter<String> weekAdapter2 = new ArrayWheelAdapter<String>(SecondActivity.this, week_str);
        wl_week.setViewAdapter(weekAdapter2);
        weekAdapter2.setTextSize(18);
        wl_week.setEnabled(false);
        wl_week.setCyclic(true);
        changeWheelWeek(curYear, curMonth, curDate);

        NumericWheelAdapter numericAdapter1 = new NumericWheelAdapter(SecondActivity.this, 0, 23);
        numericAdapter1.setLabel("：");
        numericAdapter1.setTextSize(18);
        wl_hour.setViewAdapter(numericAdapter1);
        wl_hour.setCyclic(true);// 可循环滚动

        NumericWheelAdapter numericAdapter2 = new NumericWheelAdapter(SecondActivity.this, 0, 59);
        numericAdapter2.setLabel("");
        numericAdapter2.setTextSize(18);
        wl_min.setViewAdapter(numericAdapter2);
        wl_min.setCyclic(true);// 可循环滚动


        String currenthh = new SimpleDateFormat("HH").format(c.getTime());
        List<String> asList = Arrays.asList(xiaoshi_start);
        int hour_index = asList.indexOf(currenthh);
        wl_hour.setCurrentItem(hour_index);

        String currentmm = new SimpleDateFormat("mm").format(c.getTime());
        List<String> asList2 = Arrays.asList(fenzhong_start);
        int min_index = asList2.indexOf(currentmm);
        wl_min.setCurrentItem(min_index);
    }
    private void changeWheelWeek(int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        wl_week.setCurrentItem(i - 1);
        lastweek = week_str[i - 1];
    }
    /**
     * 让屏幕变暗
     */
    private void makeWindowDark() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
    }

    /**
     * 让屏幕变亮
     */
    private void makeWindowLight() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 1f;
        window.setAttributes(lp);
    }
}
