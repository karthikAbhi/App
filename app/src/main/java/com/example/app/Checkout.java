package com.example.app;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;

import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_ATTACHED;
import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED;

public class Checkout extends AppCompatActivity {

    private TextView tvtitle, tvdescription, tvcategory, tvprice;
    private ImageView img;
    private Button print;
    private static final String mManufacturerName = "NASH INDUSTRIES (I)PVT.LTD\n\n";
    private static final String mAddress = "Goraguntepalya, Bengaluru\n\n";
    private static final String data = "BILL: 0001\t\tDATE:11/12/2018\n\nPRODUCT NAME\tQTY\tRATE\tAMT\n";
    private static final boolean forceClaim = true;
    private static final int TIMEOUT = 0;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private UsbManager mUsbManager;
    //Objects
    private UsbDevice mDevice;
    private UsbInterface mInterface;
    private UsbEndpoint mEndpoint;
    private UsbDeviceConnection mConnection;
    private PendingIntent mPermissionIntent;

    //General Variables
    private Byte[] mBytes;
    private String mUsbDevice;
    private String mTitle;
    private int mQuantity;
    private double mPrice;
    private double mTotal;
    private double sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        print = findViewById(R.id.print);
        /*tvtitle = findViewById(R.id.txttitle);
        tvdescription = findViewById(R.id.txtDesc);
        tvcategory = findViewById(R.id.txtCat);
        tvprice = findViewById(R.id.priceQty);
        img = findViewById(R.id.bookthumbnail);
        print = findViewById(R.id.print);*/

        /*//Receive Data
        Intent intent1 = getIntent();
        String Title = intent1.getExtras().getString("BookTitle");
        String Description = intent1.getExtras().getString("Description");
        double Price = intent1.getExtras().getDouble("Price");
        int image = intent1.getExtras().getInt("Thumbnail");*/


