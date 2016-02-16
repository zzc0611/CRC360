package com.aibao.fragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.aibao.R;
import com.aibao.activitys.buyActivity;
import com.aibao.activitys.orderActivity;
import com.aibao.activitys.settingActivity;
import com.aibao.view.circleImage;
import com.aibao.view.uploadPhotoDialog;

public class centerActivity extends baseFragment implements
		OnCheckedChangeListener, OnClickListener {
	private circleImage ivPhoto;
	private View parenView;
	/** 菜单项 */
	private RadioGroup rg;
	/** 右边菜单项 */
	private LinearLayout cellMenu2;
	private ImageView setting, buy, order;
	// private ViewPager vp;
	// private List<Fragment> fragments;
	private Fragment container;
	private FragmentManager manager;
	/** 开始事务 */
	private FragmentTransaction ft;

	private uploadPhotoDialog dialog;
	private  List<Fragment> fgs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// parenView = inflater.inflate(R.layout.fragment_center, container,
		// false);
		parenView = inflater.inflate(R.layout.fragment_center, null);
		return parenView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// container=manager.findFragmentById(R.id.fgContainer);

	}

	@Override
	protected void initView(Bundle savedInstanceState) {
		View v = this.getView();
		View top = getViewFromView(v, R.id.cellVcrTop);
		ivPhoto = getViewFromView(v, R.id.ivPhoto);
		rg = getViewFromView(top, R.id.cellMenu);
		cellMenu2 = getViewFromView(top, R.id.cellMenu2);
		setting = getViewFromView(cellMenu2, R.id.ivSetting);
		buy = getViewFromView(cellMenu2, R.id.ivBuy);
		order = getViewFromView(cellMenu2, R.id.ivOrder2);

		// vp=getView(R.id.vp);
		// fragments=new ArrayList<Fragment>();
		// fragments.add(new relationFragment());
		// vp.setAdapter(new
		// myVpAdapter(getActivity().getSupportFragmentManager()) );
		dialog = uploadPhotoDialog.getInstance(this);
		manager = this.getFragmentManager();
	}

	@Override
	protected void setListener() {
		ivPhoto.setOnClickListener(this);
		rg.setOnCheckedChangeListener(this);
		setting.setOnClickListener(this);
		buy.setOnClickListener(this);
		order.setOnClickListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rbPhoto:
			// TODO
			refresh(fgs.get(0));
			break;
		case R.id.rbInfo:
			refresh(fgs.get(1));
			break;
		case R.id.rbVCR:
			refresh(fgs.get(2));
			break;
		case R.id.rbRelation:
			refresh(fgs.get(3));
		}
	}

	@Override
	protected void getDatas() {
		fgs = new ArrayList<Fragment>();
		fgs.add(new photoFragment());
		fgs.add(new infoFragment());
		fgs.add(new vcrFragment());
		fgs.add(new relationFragment());
		refresh(fgs.get(0));
	}

	/**
	 * 替换fragment
	 * 
	 * @param fg
	 */
	private void refresh(Fragment fg) {
		ft = manager.beginTransaction();
		ft.replace(R.id.llContainer, fg);
		ft.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivSetting:
			skip(settingActivity.class, false);
			break;
		case R.id.ivBuy:
			skip(buyActivity.class, false);
			break;
		case R.id.ivOrder2:
			skip(orderActivity.class, false);
			break;
		case R.id.ivPhoto:// TODO 添加图片
			dialog.show();
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 0 || data == null)
			return;
		switch (requestCode) {
		case uploadPhotoDialog.photoCamera:// 拍照
		case uploadPhotoDialog.photoZoom:// 选择区域
			dialog.startPhotoZoom(data.getData());
			// startPhotoZoom(data.getData());
			break;
		case uploadPhotoDialog.photoResult:// 处理结果
			Bundle bd = data.getExtras();
			if (bd != null) {
				Bitmap photo = bd.getParcelable("data");// TODO 记录photo
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
				ivPhoto.setImageBitmap(photo);
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}
