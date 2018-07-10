package eeepay.androidmorefunctiondemo.keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;


import eeepay.androidmorefunctiondemo.R;

public class VirtualKeyboard extends View {

	private int BUTTON_SIZE = 0;
	private int LETTER_SIZE = 0;
	private Bitmap kbdBitmap;
	private ButtonDrawable btnDrawable;
	private ButtonDrawable btnCtrlDrawable;
	private ButtonDrawable btnSelectedDrawable;
	private ButtonDrawable btnToggleSelectedDrawable;
	private List<VirtualButton> buttons;
	private Paint letterPaint;
	private VirtualButton pressedButton;
	public Paint backgroundPaint;
	private int state;
	private ToggleVirtualButton shiftBtn;
	private VirtualButton enterBtn;
	private VirtualButton symbolsBtn;
	private Canvas kbdCanvas;
	
	EnterBtnClickListener listener;
	
	Context ctx;
	
	public void setListener(EnterBtnClickListener enterBtnClickListener) {
        this.listener = enterBtnClickListener;
    }
	
    public VirtualKeyboard(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		ctx = context;
		
		BUTTON_SIZE = Act.getScreenWidth(context)/5;
		LETTER_SIZE = BUTTON_SIZE/2;
		
		Log4j.debug("VirtualKeyboard is drawing.........");
		
		Resources res = context.getResources();
		btnDrawable = new ButtonDrawable(res, R.drawable.vk_button);
		btnCtrlDrawable = new ButtonDrawable(res, R.drawable.vk_button_ctrl);
		btnSelectedDrawable = new ButtonDrawable(res, R.drawable.vk_button);
//		btnSelectedDrawable = new ButtonDrawable(res, R.drawable.vk_button_selected);//选中的效果
//		btnToggleSelectedDrawable = new ButtonDrawable(res, R.drawable.vk_button_toggle_selected);//默认的效果
		btnToggleSelectedDrawable = new ButtonDrawable(res, R.drawable.vk_button);

		buttons = new ArrayList<VirtualButton>();
		letterPaint = new Paint();
		letterPaint.setAntiAlias(true);
		letterPaint.setColor(0xff000000);
		letterPaint.setTypeface(Typeface.DEFAULT_BOLD);
		letterPaint.setTextSize(LETTER_SIZE);
		backgroundPaint = new Paint();
		backgroundPaint.setColor(0xffffffff);
		backgroundPaint.setStyle(Style.FILL);
		
		pressedButton = null;
		state = 0;
		
	}
	
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int w = MeasureSpec.getSize(widthMeasureSpec);
//		int h = MeasureSpec.getSize(heightMeasureSpec);
//		
//		Log4j.debug("w: "+w);//800
//		Log4j.debug("h: "+h);//480
//		Log4j.debug("BUTTON_SIZE: "+BUTTON_SIZE);//64
//		
//		createBitmap(2 * w + BUTTON_SIZE, 2 * h + BUTTON_SIZE);
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//	}
	
	private void createBitmap(int w, int h) {
		kbdBitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		kbdCanvas = new Canvas(kbdBitmap);
		Resources res = getResources();
		VirtualButton vButton;
		
//		String packageName = R.class.getPackage().getName();
		String packageName = ctx.getPackageName();

		int i=0;
		Random random=new Random();
		i=random.nextInt(6);
		XmlPullParser xpp = getResources().getXml(R.xml.layout+i);
		try {
			while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
				if (xpp.getEventType() == XmlPullParser.START_TAG) {
					String name = xpp.getName();
					if (!name.equals("Layout")) { 
						String sX = xpp.getAttributeValue(null, "x");
						float x = 0;
						if (sX != null) {
							x = Float.parseFloat(sX);
						}
						String sY = xpp.getAttributeValue(null, "y");
						float y = 0;
						if (sY != null) {
							y = Float.parseFloat(sY);
						}
						String sWidth = xpp.getAttributeValue(null, "width");
						float width = 1f;
						if (sWidth != null) {
							width = Float.parseFloat(sWidth);
						}
						String sText = xpp.getAttributeValue(null, "text");
						String sDrawable = xpp.getAttributeValue(null, "drawable");
						if (name.equals("ShiftButton")) {
							vButton = shiftBtn = new ToggleVirtualButton(x, y, width, "shift");
						} else
						if (name.equals("BackspaceButton")) {
							vButton = new VirtualButton(x, y, width, "\b\b\b\b");
						} else
						if (name.equals("SymbolsButton")) {
							vButton = symbolsBtn = new ToggleVirtualButton(x, y, width, sText);
						} else
						if (name.equals("EnterButton")) {
							vButton = enterBtn = new ControlVirtualButton(x, y, width, sText);
						} else {
							if (sText.length() < 4) {
								Log.d("VirtualKeyboard", " Button x:" + x + " y:" + y + " has text shorter than 4 characters !!");
								sText = "????";
							}
							vButton = new VirtualButton(x, y, width, sText);
						}
						if (sDrawable != null) {
							int id = res.getIdentifier(sDrawable, null, packageName);
							if (id == 0) {
								throw new RuntimeException("resource: " + sDrawable + " not found");
							}
							vButton.setBitmap(id);
						}
						buttons.add(vButton);
					}
				}
				xpp.next();
			}
		} catch (XmlPullParserException e) {
			Log.d("VirtualKeyboard", "XmlPullParserException", e);
		} catch (IOException e) {
			Log.d("VirtualKeyboard", "IOException", e);
		}
		
