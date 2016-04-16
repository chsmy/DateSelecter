package dateselecter.chs.com.dateselecter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
public class ThridActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_last_month, tv_current_time, tv_next_month;
    private String selectDate = "";
    private PopupWindow popupWindow;
    private RelativeLayout rl_main;
    private WheelView wl_year, wl_month;
    private int screenWidth;
    private String currentTime;
    private int currentYear;
    private int currentMonth;
    private int currentSelectYear, currentSelectMonth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thrid);
        initView();
        initEvent();
    }

    private void initView() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        currentTime = GetTimeUtil.getYMTime(System.currentTimeMillis());
        currentYear = Integer.valueOf(currentTime.substring(0, 4));
        currentMonth = Integer.valueOf(currentTime.substring(5));
        selectDate = currentTime;

        tv_last_month = (TextView) findViewById(R.id.tv_last_month);
        tv_current_time = (TextView) findViewById(R.id.tv_current_time);
        tv_next_month = (TextView) findViewById(R.id.tv_next_month);
        rl_main = (RelativeLayout) findViewById(R.id.rl_main);
        tv_current_time.setText(currentTime);
    }

    private void initEvent() {
        tv_last_month.setOnClickListener(this);
        tv_current_time.setOnClickListener(this);
        tv_next_month.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_last_month:
                tv_next_month.setTextColor(getResources().getColor(R.color.blue_text));
                tv_next_month.setClickable(true);
                selectDate = GetTimeUtil.getLastMonth(selectDate, -1);
                tv_current_time.setText(selectDate);
                break;
            case R.id.tv_current_time:
                showDatePop();
                popupWindow.showAtLocation(rl_main, Gravity.CENTER, 0, 0);
                makeWindowDark();
                break;
            case R.id.tv_next_month:
                if (!TextUtils.isEmpty(selectDate)) {
                    int selectMonth = Integer.valueOf(selectDate.substring(5));
                    int selectYear = Integer.valueOf(selectDate.substring(0, 4));
                    if (selectYear == currentYear) {
                        if (selectMonth + 1 == currentMonth) {
                            tv_next_month.setTextColor(getResources().getColor(R.color.gray_text));
                            tv_next_month.setClickable(false);
                        }
                        if (!(selectMonth + 1 > currentMonth)) {
                            selectDate = GetTimeUtil.getLastMonth(selectDate, 1);
                            tv_current_time.setText(selectDate);
                        }
                    } else if (selectYear < currentYear) {
                        selectDate = GetTimeUtil.getLastMonth(selectDate, 1);
                        tv_current_time.setText(selectDate);
                    }

                }
                break;
        }
    }

    //选择时间弹窗
    private void showDatePop() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupWindowView = inflater.inflate(R.layout.pop_date_select, null);
        Button tv_ok = (Button) popupWindowView.findViewById(R.id.btn_ok);
        Button tv_cancel_time = (Button) popupWindowView.findViewById(R.id.btn_cancel);
        popupWindow = new PopupWindow(popupWindowView, screenWidth * 4 / 5, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        initWheelView(popupWindowView);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                makeWindowLight();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentSelectYear = wl_year.getCurrentItem() + 2010;//年
                currentSelectMonth = wl_month.getCurrentItem() + 1;//月
                selectDate = String.valueOf(currentSelectYear) + "-" + String.format("%02d", currentSelectMonth);

                if (currentTime.equals(selectDate)) {
                    tv_next_month.setTextColor(getResources().getColor(R.color.gray_text));
                    tv_next_month.setClickable(false);
                } else {
                    tv_next_month.setTextColor(getResources().getColor(R.color.blue_text));
                    tv_next_month.setClickable(true);
                }
                if (currentSelectYear > currentYear) {
                    showShortToast("请选择正确的日期");
                } else if (currentSelectYear == currentYear) {
                    if (currentSelectMonth > currentMonth) {
                        showShortToast("请选择正确的日期");
                    } else {
                        tv_current_time.setText(selectDate);
                        popupWindow.dismiss();
                    }
                } else {
                    tv_current_time.setText(selectDate);
                    popupWindow.dismiss();
                }


            }
        });
        tv_cancel_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private void initWheelView(View view) {
        Calendar c = Calendar.getInstance();
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
        wl_year = (WheelView) view.findViewById(R.id.wl_year);
        wl_month = (WheelView) view.findViewById(R.id.wl_month);

        NumericWheelAdapter numericWheelAdapterStart1 = new NumericWheelAdapter(this, 2010, 2100);
        numericWheelAdapterStart1.setLabel("年");
        wl_year.setViewAdapter(numericWheelAdapterStart1);
        numericWheelAdapterStart1.setTextColor(R.color.black);
        numericWheelAdapterStart1.setTextSize(20);
        wl_year.setCyclic(true);//是否可循环滑动

        NumericWheelAdapter numericWheelAdapterStart2 = new NumericWheelAdapter(this, 1, 12, "%02d");
        numericWheelAdapterStart2.setLabel("月");
        wl_month.setViewAdapter(numericWheelAdapterStart2);
        numericWheelAdapterStart2.setTextColor(R.color.black);
        numericWheelAdapterStart2.setTextSize(20);
        wl_month.setCyclic(true);
        wl_year.setCurrentItem(curYear - 2010);
        wl_month.setCurrentItem(curMonth - 1);
    }
    protected void showShortToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
