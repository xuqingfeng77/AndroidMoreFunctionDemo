package eeepay.androidmorefunctiondemo.phototwo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import eeepay.androidmorefunctiondemo.R;

//默认的相机为横平，所以Activity设置为横屏，拍出的照片才正确
public class ActivityCapture extends Activity implements
        View.OnClickListener, CaptureSensorsObserver.RefocuseListener {
    private ImageView bnCapture;

    private FrameLayout framelayoutPreview;
    private CameraPreview preview;
    private CameraCropBorderView cropBorderView;
    private Camera camera;
    private PictureCallback pictureCallBack;
    private Camera.AutoFocusCallback focusCallback;
    private CaptureSensorsObserver observer;
    private View focuseView;

    private int currentCameraId;
    private int frontCameraId;
    private boolean _isCapturing;

    CaptureOrientationEventListener _orientationEventListener;
    private int _rotation;

    public static final int kPhotoMaxSaveSideLen = 1600;
    public static final String kPhotoPath = "photo_path";
    String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        observer = new CaptureSensorsObserver(this);
        _orientationEventListener = new CaptureOrientationEventListener(this);
        super.onCreate(savedInstanceState);
        path = FileUtils.getFilePath(System.currentTimeMillis() + ".png");
//		path = getIntent().getStringExtra(kPhotoPath);
        setContentView(R.layout.activity_capture);
        getViews();
        initViews();
        setListeners();
        setupDevice();
    }

    @Override
    protected void onDestroy() {
        if (null != observer) {
            observer.setRefocuseListener(null);
            observer = null;
        }
        _orientationEventListener = null;

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera(); // release the camera immediately on pause event

        observer.stop();
        _orientationEventListener.disable();
    }

    @Override
    protected void onResume() {
        super.onResume();
        openCamera();
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release(); // release the camera for other applications
            camera = null;
        }

        if (null != preview) {
            framelayoutPreview.removeAllViews();
            preview = null;
        }
    }

    protected void getViews() {
        bnCapture = (ImageView) findViewById(R.id.bnCapture);
        framelayoutPreview = (FrameLayout) findViewById(R.id.cameraPreview);
        focuseView = findViewById(R.id.viewFocuse);
    }

    protected void initViews() {
        bnCapture.setRotation(-90);
    }

    protected void setListeners() {
        bnCapture.setOnClickListener(this);
        observer.setRefocuseListener(this);
        pictureCallBack = new PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                _isCapturing = false;
                Bitmap bitmap = null;
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeByteArray(data, 0, data.length, options);
                    options.inJustDecodeBounds = false;
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    //此处就把图片压缩了
                    options.inSampleSize = Math.max(options.outWidth
                            / kPhotoMaxSaveSideLen, options.outHeight
                            / kPhotoMaxSaveSideLen);
                    bitmap = BitmapUtil.decodeByteArrayUnthrow(data, options);
                    if (null == bitmap) {
                        options.inSampleSize = Math.max(2, options.inSampleSize * 2);
                        bitmap = BitmapUtil.decodeByteArrayUnthrow(data, options);
                    }
                } catch (Throwable e) {
                }
                if (null == bitmap) {
                    Toast.makeText(ActivityCapture.this, "内存不足，保存照片失败！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bitmap addBitmap = BitmapUtil.rotateAndScale(bitmap, _rotation, kPhotoMaxSaveSideLen, true);
                Bitmap finalBitmap = cropPhotoImage(addBitmap);
                File photoFile = new File(path);
                boolean successful = BitmapUtil.saveBitmap2file(finalBitmap, photoFile, Bitmap.CompressFormat.JPEG, 100);
                while (!successful) {
                    successful = BitmapUtil.saveBitmap2file(finalBitmap, photoFile, Bitmap.CompressFormat.JPEG, 100);
                }
                if (finalBitmap != null && !finalBitmap.isRecycled()) {
                    addBitmap.recycle();
                }
                Intent intent = new Intent();
                intent.putExtra(kPhotoPath, photoFile.getAbsolutePath());
                ActivityCapture.this.setResult(RESULT_OK, intent);
                ActivityCapture.this.finish();
            }
        };
        focusCallback = new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean successed, Camera camera) {
                focuseView.setVisibility(View.INVISIBLE);
            }
        };
    }

    //根据拍照的图片来剪裁
    private Bitmap cropPhotoImage(Bitmap bmp) {
        int dw = bmp.getWidth();
        int dh = bmp.getHeight();
        int height;
        int width;
        int left;
        int top;
        int right;
        int bottom;
        if (dh > dw) {//图片竖直方向
            //切图片时按照竖屏来计算
            height = getWindowManager().getDefaultDisplay().getWidth();
            width = getWindowManager().getDefaultDisplay().getHeight();
            left = (width - cropBorderView.getRect().height()) / 2;
            top = (height - cropBorderView.getRect().width()) / 2;
            right = left + cropBorderView.getRect().height();
            bottom = top + cropBorderView.getRect().width();
        } else {//图片是水平方向
            //切图片时按照横屏来计算
            width = getWindowManager().getDefaultDisplay().getWidth();
            height = getWindowManager().getDefaultDisplay().getHeight();
            left = (width - cropBorderView.getRect().width()) / 2;
            top = (height - cropBorderView.getRect().height()) / 2;
            right = left + cropBorderView.getRect().width();
            bottom = top + cropBorderView.getRect().height();
        }
        Rect rect = new Rect();
        rect.set(left, top, right, bottom);
        float scale = 1.0f;
        // 如果图片的宽或者高大于屏幕，则缩放至屏幕的宽或者高
        if (dw > width && dh <= height) {
            scale = width * 1.0f / dw;
        }
        if (dh > height && dw <= width) {
            scale = height * 1.0f / dh;
        }
        // 如果宽和高都大于屏幕，则让其按按比例适应屏幕大小
        if (dw > width && dh > height) {
            scale = Math.max(width * 1.0f / dw, height * 1.0f / dh);
        }
        //如果图片的宽度和高度都小于屏幕的宽度和高度，则放大至屏幕大小
        if (dw < width && dh < height) {
            scale = width * 1.0f / dw;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        try {
            Bitmap b2 = Bitmap.createBitmap(bmp, 0, 0, dw, dh, matrix, true);
            if (null != b2 && bmp != b2) {
                bmp.recycle();
                bmp = b2;
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        try {
            Bitmap b3 = Bitmap.createBitmap(bmp, rect.left, rect.top, rect.width(), rect.height());
            if (null != b3 && bmp != b3) {
                bmp.recycle();
                bmp = b3;
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        //将图片压缩至640*640
//		try {
//			Bitmap b4 = Bitmap.createScaledBitmap(bmp, 640, 640, false);
//			if (null != b4 && bmp != b4) {
//				bmp.recycle();
//				bmp = b4;
//			}
//		} catch (OutOfMemoryError e) {
//			e.printStackTrace();
//		}
        return bmp;
    }

    private void setupDevice() {
        if (android.os.Build.VERSION.SDK_INT > 8) {
            int cameraCount = Camera.getNumberOfCameras();

            if (cameraCount < 1) {
                Toast.makeText(this, "你的设备木有摄像头。。。", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            currentCameraId = 0;
            frontCameraId = findFrontFacingCamera();
        }
    }

    private void openCamera() {
        if (android.os.Build.VERSION.SDK_INT > 8) {
            try {
                camera = Camera.open(currentCameraId);
            } catch (Exception e) {
                Toast.makeText(this, "摄像头打开失败", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            setCameraDisplayOrientation(this, 0, camera);
        } else {
            try {
                camera = Camera.open();
            } catch (Exception e) {
                Toast.makeText(this, "摄像头打开失败", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }

        Camera.Parameters camParmeters = camera.getParameters();
        List<Size> sizes = camParmeters.getSupportedPreviewSizes();

        preview = new CameraPreview(this, camera);
        cropBorderView = new CameraCropBorderView(this);
        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        framelayoutPreview.addView(preview, params1);
        framelayoutPreview.addView(cropBorderView, params2);
        observer.start();
        _orientationEventListener.enable();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bnCapture:
                path = FileUtils.getFilePath(System.currentTimeMillis() + ".png");
                bnCaptureClicked();
                break;
        }
    }

    private void bnCaptureClicked() {
        if (_isCapturing) {
            return;
        }
        _isCapturing = true;
        focuseView.setVisibility(View.INVISIBLE);

        try {
            camera.takePicture(null, null, pictureCallBack);
        } catch (RuntimeException e) {
            e.printStackTrace();
            _isCapturing = false;
        }
    }

    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera mCamera;

        public CameraPreview(Context context, Camera camera) {
            super(context);
            mCamera = camera;
            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (Exception e) {
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            if (camera != null) {
                camera.setPreviewCallback(null);
                camera.stopPreview();// 停止预览
                camera.release(); // 释放摄像头资源
                camera = null;
            }
        }

        private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
            final double ASPECT_TOLERANCE = 0.05;
            double targetRatio = (double) w / h;
            if (sizes == null)
                return null;

            Size optimalSize = null;
            double minDiff = Double.MAX_VALUE;

            int targetHeight = h;

            for (Size size : sizes) {
                double ratio = (double) size.width / size.height;
                if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
                    continue;
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }

            if (optimalSize == null) {
                minDiff = Double.MAX_VALUE;
                for (Size size : sizes) {
                    if (Math.abs(size.height - targetHeight) < minDiff) {
                        optimalSize = size;
                        minDiff = Math.abs(size.height - targetHeight);
                    }
                }
            }

            return optimalSize;
        }

        private Size getOptimalPictureSize(List<Size> sizes, double targetRatio) {
            final double ASPECT_TOLERANCE = 0.05;

            if (sizes == null)
                return null;

            Size optimalSize = null;
            int optimalSideLen = 0;
            double optimalDiffRatio = Double.MAX_VALUE;

            for (Size size : sizes) {

                int sideLen = Math.max(size.width, size.height);
                boolean select = false;
                if (sideLen < kPhotoMaxSaveSideLen) {
                    if (0 == optimalSideLen || sideLen > optimalSideLen) {
                        select = true;
                    }
                } else {
                    if (kPhotoMaxSaveSideLen > optimalSideLen) {
                        select = true;
                    } else {
                        double diffRatio = Math.abs((double) size.width / size.height - targetRatio);
                        if (diffRatio + ASPECT_TOLERANCE < optimalDiffRatio) {
                            select = true;
                        } else if (diffRatio < optimalDiffRatio + ASPECT_TOLERANCE && sideLen < optimalSideLen) {
                            select = true;
                        }
                    }
                }

                if (select) {
                    optimalSize = size;
                    optimalSideLen = sideLen;
                    optimalDiffRatio = Math.abs((double) size.width / size.height - targetRatio);
                }
            }

            return optimalSize;
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            if (mHolder.getSurface() == null) {
                return;
            }

            try {
                mCamera.stopPreview();
            } catch (Exception e) {
            }

            try {
                Camera.Parameters parameters = mCamera.getParameters();
                List<Size> sizes = parameters.getSupportedPreviewSizes();
                Size optimalSize = getOptimalPreviewSize(sizes, w, h);
                parameters.setPreviewSize(optimalSize.width, optimalSize.height);
                double targetRatio = (double) w / h;
                sizes = parameters.getSupportedPictureSizes();
                optimalSize = getOptimalPictureSize(sizes, targetRatio);
                parameters.setPictureSize(optimalSize.width, optimalSize.height);
                parameters.setRotation(0);
                mCamera.setParameters(parameters);
            } catch (Exception e) {
            }
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
            } catch (Exception e) {
            }
        }
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    private static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }

        camera.setDisplayOrientation(result);
    }

    @Override
    public void needFocuse() {
        if (null == camera || _isCapturing) {
            return;
        }

        camera.cancelAutoFocus();
        try {
            camera.autoFocus(focusCallback);
        } catch (Exception e) {
            return;
        }

        if (View.INVISIBLE == focuseView.getVisibility()) {
            focuseView.setVisibility(View.VISIBLE);
            focuseView.getParent().requestTransparentRegion(preview);
        }
    }

    //相机旋转监听的类，最后保存图片时用到
    private class CaptureOrientationEventListener extends OrientationEventListener {
        public CaptureOrientationEventListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (null == camera)
                return;
            if (orientation == ORIENTATION_UNKNOWN)
                return;

            orientation = (orientation + 45) / 90 * 90;
            if (android.os.Build.VERSION.SDK_INT <= 8) {
                _rotation = (90 + orientation) % 360;
                return;
            }

            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(currentCameraId, info);

            if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
                _rotation = (info.orientation - orientation + 360) % 360;
            } else { // back-facing camera
                _rotation = (info.orientation + orientation) % 360;
            }
        }
    }
}
