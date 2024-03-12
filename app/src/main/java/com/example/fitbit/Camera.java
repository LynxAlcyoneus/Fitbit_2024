package com.example.fitbit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

public class Camera extends AppCompatActivity {
    private static final String TAG = "CameraActivity";
    private TextureView textureView;
    private String cameraId;
    protected CameraDevice cameraDevice;
    protected CameraCaptureSession cameraCaptureSessions;
    protected CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private static final int REQUEST_CAMERA_PERMISSION = 1;

    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;

    public static int hrtratebpm;
    private int mCurrentRollingAverage;
    private int mLastRollingAverage;
    private int mLastLastRollingAverage;
    private long[] mTimeArray;
    private int numCaptures = 0;
    private int mNumBeats = 0;
    private ProgressBar progressBar;
    private TextView tv;
    private boolean isHeartRateCalculated = false;
    private boolean hasDisplayedHeartRate = false; // Added a new flag


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        textureView = findViewById(R.id.texture);
        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);
        mTimeArray = new long[15];
        tv = findViewById(R.id.neechewalatext);

        ImageView heartImageView = findViewById(R.id.heartImageView);
        AnimationDrawable animationDrawable = (AnimationDrawable) heartImageView.getDrawable();
        animationDrawable.start();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(15);
        progressBar.setProgress(0);


        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAndRestartCamera();
            }
        });
    }

    private int calculateProgress() {
        if (numCaptures >= 0 && numCaptures < 15) {
            return numCaptures;
        } else {
            return 15;
        }
    }

    private void resetAndRestartCamera() {
        mTimeArray = new long[15];
        mCurrentRollingAverage = 0;
        mLastRollingAverage = 0;
        mLastLastRollingAverage = 0;
        numCaptures = 0;
        mNumBeats = 0;
        hrtratebpm = 0;
        isHeartRateCalculated = false;

        closeCamera();
        openCamera();
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            Log.d(TAG, "onSurfaceTextureUpdated");
            Bitmap bmp = textureView.getBitmap();
            int width = bmp.getWidth();
            int height = bmp.getHeight();
            int[] pixels = new int[height * width];
            bmp.getPixels(pixels, 0, width, width / 2, height / 2, width / 20, height / 20);
            int sum = 0;
            for (int i = 0; i < height * width; i++) {
                int red = (pixels[i] >> 16) & 0xFF;
                sum = sum + red;
            }
            if (numCaptures == 20) {
                mCurrentRollingAverage = sum;
            } else if (numCaptures > 20 && numCaptures < 49) {
                mCurrentRollingAverage = (mCurrentRollingAverage * (numCaptures - 20) + sum) / (numCaptures - 19);
            } else if (numCaptures >= 49) {
                mCurrentRollingAverage = (mCurrentRollingAverage * 29 + sum) / 30;
                if (mLastRollingAverage > mCurrentRollingAverage && mLastRollingAverage > mLastLastRollingAverage && mNumBeats < 15) {
                    mTimeArray[mNumBeats] = System.currentTimeMillis();
                    mNumBeats++;
                    if (mNumBeats == 15) {
                        calcBPM();
                    }
                }
            }

            // Move the progress bar and percentage update here
            int progress = calculateProgress();
            progressBar.setProgress(progress);

            numCaptures++;
            mLastLastRollingAverage = mLastRollingAverage;
            mLastRollingAverage = mCurrentRollingAverage;
        }
    };

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            Log.e(TAG, "onOpened");
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            if (cameraDevice != null)
                cameraDevice.close();
            cameraDevice = null;
        }
    };

    protected void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    protected void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void calcBPM() {
        int med;
        long[] timedist = new long[14];
        for (int i = 0; i < 14; i++) {
            timedist[i] = mTimeArray[i + 1] - mTimeArray[i];
        }
        Arrays.sort(timedist);
        med = (int) timedist[timedist.length / 2];
        hrtratebpm = 60000 / med;

        isHeartRateCalculated = true;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText("Heart Rate = " + hrtratebpm + " BPM");
                Button resetButton = findViewById(R.id.resetButton);
                resetButton.setVisibility(View.VISIBLE);
                hasDisplayedHeartRate = true; // Set the flag when heart rate is displayed
            }
        });
    }

    protected void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    if (null == cameraDevice) {
                        return;
                    }
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(Camera.this, "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Camera.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    protected void updatePreview() {
        if (null == cameraDevice) {
            Log.e(TAG, "updatePreview error, return");
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        captureRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(Camera.this, "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();
        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    @Override
    protected void onPause() {
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
