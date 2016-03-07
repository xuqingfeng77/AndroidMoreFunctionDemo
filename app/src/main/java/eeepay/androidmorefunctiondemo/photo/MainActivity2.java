package eeepay.androidmorefunctiondemo.photo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.io.File;

import eeepay.androidmorefunctiondemo.R;
import eeepay.androidmorefunctiondemo.photo.util.Bimp;
import eeepay.androidmorefunctiondemo.photo.util.FileUtils;
import eeepay.androidmorefunctiondemo.photo.util.ImageItem;
import eeepay.androidmorefunctiondemo.photo.util.PublicWay;
import eeepay.androidmorefunctiondemo.photo.util.Res;


/**
 * 首页面activity
 *
 * @author xqf update
 */
public class MainActivity2 extends Activity {

    private GridView noScrollgridview;
    private GridAdapter adapter;
    private View parentView;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private CheckBox mBbox;
    public static Bitmap bimap;
    ImageItem takePhoto = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Res.init(this);
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.mipmap.icon_addpic_unfocused);
        PublicWay.activityList.add(this);
        parentView = getLayoutInflater().inflate(R.layout.activity_selectimg2, null);
        setContentView(parentView);
        Init();
    }

    public void Init() {

        pop = new PopupWindow(MainActivity2.this);

        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(LayoutParams.MATCH_PARENT);
        pop.setHeight(LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
//				photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this,
                        AlbumActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });


        takePhoto = new ImageItem();
        takePhoto.setBitmap(BitmapFactory.decodeResource(
                getResources(),
                R.mipmap.a1));
        Bimp.tempSelectBitmap.add(takePhoto);
        takePhoto = new ImageItem();
        takePhoto.setBitmap(BitmapFactory.decodeResource(
                getResources(),
                R.mipmap.a2));
        Bimp.tempSelectBitmap.add(takePhoto);
        takePhoto = new ImageItem();
        takePhoto.setBitmap(BitmapFactory.decodeResource(
                getResources(),
                R.mipmap.a3));
        Bimp.tempSelectBitmap.add(takePhoto);
        takePhoto = new ImageItem();
        takePhoto.setBitmap(BitmapFactory.decodeResource(
                getResources(),
                R.mipmap.a4));
        Bimp.tempSelectBitmap.add(takePhoto);
        takePhoto = new ImageItem();
        takePhoto.setBitmap(BitmapFactory.decodeResource(
                getResources(),
                R.mipmap.a6));
        Bimp.tempSelectBitmap.add(takePhoto);


        takePhoto = new ImageItem();
        takePhoto.setBitmap(BitmapFactory.decodeResource(
                getResources(),
                R.mipmap.a7));
        Bimp.tempSelectBitmap.add(takePhoto);


        mBbox = (CheckBox) findViewById(R.id.cbox);
        mBbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(Bimp.temp2SelectBitmap.size()==2)
                    {
                        Bimp.tempSelectBitmap.add(4,Bimp.temp2SelectBitmap.get(0));
                        Bimp.tempSelectBitmap.add(5,Bimp.temp2SelectBitmap.get(1));
                    }else
                    {
                        takePhoto = new ImageItem();
                        takePhoto.setBitmap(BitmapFactory.decodeResource(
                                getResources(),
                                R.mipmap.a9));
                        Bimp.tempSelectBitmap.add(4, takePhoto);
                        takePhoto = new ImageItem();
                        takePhoto.setBitmap(BitmapFactory.decodeResource(
                                getResources(),
                                R.mipmap.a8));
                        Bimp.tempSelectBitmap.add(5,takePhoto);
                    }
                    Bimp.temp2SelectBitmap.clear();
                    adapter.update();

                } else {
                    Bimp.temp2SelectBitmap.add(Bimp.tempSelectBitmap.get(4));
                    Bimp.temp2SelectBitmap.add(Bimp.tempSelectBitmap.get(5));

                    Bimp.tempSelectBitmap.remove(4);
                    Bimp.tempSelectBitmap.remove(4);
                    adapter.update();
                }
            }
        });
//

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    Log.i("ddddddd", "----------");
                    ll_popup.startAnimation(AnimationUtils.loadAnimation(MainActivity2.this, R.anim.activity_translate_in));
                    pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
//					Intent intent = new Intent(MainActivity2.this,
//							GalleryActivity.class);
//					intent.putExtra("position", "1");
//					intent.putExtra("ID", arg2);
//					startActivity(intent);

                    photo(arg2);
                }
            }
        });


    }

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            if (Bimp.tempSelectBitmap.size() == 9) {
                return 9;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.mipmap.icon_addpic_unfocused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
//					while (true)
                    {
//						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
//							Message message = new Message();
//							message.what = 1;
//							handler.sendMessage(message);
//							break;
//						} else {
//							Bimp.max += 1;
//							Message message = new Message();
//							message.what = 1;
//							handler.sendMessage(message);
//						}
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }
            }).start();
        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    private static final int TAKE_PICTURE = 0x000001;
    private static final int CAMERA_WITH_DATA = 3023;
    int position = -1;
    String tempPath;

    public void photo(int pos) {
//		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		startActivityForResult(openCameraIntent, TAKE_PICTURE);
        String fileName = String.valueOf(System.currentTimeMillis());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(FileUtils.SDPATH + fileName + ".jpg")));// 照相后的图片输入路径，用了这个方法后返回后data就为null
        startActivityForResult(intent, TAKE_PICTURE);
        tempPath = FileUtils.SDPATH + fileName + ".jpg";
        position = pos;


}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {

                    Bimp.tempSelectBitmap.get(position).setBitmap(null);
                    Bimp.tempSelectBitmap.get(position).setImagePath(tempPath);

                }
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for (int i = 0; i < PublicWay.activityList.size(); i++) {
                if (null != PublicWay.activityList.get(i)) {
                    PublicWay.activityList.get(i).finish();
                }
            }
            System.exit(0);
        }
        return true;
    }

}