		drawLayout();
	}

	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
		
		if(kbdBitmap == null)
		{
			Log4j.debug("w "+w);
			Log4j.debug("h "+h);
			
			createBitmap(2 * w + BUTTON_SIZE, 2 * h + BUTTON_SIZE);
		}
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
	protected void onDraw(Canvas canvas) {
		canvas.scale(0.5f, 0.5f);
		canvas.translate(-BUTTON_SIZE/2, -BUTTON_SIZE/2);
		canvas.drawBitmap(kbdBitmap, 0, 0, null);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();   //action = 0
		if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
			// offset getX & getY by BUTTON_SIZE/4: bitmap padding
			int x = (int) event.getX() + BUTTON_SIZE/4;  //x=29
			int y = (int) event.getY() + BUTTON_SIZE/4;  //y=130
			int xx = x * 2;  //xx=58
			int yy = y * 2;  //yy=260
			boolean in = false;
			for (VirtualButton button : buttons) {
				if (button.contains(xx, yy)) {
					in = true;
					if (pressedButton == button) {//n
						break;
					}
					if (pressedButton != null) {//n
						pressedButton.up(false);
					}
					pressedButton = button;
					pressedButton.down();
					break;
				}
			}
			if (!in && pressedButton != null) {//n
				pressedButton.up(false);
				pressedButton = null;
			}
		} else if (action == MotionEvent.ACTION_UP) {// action=1
			if (pressedButton != null) {
				pressedButton.up(true);
				if (pressedButton.toString().equals(shiftBtn.toString())) {//y
				    Log.d("Test", "shiftBtn is pressed!");
					state ^= 1;
					drawLayout();
				} else if (pressedButton.toString().equals(symbolsBtn.toString())) {//y
					state ^= 2;
					drawLayout();
				} else if (pressedButton.toString().equals(enterBtn.toString())) {//y
					listener.done();
				} else {
					updateEditText(pressedButton.text.charAt(state));
				}
				pressedButton = null;
			}
			pressedButton = null;
		}
		invalidate();
		return true;
	}

	private void drawLayout() {
		kbdCanvas.drawARGB(255, 255, 255, 255);
		for (VirtualButton button : buttons) {
			button.draw(kbdCanvas);
		}
	}

	private void updateEditText(char c) {
		int start = edit.getSelectionStart();
		int end = edit.getSelectionEnd();
		if (start > end) {
			int tmp = end;
			end = start;
			start = tmp;
		}
		Editable text = edit.getText();
		int size =20;
		if(PopupWin.inputTpye==0){
			size =20;
		}else{
			size =6;
		}
		if (c != '\b') {
			if(text.length()<size){
				if(PopupWin.inputTpye==1){
					if((48<=c&&c<=57)){
						text.replace(start, end, String.valueOf(c), 0, 1);
						start++;
					}else{
					}
				}else{
					text.replace(start, end, String.valueOf(c), 0, 1);
					start++;
				}
			}else{
				
			}
		} else {
			if (start == end && start > 0) {
				start--;
			}
			if (start + end == 0) {
				// start of buffer & no selection: just return
				return;
			}
			text.delete(start, end);
		}
		edit.setText(text);
		edit.setSelection(start);
	}

	// tmp
	private EditText edit;
	public void setUp(EditText edit) {
		this.edit = edit;
	}
	// end of tmp

	class VirtualButton {
		private Rect rect;
		protected String text;
		private Bitmap bitmap;
		
		public VirtualButton(float x, float y, String letters) {
			this(x, y, 1, letters);
		}
		public VirtualButton(float x, float y, float w, String letters) {
			int left = (int) (x * BUTTON_SIZE);
			int top = (int) (y * BUTTON_SIZE);
			int right = (int) ((x + w) * BUTTON_SIZE);
			int bottom = (int) ((y + 1) * BUTTON_SIZE);
			this.rect = new Rect(left, top, right, bottom);
			this.rect.offset(BUTTON_SIZE/2, BUTTON_SIZE/2);
			this.text = letters;
		}
		public void setBitmap(int resId) {
			BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(resId);
			bitmap = drawable.getBitmap();
		}
		public Rect getRect() {
			return rect;
		}

		public void up(boolean inside) {
			draw(kbdCanvas, btnDrawable, text.substring(state, state+1));
		}

		public void down() {
			draw(kbdCanvas, null, text.substring(state, state+1));
		}

		public boolean contains(int x, int y) {
			return rect.contains(x, y);
		}

		public void draw(Canvas canvas) {
			draw(canvas, btnDrawable, text.substring(state, state+1));
		}
		
		void draw(Canvas canvas, ButtonDrawable buttonDrawable, String t) {
			canvas.drawRect(rect, backgroundPaint);
			if (buttonDrawable != null) {
				Bitmap bitmap = buttonDrawable.getBitmap(rect);
				canvas.drawBitmap(bitmap, rect.left, rect.top, null);
			} else {
				Bitmap bitmap = btnSelectedDrawable.getBitmap(rect);
				canvas.drawBitmap(bitmap, rect.left, rect.top, null);
			}
			if (bitmap != null) {
				canvas.drawBitmap(bitmap,
						rect.centerX() - bitmap.getWidth() / 2,
						rect.centerY() - bitmap.getHeight() / 2,
						null);
			} else {
				float x = (rect.width() - letterPaint.measureText(t)) / 2;
				FontMetrics fm = letterPaint.getFontMetrics();
				float y = (rect.height() - letterPaint.getTextSize()) / 2 - fm.ascent - fm.descent;
				// TODO fix it
				y += 4;
				canvas.drawText(t, rect.left+x, rect.top+y, letterPaint);
			}
		}
		
        @Override
        public String toString() {
            return "vb:"+text;
        }
		
	}
	
	class ControlVirtualButton extends VirtualButton {
		public ControlVirtualButton(float x, float y, float w, String letters) {
			super(x, y, w, letters);
		}
		public ControlVirtualButton(float x, float y, String letters) {
			super(x, y, letters);
		}
		@Override
		public void draw(Canvas canvas) {
			draw(canvas, btnCtrlDrawable, text);
		}
		public void up(boolean inside) {
			draw(kbdCanvas, btnCtrlDrawable, text);
		}
		public void down() {
			draw(kbdCanvas, null, text);
		}
		@Override
        public String toString() {
            return "cvb:"+text;
        }
	}
	class ToggleVirtualButton extends ControlVirtualButton {
		private boolean down;
		public ToggleVirtualButton(float x, float y, float w, String letters) {
			super(x, y, w, letters);
		}
		public ToggleVirtualButton(float x, float y, String letters) {
			super(x, y, letters);
		}
		@Override
		public void draw(Canvas canvas) {
			draw(canvas, down? btnToggleSelectedDrawable : btnCtrlDrawable, text);
		}
		@Override
		public void up(boolean inside) {
			if (inside) {
				down = !down;
			}
			draw(kbdCanvas, down? btnToggleSelectedDrawable : btnCtrlDrawable, text);
		}
		@Override
        public String toString() {
            return "tvb:"+text;
        }
	}
	
	class ButtonDrawable {
		private Drawable drawable;
		private SparseArray<Bitmap> bitmapCache;
		public ButtonDrawable(Resources res, int id) {
			drawable = res.getDrawable(id);
			bitmapCache = new SparseArray<Bitmap>();
		}
		public Bitmap getBitmap(Rect rect) {
			int w = rect.width();
			int h = rect.height();
			int key = (w << 16) + h;
			Bitmap bitmap = bitmapCache.get(key);
			if (bitmap == null) {
				drawable.setBounds(0, 0, w, h);
				bitmap =  Bitmap.createBitmap(w, h, Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmap);
				drawable.draw(canvas);
				bitmapCache.put(key, bitmap);
			}
			return bitmap;
		}
	}
//	public void setInputPwdType(int inputType){
//		this.inputType = inputType;
//	}
}
