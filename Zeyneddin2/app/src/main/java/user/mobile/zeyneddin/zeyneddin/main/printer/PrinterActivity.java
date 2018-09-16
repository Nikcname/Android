package user.mobile.zeyneddin.zeyneddin.main.printer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zj.btsdk.BluetoothService;
import com.zj.btsdk.PrintPic;

import user.mobile.zeyneddin.zeyneddin.R;

public class PrinterActivity extends AppCompatActivity {

    BluetoothService mService = null;
    BluetoothDevice con_dev = null;

    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CONNECT_DEVICE = 1;

    /*
        Ненухжная хрень:
     */
    Button btnSendDraw;
    Button btnSearch;
    Button btnSend;
    Button btnClose;
    EditText edtContext;
    EditText edtPrint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);

        mService = new BluetoothService(this, mHandler);

        if (mService.isAvailable() == false){
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
        }

        btnSendDraw = findViewById(R.id.btn_test);
        btnSearch = findViewById(R.id.btnSearch);
        btnSend = findViewById(R.id.btnSend);
        btnClose = findViewById(R.id.btnClose);
        edtContext = findViewById(R.id.txt_content);
        btnClose.setEnabled(false);
        btnSend.setEnabled(false);
        btnSendDraw.setEnabled(false);
    }

    @Override
    public void onStart(){
        super.onStart();

        if (mService.isBTopen() == false){
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mService != null){
            mService.stop();
        }
        mService = null;
    }

    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1){
                        case BluetoothService.STATE_CONNECTED:
                            Toast.makeText(getApplicationContext(), "Connect successful", Toast.LENGTH_SHORT).show();
                            btnClose.setEnabled(true);
                            btnSend.setEnabled(true);
                            btnSendDraw.setEnabled(true);
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            Toast.makeText(getApplicationContext(), "Connecting...", Toast.LENGTH_SHORT).show();
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
//                            Toast.makeText(getApplicationContext(), "No state", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_CONNECTION_LOST:
                    Toast.makeText(getApplicationContext(), "Device connection was lost", Toast.LENGTH_SHORT).show();
                    btnClose.setEnabled(false);
                    btnSend.setEnabled(false);
                    btnSendDraw.setEnabled(false);
                    break;
                case BluetoothService.MESSAGE_UNABLE_CONNECT:
                    Toast.makeText(getApplicationContext(), "Unable to connect device", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK){
                    Toast.makeText(this, "Bluetooth open successful", Toast.LENGTH_LONG).show();
                } else {
                    finish();
                }
                break;
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK){
                    String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    con_dev = mService.getDevByMac(address);
                    mService.connect(con_dev);
                }
                break;
        }
    }

    @SuppressLint("SdCardPath")
    private void printImage(){
        byte[] sendData = null;
        PrintPic pg = new PrintPic();
        pg.initCanvas(384);
        pg.initPaint();
        pg.drawImage(0,0,"/mnt/sdcard/icon.jpg");
        sendData = pg.printDraw();
        mService.write(sendData);
    }

    public void onBtnSearch(View view) {
        Intent serverIntent = new Intent(PrinterActivity.this,DeviceListActivity.class);      //��������һ����Ļ
        startActivityForResult(serverIntent,REQUEST_CONNECT_DEVICE);
    }

    public void onBtnSend(View view) {
        String msg = edtContext.getText().toString();
        if( msg.length() > 0 ){
            mService.sendMessage(msg+"\n", "GBK");
        }
    }

    public void onBtnClose(View view) {
        mService.stop();
    }

    public void onBtnTest(View view) {
        String msg = "";
        String lang = "en";
        //printImage();

        byte[] cmd = new byte[3];
        cmd[0] = 0x1b;
        cmd[1] = 0x21;
        if ((lang.compareTo("en")) == 0) {
            cmd[2] |= 0x10;
            mService.write(cmd);
            mService.sendMessage("Congratulations!\n", "GBK");
            cmd[2] &= 0xEF;
            mService.write(cmd);
            msg = "  You have sucessfully created communications between your device and our bluetooth printer.\n\n"
                    + "  the company is a high-tech enterprise which specializes" +
                    " in R&D,manufacturing,marketing of thermal printers and barcode scanners.\n\n";


            mService.sendMessage(msg, "GBK");
        } else if ((lang.compareTo("ch")) == 0) {
            cmd[2] |= 0x10;
            mService.write(cmd);
            mService.sendMessage("Me don't know!\n", "GBK");
            cmd[2] &= 0xEF;
            mService.write(cmd);
            msg = "I don't know!\n";

            mService.sendMessage(msg, "GBK");
        }
    }
}
