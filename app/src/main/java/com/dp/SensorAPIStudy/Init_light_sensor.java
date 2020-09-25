package com.dp.SensorAPIStudy;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Init_light_sensor extends Activity implements SensorEventListener {
  private SensorManager m_sensorManager;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    // 取得感應器管理員的實例
    m_sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    List<Sensor> availableSensor = m_sensorManager
        .getSensorList(Sensor.TYPE_ALL);
    // 取得文字區塊的實例
    TextView sensorList = (TextView) findViewById(R.id.sensor_list_text);
    String outputString = "";
    // 列出這台Android 裝置上的所有感應器
    for (int i = 0; i < availableSensor.size(); i++) {
      Sensor thisSensor = availableSensor.get(i);
      // 列出這個感應器的名稱
      outputString += thisSensor.getName();
      // 列出它的型態
      outputString += printType(thisSensor.getType());
      outputString += ",";
      // 列出它的功耗
      outputString += thisSensor.getPower();
      outputString += "mA\n";

    }
    sensorList.setText(outputString);
  }

  /**
   * 依照感應器的型態, 傳入
   * 
   * @param i
   *          感應器的型態代碼
   * @return 對應的感應器中文型態
   */
  private String printType(int i) {
    switch (i) {
    case 1: // TYPE_ACCELEROMETER
      return "(加速度感應器)";
    case 2: // TYPE_MAGNETIC_FIELD
      return "(磁力感應器)";
    case 3: // TYPE_ORIENTATION
      return "(水平垂直方向感應器)";
    case 4: // TYPE_GYROSCOPE
      return "(陀螺儀感應器)";
    case 5: // TYPE_LIGHT
      return "(光度感應器)";
    case 6: // TYPE_PRESSURE
      return "(壓力感應器)";
    case 7: // TYPE_TEMPERATURE
      return "(溫度感應器)";
    case 8: // TYPE_PROXIMITY
      return "(遠近感應器)";

    }
    return "";
  }

  /**
   * 當畫面一顯現時，就將感應器的聆聽器註冊，並且監控想要的感應器
   */
  @Override
  protected void onResume() {
    super.onResume();
    // 取得光度感應器
    List<Sensor> sensors = m_sensorManager.getSensorList(Sensor.TYPE_LIGHT);
    // 註冊進感應器管理員
    m_sensorManager.registerListener(this, sensors.get(0),
        SensorManager.SENSOR_DELAY_NORMAL);
  }

  /**
   * 當這個Activity一消失就取消註冊這個Listener
   */
  @Override
  protected void onPause() {
    // 取消Listener的註冊
    m_sensorManager.unregisterListener(this);
    super.onPause();
  }

  /**
   * 擷取感應器的數值
   */
  @Override
  public void onSensorChanged(SensorEvent event) {
    // 當感應器是光度感應器
    if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
      float light = event.values[0];
      TextView xText = (TextView) findViewById(R.id.sensor_x);
      xText.setText("光度感測器的值是 = " + light);

    }

  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // TODO Auto-generated method stub

  }

}