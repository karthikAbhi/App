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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_ATTACHED;
import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED;


public class Book_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView tvtitle, tvdescription, tvcategory, tvprice;
    private ImageView img;
    private Button printButton;
    private Spinner mSpinner;

    private static final String mManufacturerName = "NASH INDUSTRIES (I)PVT.LTD\n\n";
    private static final String mAddress = "Goraguntepalya, Bengaluru\n\n";
    private static final String data = "BILL: 0001\t\tDATE:11/12/2018\n\nPRODUCT NAME\tQTY\tRATE\tAMT\n";
    private static final boolean forceClaim = true;
    private static final int TIMEOUT = 0;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    private UsbManager mUsbManager;

    //Spinner
    //String[] quantity = {"1","2","3"};

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
    private String mQuantity;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_);

        tvtitle = findViewById(R.id.txttitle);
        tvdescription = findViewById(R.id.txtDesc);
        tvcategory = findViewById(R.id.txtCat);
        tvprice = findViewById(R.id.priceQty);
        img = findViewById(R.id.bookthumbnail);
        printButton = findViewById(R.id.printButton);

        //Receive Data
        Intent intent1 = getIntent();
        String Title = intent1.getExtras().getString("BookTitle");
        String Description = intent1.getExtras().getString("Description");
        double Price = intent1.getExtras().getDouble("Price");
        int image = intent1.getExtras().getInt("Thumbnail");


        // Setting values
        tvtitle.setText(Title);
        tvdescription.setText(Description);
        img.setImageResource(image);
        tvprice.setText(Double.toString(Price));

        //Setting Print Text
        mTitle = Title + "\t";

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(MainActivity.CHAT_PREFS, 0);
                //HashMap hs = new HashMap<>().getAll();
                Log.d("Memory", "Name = " + prefs.getString(MainActivity.NAME, null));
                Log.d("Memory", "Age = " + prefs.getString(MainActivity.AGE, null));
                transfer(mConnection, mEndpoint);
            }
        });

        Log.d("Print", "Print");

        mSpinner = findViewById(R.id.spinnerQty);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.quantity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

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
                        "DeviceClass: " + mDevice.getDeviceClass() + " - " + translateDeviceClass(mDevice.getDeviceClass()) + "\n" +
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

    private String translateDeviceClass(int deviceClass) {

        switch (deviceClass) {

            case UsbConstants.USB_CLASS_APP_SPEC:
                return "Application specific USB class";

            case UsbConstants.USB_CLASS_AUDIO:
                return "USB class for audio devices";

            case UsbConstants.USB_CLASS_CDC_DATA:
                return "USB class for CDC devices (communications device class)";

            case UsbConstants.USB_CLASS_COMM:
                return "USB class for communication devices";

            case UsbConstants.USB_CLASS_CONTENT_SEC:
                return "USB class for content security devices";

            case UsbConstants.USB_CLASS_CSCID:
                return "USB class for content smart card devices";

            case UsbConstants.USB_CLASS_HID:
                return "USB class for human interface devices (for example, mice and keyboards)";

            case UsbConstants.USB_CLASS_HUB:
                return "USB class for USB hubs";

            case UsbConstants.USB_CLASS_MASS_STORAGE:
                return "USB class for mass storage devices";

            case UsbConstants.USB_CLASS_MISC:
                return "USB class for wireless miscellaneous devices";

            case UsbConstants.USB_CLASS_PER_INTERFACE:
                return "USB class indicating that the class is determined on a per-interface basis";

            case UsbConstants.USB_CLASS_PHYSICA:
                return "USB class for physical devices";

            case UsbConstants.USB_CLASS_PRINTER:
                return "USB class for printers";

            case UsbConstants.USB_CLASS_STILL_IMAGE:
                return "USB class for still image devices (digital cameras)";

            case UsbConstants.USB_CLASS_VENDOR_SPEC:
                return "Vendor specific USB class";

            case UsbConstants.USB_CLASS_VIDEO:
                return "USB class for video devices";

            case UsbConstants.USB_CLASS_WIRELESS_CONTROLLER:
                return "USB class for wireless controller devices";

            default:
                return "Unknown USB class!";
        }
    }

    private void transfer(final UsbDeviceConnection mConnection, final UsbEndpoint mEndpoint) {


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
                    byte[] cmdnormalfont = {0x1b, 0x21, 0x00};
                    byte[] cmdSpacing100 = {0x1D, 0x4C, 0x00, 0x00}; // third index should be adjusted
                    byte[] cmdSpacing300 = {0x1D, 0x4C, 0x00, 0x00}; // third index should be adjusted
                    byte[] cmdReset = {0x1b, 0x40};
                    byte[] barcode_begin_cmd = {0x1d, 0x28, 0x6b, 0x03, 0x00, 0x30, 0x44, 0x16};
                    byte[] barcode_begin = {0x1d, 0x6b, 0x49, 0x08, 0x67, 0x15, 0x16, 0x17, 0x18, 0x00, 0x19, 0x67, 0x0a};
                    byte[] next_line = {0x0a};
                    mConnection.bulkTransfer(mEndpoint, cmdDoublehead, cmdDoublehead.length, 0);
                    //mConnection.bulkTransfer(mEndpoint, cmdSpacing100, cmdSpacing100.length, 0);
                    mConnection.bulkTransfer(mEndpoint, mManufacturerName.getBytes(), mManufacturerName.getBytes().length, 0);
                    mConnection.bulkTransfer(mEndpoint, cmdnormalfont, cmdnormalfont.length, 0);
                    mConnection.bulkTransfer(mEndpoint, mAddress.getBytes(), mAddress.getBytes().length, 0);
                    mConnection.bulkTransfer(mEndpoint, data.getBytes(), data.getBytes().length, 0);
                    //mConnection.bulkTransfer(mEndpoint, next_line, next_line.length, 0);
                    //mConnection.bulkTransfer(mEndpoint, barcode_begin_cmd, barcode_begin_cmd.length, 0);
                    //mConnection.bulkTransfer(mEndpoint, barcode_begin, barcode_begin.length, 0);
                    mConnection.bulkTransfer(mEndpoint, mTitle.getBytes(), mTitle.getBytes().length, 0);
                    //mConnection.bulkTransfer(mEndpoint,barcode_end,barcode_end.length,0);
                    mConnection.bulkTransfer(mEndpoint, mQuantity.getBytes(), mQuantity.getBytes().length, 0);
                    //mConnection.bulkTransfer(mEndpoint, cmdnormalfont, cmdnormalfont.length, 0);
                    mConnection.bulkTransfer(mEndpoint, cut_paper, cut_paper.length, 0);
                }
            });
            thread.run();

            //finish();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Current Selection = " + parent.getItemAtPosition(position), Toast.LENGTH_LONG).show();
        mQuantity = parent.getItemAtPosition(position).toString() + "\t";
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "NO Selection Made", Toast.LENGTH_LONG).show();
    }

}


