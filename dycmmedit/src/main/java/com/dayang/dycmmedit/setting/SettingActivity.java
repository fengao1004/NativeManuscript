package com.dayang.dycmmedit.setting;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.adapter.DialogCreateListAdapter;
import com.dayang.dycmmedit.base.BaseActivity;
import com.dayang.dycmmedit.http.BaseObserver;
import com.dayang.dycmmedit.http.RetrofitHelper;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.ResultSaveManuscriptInfo;
import com.dayang.dycmmedit.utils.Constant;
import com.dayang.dycmmedit.utils.PermissionUtil;
import com.dayang.dycmmedit.utils.PublicResource;
import com.dayang.dycmmedit.utils.StatusBarUtil;
import com.dayang.pickmediafile.common.PickFileManager;
import com.dayang.uploadfile.upload.Constants;
import com.dayang.uploadfile.upload.FtpUpload;
import com.dayang.uploadfile.upload.HttpUpload;
import com.dayang.uploadfile.upload.UploadFileThread;
import com.elvishew.xlog.XLog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

public class SettingActivity extends BaseActivity implements View.OnClickListener, MaterialDialog.ListCallback {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_setting_list)
    RecyclerView rv_setting_list;

    @BindView(R.id.iv_tool_title)
    TextView iv_tool_title;

    public ProgressDialog progressDialog;
    private ManuscriptListInfo info;
    private boolean hasPrivilege;
    private MaterialDialog uploadThumbnailDialog;
    public static final int SELECTFILE_FOR_WECHAT_THUMBNAIL_TAKE_PHOTO = 835;
    public static final int SELECTFILE_FOR_WECHAT_THUMBNAIL_CHOOSE_FILE = 839;
    private File imageFile;
    private DialogCreateListAdapter adapter;
    private PermissionUtil permissionUtil;

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarLightMode(this, Color.parseColor("#ffffff"));
        info = (ManuscriptListInfo) getIntent().getSerializableExtra("info");
        hasPrivilege = getIntent().getBooleanExtra("hasPrivilege", false);
        //TODO 能否被编辑
        adapter = new DialogCreateListAdapter(this, info, hasPrivilege);
        adapter.setSaveListener(new DialogCreateListAdapter.SaveListener() {
            @Override
            public void save() {
                SettingActivity.this.save(true);
            }
        });
        adapter.setUpdateListener(new DialogCreateListAdapter.UpdateListener() {
            @Override
            public void updateImage() {
                uploadThumbnailDialog.show();
            }
        });
        adapter.setDelListener(new DialogCreateListAdapter.DelListener() {
            @Override
            public void del() {
                info.weixinlowimage = "";
                save(false);
            }
        });
        rv_setting_list.setItemViewCacheSize(20);
        rv_setting_list.setLayoutManager(new LinearLayoutManager(this));
        rv_setting_list.setAdapter(adapter);
        XLog.i("initView: " + info);

        switch (info.manuscripttype) {
            case ManuscriptListInfo.MANUSCRIPT_TYPE_CMS:
                iv_tool_title.setText("稿件详情");
                break;
            case ManuscriptListInfo.MANUSCRIPT_TYPE_TV:
                iv_tool_title.setText("电视详情");
                break;
            case ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO:
                iv_tool_title.setText("微博详情");
                break;
            case ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT:
                iv_tool_title.setText("微信详情");
                break;
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        uploadThumbnailDialog = new MaterialDialog.Builder(this)
                .title("缩略图获取方式")
                .itemsGravity(GravityEnum.CENTER)
                .items(new String[]{"本地", "拍照"})
                .itemsColor(Color.parseColor("#333333"))
                .itemsCallback(this)
                .build();
        permissionUtil = new PermissionUtil(this);

    }

    private void back() {
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void removeLoading() {

    }

    public void showTextDialog(String title, String message) {
        new MaterialDialog.Builder(this)
                .title(title)
                .content(message)
                .positiveText("确定")
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save:
                break;
            case R.id.search_top_bar:
                break;
        }
    }

    public void showWaiting(String text) {
        getProgressDialog(text);
        progressDialog.show();
    }

    public void removeWaiting() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    private DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                return true;
            } else {
                return false;
            }
        }
    };

    public ProgressDialog getProgressDialog(String text) {
        if (progressDialog != null) {
            progressDialog.setMessage(text);
            return progressDialog;
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(text);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setOnKeyListener(keylistener);
            return progressDialog;
        }
    }

    private void save(final boolean exit) {
        showWaiting("保存稿件中...");
        Map map = info.getMap();
        Map<String, String> stringMap = (Map<String, String>) map.get("stringMap");
        Map<String, Integer> intMap = (Map<String, Integer>) map.get("int");
        RetrofitHelper.getInstance(this).saveManuscript(stringMap, intMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ResultSaveManuscriptInfo>() {
                    @Override
                    public void onNext(@NonNull ResultSaveManuscriptInfo resultSaveManuscriptInfo) {
                        boolean status = resultSaveManuscriptInfo.isStatus();
                        if (status) {
                            Toast.makeText(SettingActivity.this, "保存成功", 0).show();
                        } else {
                            Toast.makeText(SettingActivity.this, "保存失败", 0).show();
                        }
                        removeWaiting();
                        if (exit) {
                            Intent intent = new Intent();
                            intent.putExtra("info", info);
                            setResult(Constant.RESULTCODE_SETTING, intent);
                            finish();
                        } else {
                            adapter.setImageUrl();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(SettingActivity.this, "保存失败", 0).show();
                        removeWaiting();
                    }
                });
    }

    public void selectFile(int requestCode) {
        if (requestCode == SELECTFILE_FOR_WECHAT_THUMBNAIL_CHOOSE_FILE) {
            PickFileManager.getInstance().pickFile(this, 1, requestCode);
        } else if (requestCode == SELECTFILE_FOR_WECHAT_THUMBNAIL_TAKE_PHOTO) {
            takePhoto(requestCode);
        }
    }

    private void takePhoto(int code) {
        imageFile = this.getFileByDate("images", "jpg");
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        this.startActivityForResult(cameraIntent, code);
    }


    public File getFileByDate(String fileDir, String fileExtType) {
        File fileout = null;
        try {
            File out = null;
            SimpleDateFormat sDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String date = sDateFormat.format(new Date());
            date = date.replaceAll(" |:|-", "");
            String uploadPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + fileDir + "/";
            out = new File(uploadPath);
            if (!out.exists()) {
                out.mkdirs();
            }
            String uplaodFileName = date.toString() + "." + fileExtType;
            fileout = new File(uploadPath, uplaodFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return fileout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String path;
        if (requestCode == SELECTFILE_FOR_WECHAT_THUMBNAIL_CHOOSE_FILE) {
            if (data == null) {
                return;
            }
            ArrayList<String> files = data.getStringArrayListExtra("files");
            path = files.get(0);
            upLoadFile(path);
        } else {
            upLoadFile(imageFile.getPath());
        }
    }

    public void upLoadFile(String path) {
        File file = new File(path);
        boolean exists = file.exists();
        long length = file.length();
        if (!exists || length < 1) {
            return;
        }
        String taskId = info.manuscriptid;
        taskId = taskId + "_weixinLowImage";
        uploadFile(path, taskId);
    }

    public void uploadFile(final String path, String taskId) {
        showWaiting("文件上传中");
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                removeWaiting();
                switch (msg.what) {
                    case Constants.UPLOADSUCCESS:
                        upLoadFileComplete(path);
                        Toast.makeText(SettingActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                        break;
                    case Constants.UPLOADFAILTURE:
                        Toast.makeText(SettingActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        UploadFileThread uploadFileThread;
        String storageURL = PublicResource.getInstance().getStorageURL();
        String fileStatusNotifyURL = PublicResource.getInstance().getFileStatusNotifyURL();
        if (storageURL.startsWith("http")) {
            uploadFileThread = new HttpUpload(storageURL,
                    fileStatusNotifyURL, path, handler,
                    taskId);
            uploadFileThread.start();
        } else if (storageURL.startsWith("ftp")) {
            Log.i(TAG, "uploadFiles: " + fileStatusNotifyURL);
            uploadFileThread = new FtpUpload(storageURL, path,
                    fileStatusNotifyURL, handler,
                    taskId);
            uploadFileThread.start();
        }
    }

    public void upLoadFileComplete(String path) {
        String streamPath = PublicResource.getInstance().getStreamPath();
        streamPath = streamPath.endsWith("/") ? streamPath : streamPath + "/";
        File file = new File(path);
        String name = file.getName();
        if (ManuscriptListInfo.MANUSCRIPT_TYPE_CMS == info.manuscripttype) {
            info.weixinlowimage = "phone/" + info.manuscriptid + "_weixinLowImage/" + name;
        } else {
            info.weixinlowimage = "phone/" + info.manuscriptid + "_weixinLowImage/" + name;
        }
        adapter.setImageUrl();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        permissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onSelection(MaterialDialog dialog, View itemView, int position, final CharSequence text) {

        permissionUtil.checkPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, new PermissionUtil.PermissionListener() {
            @Override
            public void permissionAllowed() {
                if (text.equals("本地")) {
                    selectFile(SELECTFILE_FOR_WECHAT_THUMBNAIL_CHOOSE_FILE);
                } else if (text.equals("拍照")) {
                    selectFile(SELECTFILE_FOR_WECHAT_THUMBNAIL_TAKE_PHOTO);
                }
            }

            @Override
            public void permissionRefused() {
                showTextDialog("提示", "没有足够的权限，请进入权限设置更改权限");
            }
        });
    }
}
