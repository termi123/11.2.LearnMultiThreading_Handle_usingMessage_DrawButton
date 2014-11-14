package tranduythanh.com;

import java.util.concurrent.atomic.AtomicBoolean;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity {

	Handler handlerMain;
	AtomicBoolean atomic=null;
	LinearLayout layoutdevebutton;
	Button btnOk;
	EditText edtOk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//lấy LinearLayout chứa Button ra
		layoutdevebutton=(LinearLayout) findViewById(R.id.layout_draw_button);
		btnOk=(Button) findViewById(R.id.btnDrawButton);
		edtOk=(EditText) findViewById(R.id.editNumber);
		handlerMain=new Handler()
		{
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				//Nhận nhãn của Button được gửi về từ tiến trình con
				String nhan_button=msg.obj.toString();
				//khởi tạo 1 Button
				Button b=new Button(MainActivity.this);
				//thiết lập nhãn cho Button
				b.setText(nhan_button);
				//thiết lập kiểu Layout : Width, Height
				LayoutParams params=new 
						LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.WRAP_CONTENT);
				//thiết lập layout cho Button
				b.setLayoutParams(params);
				//đưa Button vào layoutdevebutton
				layoutdevebutton.addView(b);
			}
		};
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				doStart();
			}
		});
	}
	private void doStart()
	{
		atomic=new AtomicBoolean(false);
		final int sobutton=Integer.parseInt(edtOk.getText()+"");
		Thread thCon=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=0;i<sobutton && atomic.get();i++)
				{
					//nghỉ 200 mili second
					SystemClock.sleep(200);
					//lấy message từ Main Thread
					Message msg=handlerMain.obtainMessage();
					//gán dữ liệu cho msg Mainthread, lưu vào biến obj
					//chú ý ta có thể lưu bất kỳ kiểu dữ liệu nào vào obj
					msg.obj="Button thứ "+i;
					//gửi trả lại message cho Mainthread
					handlerMain.sendMessage(msg);
				}
			}
		});
		atomic.set(true);
		//thực thi tiến trình
		thCon.start();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