        /*// Setting values
        tvtitle.setText(Title);
        tvdescription.setText(Description);
        img.setImageResource(image);
        tvprice.setText(Double.toString(Price));

        //Setting Print Text
        mTitle = Title + "\t";
*/
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*SharedPreferences prefs = getSharedPreferences(MainActivity.CHAT_PREFS, 0);
                //HashMap hs = new HashMap<>().getAll();
                Log.d("Memory", "Name = " + prefs.getString(MainActivity.NAME, null));
                Log.d("Memory", "Age = " + prefs.getString(MainActivity.AGE, null));*/
                //getdata();
                transfer(mConnection, mEndpoint);
            }
        });

            Log.d("Print", "Print");

        BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Toast.makeText(getApplicationContext(), "Receiver called", Toast.LENGTH_SHORT).show();
                String action = intent.getAction();
                if (ACTION_USB_PERMISSION.equals(action)) {
                    synchronized (this) {
                        UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                        Toast.makeText(getApplicationContext(), "Receiver called : " + device.getDeviceName() + " " + device.getManufacturerName(), Toast.LENGTH_SHORT).show();

                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            if (device != null) {
                                //call method to set up device communication
                                mInterface = device.getInterface(0);
                                mEndpoint = mInterface.getEndpoint(1);// 0 IN and  1 OUT to printer.
                                mConnection = mUsbManager.openDevice(device);

                                for (int i = 0; i < mInterface.getEndpointCount(); i++) {
                                    Log.i("Printer", "EP: "
                                            + String.format("0x%02X", mInterface.getEndpoint(i)
                                            .getAddress()));

                                    if (mInterface.getEndpoint(i).getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                                        Log.i("Printer", "Bulk Endpoint");
                                        if (mInterface.getEndpoint(i).getDirection() == UsbConstants.USB_DIR_IN) {
//                                        epIN = mInterface.getEndpoint(i);
                                            Log.i("Printer", "input stream found");
                                        } else {
                                            mEndpoint = mInterface.getEndpoint(i);
                                            Log.i("Printer", "outstream found");
                                        }
                                    } else {
                                        Log.i("Printer", "Not Bulk");
                                    }
                                }

                            }
                        } else {
                            Toast.makeText(context, "PERMISSION DENIED FOR THIS DEVICE", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };

        BroadcastReceiver mUsbReceiver1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                Toast.makeText(getApplicationContext(), "Device Disconnected", Toast.LENGTH_SHORT).show();
                mConnection.close();
                finish();
        /*if(UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)){
            UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            if(device != null){
                if(mConnection.releaseInterface(mInterface)){
                    Toast.makeText(getApplicationContext(),"Device Disconnected",Toast.LENGTH_SHORT).show();

                }

            }
        }*/
            }
        };

        BroadcastReceiver mUsbReceiver2 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //String action = intent.getAction();

                Toast.makeText(getApplicationContext(), "Device Connected", Toast.LENGTH_SHORT).show();
            }
        };


        //Intent
        Intent intent = new Intent(ACTION_USB_DEVICE_ATTACHED);

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        mDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

        //Contains all the UsbDevices list in a Hashmap Datastructure
        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        //deviceList = mUsbManager.getDeviceList();

        while (deviceIterator.hasNext()) {
            mDevice = deviceIterator.next();
            if (mDevice.getVendorId() == 12232) {
                Log.d("Connect", "Device Found");
                mUsbDevice += "\n" +
                        "DeviceID: " + mDevice.getDeviceId() + "\n" +
                        "DeviceName: " + mDevice.getDeviceName() + "\n" +
                        "Protocol: " + mDevice.getDeviceProtocol() + "\n" +
                        "Product Name: " + mDevice.getProductName() + "\n" +
                        "Manufacturer Name: " + mDevice.getManufacturerName() + "\n" +
                        "DeviceSubClass: " + mDevice.getDeviceSubclass() + "\n" +
                        "VendorID: " + mDevice.getVendorId() + "\n" +
                        "ProductID: " + mDevice.getProductId() + "\n";
                //mUsbDeviceDescription.setText(mUsbDevice);
            /*
            ((Activity)getApplicationContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                     mUsbDeviceDescription.setText(mUsbDevice);
                }
            });*/
                //mUsbDeviceDescription.setText(mUsbDevice);
                Toast.makeText(getApplicationContext(), "Printer Connected" + mUsbDevice, Toast.LENGTH_SHORT).show();
                mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
                IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
                IntentFilter filterOnConnection = new IntentFilter(ACTION_USB_DEVICE_ATTACHED);
                IntentFilter filterOffConnection = new IntentFilter(ACTION_USB_DEVICE_DETACHED);
                registerReceiver(mUsbReceiver, filter);
                registerReceiver(mUsbReceiver2, filterOnConnection);
                registerReceiver(mUsbReceiver1, filterOffConnection);
                mUsbManager.requestPermission(mDevice, mPermissionIntent);
                break;
            } else {
                Toast.makeText(getApplicationContext(), "Not a Printer", Toast.LENGTH_SHORT).show();
            }

        }
    }



    private void transfer(final UsbDeviceConnection mConnection, final UsbEndpoint mEndpoint) {
            //Book instance = new Book();

            if (mInterface == null) {
                Toast.makeText(this, "INTERFACE IS NULL", Toast.LENGTH_SHORT).show();
            } else if (mConnection == null) {
                Toast.makeText(this, "CONNECTION IS NULL", Toast.LENGTH_SHORT).show();
            } else {

                boolean status = mConnection.claimInterface(mInterface, true);
                Log.i("Printer", "claim status " + status);

                Log.i("Printer", "Before control transfer: ");

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        byte[] cut_paper = {0x0a, 0x1b, 0x69};
                        byte[] cmdDoublehead = {0x1b, 0x21, 0x31};
                        byte[] cmdDoublehead11 = {0x1b, 0x21, 0x20};

                        byte[] cmdnormalfont = {0x1b, 0x21, 0x00};
                        byte[] cmdSpacing100 = {0x1D, 0x4C, 0x00, 0x00}; // third index should be adjusted
                        byte[] cmdSpacing300 = {0x1D, 0x4C, 0x00, 0x00}; // third index should be adjusted
                        byte[] cmdReset = {0x1b, 0x40};
                        byte[] barcode_begin_cmd = {0x1d, 0x28, 0x6b, 0x03, 0x00, 0x30, 0x44, 0x16};
                        byte[] barcode_begin = {0x1d, 0x6b, 0x49, 0x08, 0x67, 0x15, 0x16, 0x17, 0x18, 0x00, 0x19, 0x67, 0x0a};
                        byte[] next_line = {0x0a};
                        byte[] tab_space ={0x09};
                        byte[] esc_3 = {0x1b,0x33,0x28};
                        byte[] hypen = {0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x2d,0x0a};
                        byte[] seperation = {0x0a,0x5f,0x5f, 0x5f,0x5f, 0x5f,0x5f, 0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x5f,0x0a};
                        String Total_word = "\tTOTAL : \t";
                        mConnection.bulkTransfer(mEndpoint, esc_3, esc_3.length, 0);

                        mConnection.bulkTransfer(mEndpoint, cmdDoublehead, cmdDoublehead.length, 0);
                        //mConnection.bulkTransfer(mEndpoint, cmdSpacing100, cmdSpacing100.length, 0);
                        mConnection.bulkTransfer(mEndpoint, mManufacturerName.getBytes(), mManufacturerName.getBytes().length, 0);
                        mConnection.bulkTransfer(mEndpoint, cmdnormalfont, cmdnormalfont.length, 0);
                        mConnection.bulkTransfer(mEndpoint, mAddress.getBytes(), mAddress.getBytes().length, 0);
                        mConnection.bulkTransfer(mEndpoint, data.getBytes(), data.getBytes().length, 0);
                        //mConnection.bulkTransfer(mEndpoint, next_line, next_line.length, 0);
                        //mConnection.bulkTransfer(mEndpoint, barcode_begin_cmd, barcode_begin_cmd.length, 0);
                        //mConnection.bulkTransfer(mEndpoint, barcode_begin, barcode_begin.length, 0);
                        mConnection.bulkTransfer(mEndpoint, esc_3, esc_3.length, 0);
                        sum = 0;
                        for(int i = 0; i<10;i++){

                                Book instance = MainActivity.lstBook.get(i);
                                mTitle = instance.getTitle();
                                mQuantity = MainActivity.code[i];
                                mPrice = instance.getPrice();
                                mTotal = mPrice * mQuantity;
                                Toast.makeText(getApplicationContext(),""+instance.getTitle(),Toast.LENGTH_SHORT).show();

                            if(mQuantity != 0) {
                                sum += mTotal;//+sum;
                                mConnection.bulkTransfer(mEndpoint, mTitle.getBytes(), mTitle.getBytes().length, 0);
                                mConnection.bulkTransfer(mEndpoint, tab_space, tab_space.length, 0);

                                //mConnection.bulkTransfer(mEndpoint,barcode_end,barcode_end.length,0);
                                mConnection.bulkTransfer(mEndpoint, String.valueOf(mQuantity).getBytes(), String.valueOf(mQuantity).getBytes().length, 0);
                                mConnection.bulkTransfer(mEndpoint, tab_space, tab_space.length, 0);

                                //mConnection.bulkTransfer(mEndpoint, cmdnormalfont, cmdnormalfont.length, 0);
                                mConnection.bulkTransfer(mEndpoint, String.valueOf(mPrice).getBytes(), String.valueOf(mPrice).getBytes().length, 0);
                                mConnection.bulkTransfer(mEndpoint, tab_space, tab_space.length, 0);

                                mConnection.bulkTransfer(mEndpoint, String.valueOf(mTotal).getBytes(), String.valueOf(mTotal).getBytes().length, 0);


                                mConnection.bulkTransfer(mEndpoint, next_line, next_line.length, 0);
                            }
                        }
                        mConnection.bulkTransfer(mEndpoint, hypen, hypen.length, 0);
                        mConnection.bulkTransfer(mEndpoint, cmdDoublehead11, cmdDoublehead11.length, 0);

                        mConnection.bulkTransfer(mEndpoint, Total_word.getBytes(), Total_word.getBytes().length, 0);

                        mConnection.bulkTransfer(mEndpoint, String.valueOf(sum).getBytes(), String.valueOf(sum).getBytes().length, 0);

                        mConnection.bulkTransfer(mEndpoint, cmdnormalfont, cmdnormalfont.length, 0);

                        mConnection.bulkTransfer(mEndpoint, seperation, seperation.length, 0);

                        //mConnection.bulkTransfer(mEndpoint, cmdnormalfont, cmdnormalfont.length, 0);
                        mConnection.bulkTransfer(mEndpoint, cut_paper, cut_paper.length, 0);
                    }
                });
                thread.run();

                //finish();
            }
        }

    private void getdata(){

        Book instance = new Book();
        int i = 0;
        for(i=0;i<10;i++){

            instance = MainActivity.lstBook.get(i);
            mTitle = instance.getTitle();
            mQuantity = MainActivity.code[i];
            mPrice = instance.getPrice();
            mTotal = mPrice * mQuantity;
            Toast.makeText(getApplicationContext(),""+instance.getTitle(),Toast.LENGTH_SHORT).show();
            i++;
            transfer(mConnection,mEndpoint);
        }
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);

    }
}

