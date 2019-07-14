package com.example.hcliq_priya.activity_admin;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hcliq_priya.R;
import com.example.hcliq_priya.service.ApiService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignatureScreen1 extends AppCompatActivity {

    String DIRECTORY = Environment.getExternalStorageDirectory().getPath()+"/UserSignature/";
    String sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    String StoredPath=DIRECTORY+sdf+".png";
    Button buttonClear,buttonSave;
    LinearLayout layout;
    View view;
    signature sign;
    File file;
    Bitmap bitmap;
    AutoCompleteTextView autocompleteTextViewRelation;
    String relationavlbl,ipdClGuardianSgntrPath,ipdClWitness1SgntrPath,ipdClWitness2SgntrPath,savedSignaturePath,doctorName;
    ImageView image;
    EditText editName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_screen1);

        buttonClear = (Button) findViewById(R.id.button_clear);
        buttonSave = (Button) findViewById(R.id.button_Save);
        buttonSave.setEnabled(false);
        layout = (LinearLayout) findViewById(R.id.CanvasLayout);
        sign.setBackgroundColor(Color.WHITE);
        view = layout;
        autocompleteTextViewRelation = (AutoCompleteTextView) findViewById(R.id.edit_relation);
        image = (ImageView)findViewById(R.id.image);
        layout.addView(sign, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        editName = (EditText)findViewById(R.id.edit_name);

        relationavlbl = getIntent().getStringExtra("relationavlbl");
        ipdClGuardianSgntrPath = getIntent().getStringExtra("ipdClGuardianSgntrPath");
        ipdClWitness1SgntrPath = getIntent().getStringExtra("ipdClWitness1SgntrPath");
        ipdClWitness2SgntrPath = getIntent().getStringExtra("ipdClWitness2SgntrPath");
        savedSignaturePath = getIntent().getStringExtra("savedSignaturePath");
        doctorName = getIntent().getStringExtra("doctorName");

        if(relationavlbl.equals("GuardianSign")){
            autocompleteTextViewRelation.setVisibility(View.VISIBLE);
            Picasso.with(getApplicationContext()).load(ipdClGuardianSgntrPath).fit().into(image);
        } else if(relationavlbl.equals("Witness1Sign")){
            autocompleteTextViewRelation.setVisibility(View.GONE);
            Picasso.with(getApplicationContext()).load(ipdClWitness1SgntrPath).fit().into(image);
        } else if(relationavlbl.equals("Witness2Sign")){
            autocompleteTextViewRelation.setVisibility(View.GONE);
            Picasso.with(getApplicationContext()).load(ipdClWitness2SgntrPath).fit().into(image);
        } else if(relationavlbl.equals("DoctorSigned")){
            autocompleteTextViewRelation.setVisibility(View.GONE);
            Picasso.with(getApplicationContext()).load(savedSignaturePath).fit().into(image);
            editName.setText(doctorName);
        } else if(relationavlbl.equals("DoctorSign")){
            autocompleteTextViewRelation.setVisibility(View.GONE);
            editName.setText(doctorName);
        } else {
            autocompleteTextViewRelation.setVisibility(View.GONE);
        }

        file = new File(DIRECTORY);
         if(!file.exists()){
              file.mkdir();
          }

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("log_tag","panel cleared");
                sign.clear();
                buttonSave.setEnabled(false);
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("log_tag", "Panel Saved");
                if (Build.VERSION.SDK_INT >= 23) {
                    isStoragePermissionGranted();
                    } else {
                    view.setDrawingCacheEnabled(true);
                    Toast.makeText(SignatureScreen1.this, "Save called directly", Toast.LENGTH_SHORT).show();
                    sign.save(view,StoredPath);
                    recreate();
                }
            }
        });
    }

    public boolean isStoragePermissionGranted(){
        Toast.makeText(this, "is storage function called", Toast.LENGTH_SHORT).show();
        if(Build.VERSION.SDK_INT >= 23){
            if(getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                view.setDrawingCacheEnabled(true);
                sign.save(view,StoredPath);
                recreate();
                return true;
            }
            else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        Toast.makeText(SignatureScreen1.this, "Function called", Toast.LENGTH_SHORT).show();
        if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            view.setDrawingCacheEnabled(true);
            sign.save(view,StoredPath);

            recreate();
        }
        else {
            Toast.makeText(this, "The app was not allowed to write to your storage.Hence,it cannot function properly.Please consider granting this permission", Toast.LENGTH_SHORT).show();
        }
    }

    public class signature extends View {
        private static final float strokeWidth = 5f;
        private static final float halfStrokeWidth = strokeWidth/2;
        private Paint paint = new Paint();
        private Path path = new Path();
        private float lastTouchX,lastTouchY;
        private final RectF rectF = new RectF();
        public signature(Context context, AttributeSet attributeSet){
            super(context,attributeSet);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(strokeWidth );
        }

        public void save(View v,String StoredPath){
            Toast.makeText(SignatureScreen1.this, "Save function called", Toast.LENGTH_SHORT).show();
            Log.v("log_tag","Width"+v.getWidth());
            Log.v("log_tag","Height"+v.getHeight());
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(layout.getWidth(), layout.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmap);
            try {
               FileOutputStream outStream = new FileOutputStream(StoredPath);
                v.draw(canvas);
               /* final AlertDialog.Builder builder = new AlertDialog.Builder(SignatureScreen1.this);
                final AlertDialog alertDialog = builder.create();
                builder.setMessage("Save this signature");
                builder.setNegativeButton("For future references", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    //save Api
                    }
                });
                builder.setPositiveButton("For this consent letter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //upload
                    }
                });
                builder.show();*/

                Toast.makeText(SignatureScreen1.this, ""+StoredPath, Toast.LENGTH_SHORT).show();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90,outStream);
                //Intent i = new Intent(SignatureScreen1.this, SignatureScreen.class);
                //i.putExtra("imagePath",StoredPath);
                //st//artActivity(i);
                //finish();
                 outStream.flush();
                 outStream.close();
            } catch (Exception e) {
                Toast.makeText(SignatureScreen1.this, "Not Got"+e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
            buttonSave.setEnabled(false);
            image.setVisibility(View.GONE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            buttonSave.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetRectF(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandRectF(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (rectF.left - halfStrokeWidth),
                    (int) (rectF.top - halfStrokeWidth),
                    (int) (rectF.right + halfStrokeWidth),
                    (int) (rectF.bottom + halfStrokeWidth));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandRectF(float historicalX, float historicalY) {
            if (historicalX < rectF.left) {
                rectF.left = historicalX;
            } else if (historicalX > rectF.right) {
                rectF.right = historicalX;
            }

            if (historicalY < rectF.top) {
                rectF.top = historicalY;
            } else if (historicalY > rectF.bottom) {
                rectF.bottom = historicalY;
            }
        }

        private void resetRectF(float eventX, float eventY) {
            rectF.left = Math.min(lastTouchX, eventX);
            rectF.right = Math.max(lastTouchX, eventX);
            rectF.top = Math.min(lastTouchY, eventY);
            rectF.bottom = Math.max(lastTouchY, eventY);
        }

    }
}


