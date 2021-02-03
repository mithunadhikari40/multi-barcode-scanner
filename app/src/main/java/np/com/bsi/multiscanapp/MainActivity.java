package np.com.bsi.multiscanapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import np.com.bsi.multiscanapp.constants.AppConstants;

public class MainActivity extends AppCompatActivity {
    private String codeFormat, codeContent;
    private TextView formatTxt, contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formatTxt = findViewById(R.id.scan_format);
        contentTxt = findViewById(R.id.scan_content);
//        final Button scanNowButton = findViewById(R.id.btn_scan_now);
//        scanNowButton.setOnClickListener(this::scanNow);
    }

    public void scanNow(View view) {
//        String tag = view.getTag().toString();
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt(this.getString(R.string.scan_bar_code));
        integrator.setCaptureActivity(TorchOnCaptureActivity.class);
//        integrator.setCaptureActivity(AnyOrientationCaptureActivity.class);
        integrator.setOrientationLocked(false);
        //integrator.setResultDisplayDuration(0);
        //integrator.setWide();  // Wide scanning rectangle, may work better for 1D barcodes
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBarcodeImageEnabled(true);
        // set turn the camera flash on by default
        // integrator.addExtra(appConstants.CAMERA_FLASH_ON,true);
//        if (tag.equals("continuous")) {
            integrator.addExtra(AppConstants.CONTINEUOUS_SCAN, true);
//        }
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                //we have a result
                codeContent = scanningResult.getContents();
                codeFormat = scanningResult.getFormatName();

                // display it on screen
                formatTxt.setText("FORMAT: " + codeFormat);
                contentTxt.setText("CONTENT: " + codeContent);

            } else {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
        super.onActivityResult(requestCode, resultCode, intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}