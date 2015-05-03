package com.leleliu008.newton.framework.ui.datatimepicker;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leleliu008.newton.R;
import com.leleliu008.newton.framework.ui.UIUtil;

public class TimePickerDialog {

	private Context mContext;
	private LinearLayout.LayoutParams LP_WW = new LinearLayout.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

	private WheelView yearWheel, monthWheel, dayWheel, hourWheel, minuteWheel,
			secondWheel;
	private static String[] yearContent = null;
	private static String[] monthContent = null;
	private static String[] dayContent = null;
	private static String[] hourContent = null;
	private static String[] minuteContent = null;
	private static String[] secondContent = null;
	private String result = "";
	private EditText editext;

	public TimePickerDialog(Context context) {
		mContext = context;
		initContent();
	}

	private void initContent() {
		yearContent = new String[10];
		for (int i = 0; i < 10; i++)
			yearContent[i] = String.valueOf(i + 2013);

		monthContent = new String[12];
		for (int i = 0; i < 12; i++) {
			monthContent[i] = String.valueOf(i + 1);
			if (monthContent[i].length() < 2) {
				monthContent[i] = "0" + monthContent[i];
			}
		}

		dayContent = new String[31];
		for (int i = 0; i < 31; i++) {
			dayContent[i] = String.valueOf(i + 1);
			if (dayContent[i].length() < 2) {
				dayContent[i] = "0" + dayContent[i];
			}
		}
		hourContent = new String[24];
		for (int i = 0; i < 24; i++) {
			hourContent[i] = String.valueOf(i);
			if (hourContent[i].length() < 2) {
				hourContent[i] = "0" + hourContent[i];
			}
		}

		minuteContent = new String[60];
		for (int i = 0; i < 60; i++) {
			minuteContent[i] = String.valueOf(i);
			if (minuteContent[i].length() < 2) {
				minuteContent[i] = "0" + minuteContent[i];
			}
		}
		secondContent = new String[60];
		for (int i = 0; i < 60; i++) {
			secondContent[i] = String.valueOf(i);
			if (secondContent[i].length() < 2) {
				secondContent[i] = "0" + secondContent[i];
			}
		}
	}

	public void StartDialog() {

		View view = ((LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				R.layout.time_picker, null);

		Calendar calendar = Calendar.getInstance();
		int curYear = calendar.get(Calendar.YEAR);
		int curMonth = calendar.get(Calendar.MONTH) + 1;
		int curDay = calendar.get(Calendar.DAY_OF_MONTH);
		int curHour = calendar.get(Calendar.HOUR_OF_DAY);
		int curMinute = calendar.get(Calendar.MINUTE);
		int curSecond = calendar.get(Calendar.SECOND);

		yearWheel = (WheelView) view.findViewById(R.id.yearwheel);
		monthWheel = (WheelView) view.findViewById(R.id.monthwheel);
		dayWheel = (WheelView) view.findViewById(R.id.daywheel);
		hourWheel = (WheelView) view.findViewById(R.id.hourwheel);
		minuteWheel = (WheelView) view.findViewById(R.id.minutewheel);
		secondWheel = (WheelView) view.findViewById(R.id.secondwheel);

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setView(view);

		yearWheel.setAdapter(new StrericWheelAdapter(yearContent));
		yearWheel.setCurrentItem(curYear - 2013);
		yearWheel.setCyclic(true);
		yearWheel.setInterpolator(new AnticipateOvershootInterpolator());

		monthWheel.setAdapter(new StrericWheelAdapter(monthContent));

		monthWheel.setCurrentItem(curMonth - 1);

		monthWheel.setCyclic(true);
		monthWheel.setInterpolator(new AnticipateOvershootInterpolator());

		dayWheel.setAdapter(new StrericWheelAdapter(dayContent));
		dayWheel.setCurrentItem(curDay - 1);
		dayWheel.setCyclic(true);
		dayWheel.setInterpolator(new AnticipateOvershootInterpolator());

		hourWheel.setAdapter(new StrericWheelAdapter(hourContent));
		hourWheel.setCurrentItem(curHour);
		hourWheel.setCyclic(true);
		hourWheel.setInterpolator(new AnticipateOvershootInterpolator());

		minuteWheel.setAdapter(new StrericWheelAdapter(minuteContent));
		minuteWheel.setCurrentItem(curMinute);
		minuteWheel.setCyclic(true);
		minuteWheel.setInterpolator(new AnticipateOvershootInterpolator());

		secondWheel.setAdapter(new StrericWheelAdapter(secondContent));
		secondWheel.setCurrentItem(curSecond);
		secondWheel.setCyclic(true);
		secondWheel.setInterpolator(new AnticipateOvershootInterpolator());

		builder.setTitle(R.string.select_time);
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						StringBuilder sb = new StringBuilder();
						sb.append(yearWheel.getCurrentItemValue()).append("-")
								.append(monthWheel.getCurrentItemValue())
								.append("-")
								.append(dayWheel.getCurrentItemValue());

						sb.append(" ");
						sb.append(hourWheel.getCurrentItemValue()).append(":")
								.append(minuteWheel.getCurrentItemValue());
						// .append(":").append(secondWheel.getCurrentItemValue())
						result = sb.toString();

						editext.setText(result);

						dialog.cancel();
					}
				});

		builder.show();
	}

	public void setSendMsgForkeyAndDateTimeValue(LinearLayout layout,
			Context context, int bgColur, String key, String value, int id) {
		LinearLayout myLayout = new LinearLayout(context);

		// LinearLayout.LayoutParams LP_FW = new LinearLayout.LayoutParams(
		// LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		myLayout.setBackgroundColor(bgColur);
		myLayout.setOrientation(LinearLayout.HORIZONTAL);// 控件对其方式为垂直，默认为水平

		TextView tv = new TextView(context); // 普通聊天对话
		tv.setBackgroundColor(bgColur);
		tv.setText(key);
		tv.setTextColor(Color.BLACK);
		myLayout.addView(tv);

		editext = new EditText(context);
		editext.setId(id);
		Resources r = context.getResources();
		float a = r.getDimension(R.dimen.viewpager_EditText_With);
		editext.setWidth(UIUtil.dip2px(context, a / 1));
		editext.setHeight(UIUtil.dip2px(context, 35));
		editext.setLayoutParams(LP_WW);
		editext.setFocusable(false);
		editext.setTextColor(Color.BLACK);
		editext.setBackgroundResource(R.drawable.my_edittext);
		myLayout.addView(editext);

		layout.addView(myLayout);

		editext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StartDialog();
			}
		});
	}

}
