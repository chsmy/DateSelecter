package dateselecter.chs.com.dateselecter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import dateselecter.chs.com.dateselecter.wheelview.OnWheelScrollListener;
import dateselecter.chs.com.dateselecter.wheelview.WheelView;
import dateselecter.chs.com.dateselecter.wheelview.adapter.NumericWheelAdapter;

public class MainActivity extends AppCompatActivity {
    private PopupWindow popupWindow;

    private WheelView wl_start_year;
    private WheelView wl_start_month;
    private WheelView wl_start_day;
    private WheelView wl_end_year;
    private WheelView wl_end_month;
    private WheelView wl_end_day;

    private RelativeLayout rl_main;
    private TextView tv_start_time,tv_end_time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rl_main = (RelativeLayout) findViewById(R.id.rl_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop();
                makeWindowDark();
            }
        });
    }

    private void showPop() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupWindowView = inflater.inflate(R.layout.pop_term_select, null);
        tv_start_time= (TextView) popupWindowView.findViewById(R.id.tv_start_time);
        tv_end_time= (TextView) popupWindowView.findViewById(R.id.tv_end_time);
        popupWindow = new PopupWindow(popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
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
        /*****************开始时间***********************/
        wl_start_year = (WheelView) view.findViewById(R.id.wl_start_year);
        wl_start_month = (WheelView) view.findViewById(R.id.wl_start_month);
        wl_start_day = (WheelView) view.findViewById(R.id.wl_start_day);

        NumericWheelAdapter numericWheelAdapterStart1=new NumericWheelAdapter(this,1950, 2100);
        numericWheelAdapterStart1.setLabel(" ");
        wl_start_year.setViewAdapter(numericWheelAdapterStart1);
        numericWheelAdapterStart1.setTextColor(R.color.black);
        numericWheelAdapterStart1.setTextSize(20);
        wl_start_year.setCyclic(true);//是否可循环滑动
        wl_start_year.addScrollingListener(startScrollListener);

        NumericWheelAdapter numericWheelAdapterStart2=new NumericWheelAdapter(this,1, 12, "%02d");
        numericWheelAdapterStart2.setLabel(" ");
        wl_start_month.setViewAdapter(numericWheelAdapterStart2);
        numericWheelAdapterStart2.setTextColor(R.color.black);
        numericWheelAdapterStart2.setTextSize(20);
        wl_start_month.setCyclic(true);
        wl_start_month.addScrollingListener(startScrollListener);

        NumericWheelAdapter numericWheelAdapterStart3=new NumericWheelAdapter(this,1,getDay(curYear,curMonth), "%02d");
        numericWheelAdapterStart3.setLabel(" ");
        wl_start_day.setViewAdapter(numericWheelAdapterStart3);
        numericWheelAdapterStart3.setTextColor(R.color.black);
        numericWheelAdapterStart3.setTextSize(20);
        wl_start_day.setCyclic(true);
        wl_start_day.addScrollingListener(startScrollListener);
        /********************结束时间*********************/
        wl_end_year = (WheelView) view.findViewById(R.id.wl_end_year);
        wl_end_month = (WheelView) view.findViewById(R.id.wl_end_month);
        wl_end_day = (WheelView) view.findViewById(R.id.wl_end_day);

        NumericWheelAdapter numericWheelAdapterEnd1=new NumericWheelAdapter(this,1950, 2100);
        numericWheelAdapterEnd1.setLabel(" ");
        wl_end_year.setViewAdapter(numericWheelAdapterEnd1);
        numericWheelAdapterEnd1.setTextColor(R.color.black);
        numericWheelAdapterEnd1.setTextSize(20);
        wl_end_year.setCyclic(true);//是否可循环滑动
        wl_end_year.addScrollingListener(endScrollListener);

        NumericWheelAdapter numericWheelAdapterEnd2=new NumericWheelAdapter(this,1, 12, "%02d");
        numericWheelAdapterEnd2.setLabel(" ");
        wl_end_month.setViewAdapter(numericWheelAdapterEnd2);
        numericWheelAdapterEnd2.setTextColor(R.color.black);
        numericWheelAdapterEnd2.setTextSize(20);
        wl_end_month.setCyclic(true);
        wl_end_month.addScrollingListener(endScrollListener);

        NumericWheelAdapter numericWheelAdapterEnd3=new NumericWheelAdapter(this,1,getDay(curYear,curMonth), "%02d");
        numericWheelAdapterEnd3.setLabel(" ");
        wl_end_day.setViewAdapter(numericWheelAdapterEnd3);
        numericWheelAdapterEnd3.setTextColor(R.color.black);
        numericWheelAdapterEnd3.setTextSize(20);
        wl_end_day.setCyclic(true);
        wl_end_day.addScrollingListener(endScrollListener);

        wl_start_year.setCurrentItem(curYear - 1950);
        wl_start_month.setCurrentItem(curMonth-1);
        wl_start_day.setCurrentItem(curDate-1);

        wl_end_year.setCurrentItem(curYear - 1950);
        wl_end_month.setCurrentItem(curMonth - 1);
        wl_end_day.setCurrentItem(curDate-1);
    }
    OnWheelScrollListener startScrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {
        }
        @Override
        public void onScrollingFinished(WheelView wheel) {
            int n_year = wl_start_year.getCurrentItem() + 1950;//年
            int n_month = wl_start_month.getCurrentItem() + 1;//月
            int n_day = wl_start_day.getCurrentItem() + 1;//日
            tv_start_time.setText(n_year+"/"+n_month+"/"+n_day);
        }
    };
    OnWheelScrollListener endScrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {
        }
        @Override
        public void onScrollingFinished(WheelView wheel) {
            int n_year = wl_end_year.getCurrentItem() + 1950;//年
            int n_month = wl_end_month.getCurrentItem() + 1;//月
            int n_day = wl_end_day.getCurrentItem() + 1;//日
            tv_end_time.setText(n_year+"/"+n_month+"/"+n_day);
        }
    };

    /**
     * 根据年月获得 这个月总共有几天
     * @param year
     * @param month
     * @return
     */
    public static int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }
    /**
     * 让屏幕变暗
     */
    private void makeWindowDark(){
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
    }
    /**
     * 让屏幕变亮
     */
    private void makeWindowLight(){
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 1f;
        window.setAttributes(lp);
    }
}
