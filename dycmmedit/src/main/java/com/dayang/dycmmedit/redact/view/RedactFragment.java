package com.dayang.dycmmedit.redact.view;


import android.Manifest;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.adapter.EmojiListAdapter;
import com.dayang.dycmmedit.adapter.EmojiPagerAdapter;
import com.dayang.dycmmedit.base.BaseActivity;
import com.dayang.dycmmedit.base.BaseFragment;
import com.dayang.dycmmedit.dialog.AuditDialog;
import com.dayang.dycmmedit.dialog.AuditHistoryDialog;
import com.dayang.dycmmedit.dialog.CreateManuscriptDialog;
import com.dayang.dycmmedit.dialog.ImageDialog;
import com.dayang.dycmmedit.dialog.SubmitDialog;
import com.dayang.dycmmedit.info.CensorRecordInfo;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.dayang.dycmmedit.info.RequestAuditManuscript;
import com.dayang.dycmmedit.info.RequestSubmitManuscript;
import com.dayang.dycmmedit.info.UserListAndTargetSystem;
import com.dayang.dycmmedit.preview.PreviewActivity;
import com.dayang.dycmmedit.redact.presenter.RedactPresenterImpl;
import com.dayang.dycmmedit.setting.SettingActivity;
import com.dayang.dycmmedit.utils.Constant;
import com.dayang.dycmmedit.utils.MediaFile;
import com.dayang.dycmmedit.utils.PermissionUtil;
import com.dayang.dycmmedit.utils.PrivilegeUtil;
import com.dayang.dycmmedit.utils.PublicResource;
import com.dayang.pickmediafile.common.PickFileManager;
import com.dayang.richeditor.RichEditor;
import com.elvishew.xlog.XLog;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

import static com.dayang.uploadlib.task.FtpUploadTask.TAG;

/**
 * Created by 冯傲 on 2017/5/8.
 * e-mail 897840134@qq.com
 */

public class RedactFragment extends BaseFragment implements RedactViewInterface, View.OnLayoutChangeListener, MaterialDialog.ListCallback {

    @BindView(R.id.toolbar_tv_redact)
    Toolbar toolbar;
    @BindView(R.id.ll_select_image)
    LinearLayout ll_select_image;

    @BindView(R.id.take_photo)
    TextView take_photo;

    @BindView(R.id.take_video)
    TextView take_video;

    @BindView(R.id.iv_select_file)
    ImageView iv_select_file;

    @BindView(R.id.iv_shelter)
    ImageView iv_shelter;

    @BindView(R.id.editor)
    RichEditor editor;

    @BindView(R.id.et_weibo)
    EditText et_weibo;

    @BindView(R.id.tv_weibo_text_count)
    TextView tv_weibo_text_count;

    @BindView(R.id.from_album)
    TextView from_album;

    @BindView(R.id.iv_alter)
    ImageView iv_alter;

    @BindView(R.id.iv_emotion)
    ImageView iv_emotion;

    @BindView(R.id.iv_input_method)
    ImageView iv_input_method;

    @BindView(R.id.iv_topic)
    ImageView iv_topic;

    @BindView(R.id.iv_record)
    ImageView iv_record;

    @BindView(R.id.et_header)
    EditText et_header;

    @BindView(R.id.rl_root)
    RelativeLayout rl_root;

    @BindView(R.id.ll_tool_set)
    LinearLayout ll_tool_set;

    @BindView(R.id.iv_tool_title)
    TextView iv_tool_title;

    @BindView(R.id.ll_editor_weibo)
    LinearLayout ll_editor_weibo;

    @BindView(R.id.iv_camera_weibo)
    ImageView iv_camera_weibo;

    @BindView(R.id.iv_select_file_weibo)
    ImageView iv_select_file_weibo;

    @BindView(R.id.iv_close_weibo_image)
    ImageView iv_close_weibo_image;

    @BindView(R.id.iv_weibo_image)
    ImageView iv_weibo_image;

    @BindView(R.id.rl_weibo_image)
    RelativeLayout rl_weibo_image;

    @BindView(R.id.vp_emoji)
    ViewPager vp_emoji;

    @BindView(R.id.ll_emoji)
    LinearLayout ll_emoji;

    @BindView(R.id.ll_emoji_point)
    LinearLayout ll_emoji_point;

    public static final int UPLOAD_REQUESTCODE_NORMAL = 786;
    public static final int UPLOAD_REQUESTCODE_WEIBO = 787;
    public static final int UPLOAD_REQUESTCODE_WECHAT_THUMBNAIL = 788;

    public static final int SELECTFILE_NORMAL_TAKE_PHOTO = 836;
    public static final int SELECTFILE_WEB_TAKE_VIDEO = 837;
    public static final int SELECTFILE_WEB_RECORD = 842;
    public static final int SELECTFILE_WEB_CHOOSE_FILE = 840;

    public static final int SELECTFILE_FOR_WEIBO_TAKE_PHOTO = 838;
    public static final int SELECTFILE_FOR_WEIBO_CHOOSE_FILE = 841;

    public static final int SELECTFILE_FOR_WECHAT_THUMBNAIL_TAKE_PHOTO = 835;
    public static final int SELECTFILE_FOR_WECHAT_THUMBNAIL_CHOOSE_FILE = 839;
    public static final int SELECTFILE_FOR_WECHAT_CHOOSE_FILE = 827;

    private RedactActivity activity;
    private ManuscriptListInfo manuscriptListInfo;
    private RedactPresenterImpl redactPresenter;
    private CreateManuscriptDialog dialog;
    private boolean hasChange = false;
    private String editorH5content="";

    String title = "";
    String originalTitle = "";
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private InputMethodManager imm;
    private MaterialDialog uploadThumbnailDialog;
    private MaterialDialog saveChangeDialog;
    private File imageFile;
    private File videoFile;
    String weixinImageUrl;
    String textContent;
    private boolean hasPrivilege;
    private List<View> emojiRecyclerViewList;
    private int width;
    private int emojiheight;
    private ValueAnimator emojiLayoutShowAnim;
    private ValueAnimator emojiLayoutCloseAnim;
    private boolean emojiMoveing;
    private boolean immOpen;
    final int OPEN_EMOJI = 123;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case OPEN_EMOJI:
                    emojiLayoutShowAnim.start();
                    iv_shelter.setVisibility(View.VISIBLE);
                    iv_shelter.setClickable(true);
                    iv_shelter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showEmoji();
                        }
                    });
                    break;
            }
        }
    };
    private MaterialDialog historyDialog;
    private List<CensorRecordInfo> censorRecordInfos;
    private PermissionUtil permissionUtil;

    public static RedactFragment newInstance(ManuscriptListInfo manuscriptListInfo) {
        RedactFragment newFragment = new RedactFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("manuscriptListInfo", manuscriptListInfo);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_redact_tv;
    }

    @Override
    public void init(final View view, @Nullable Bundle savedInstanceState) {
        permissionUtil = new PermissionUtil(getActivity(), this);
        activity = (RedactActivity) getActivity();
        Bundle arguments = getArguments();
        manuscriptListInfo = (ManuscriptListInfo) arguments.getSerializable("manuscriptListInfo");
        originalTitle = manuscriptListInfo.header;
        WebView.setWebContentsDebuggingEnabled(true);
        redactPresenter = new RedactPresenterImpl(this);
        //获取屏幕高度
        screenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
        imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        uploadThumbnailDialog = new MaterialDialog.Builder(getActivity())
                .title("缩略图获取方式")
                .itemsGravity(GravityEnum.CENTER)
                .items(new String[]{"本地", "拍照"})
                .itemsColor(Color.parseColor("#333333"))
                .itemsCallback(this)
                .build();
        saveChangeDialog = new MaterialDialog.Builder(getActivity())
                .title("稿件已经更改需要保存吗？")
                .itemsGravity(GravityEnum.CENTER)
                .items(new String[]{"保存", "不保存", "取消"})
                .itemsColor(Color.parseColor("#333333"))
                .itemsCallback(this)
                .build();
        weixinImageUrl = manuscriptListInfo.weixinlowimage;
        textContent = manuscriptListInfo.textcontent;
        hasPrivilege = PrivilegeUtil.hasPrivilege(PrivilegeUtil.PRIVILEGE_WRITE, manuscriptListInfo);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        rl_root.addOnLayoutChangeListener(this);
    }

    private void initView() {
        setHasOptionsMenu(true);
        //TODO 标题设置
        et_header.addTextChangedListener(new ListViewCompat(getContext()) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                title = et_header.getText().toString().trim();
            }
        });
        setBackKeyListener(new BaseActivity.BackKeyListener() {
            @Override
            public boolean onBackKeyDown() {
                editorH5content = editor.getHtml();
                back();
                return true;
            }
        });
        switch (manuscriptListInfo.manuscripttype) {
            case ManuscriptListInfo.MANUSCRIPT_TYPE_CMS:
                iv_tool_title.setText("网页");
                break;
            case ManuscriptListInfo.MANUSCRIPT_TYPE_TV:
                iv_tool_title.setText("电视");
                break;
            case ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT:
                iv_tool_title.setText("微信");
                break;
            case ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO:
                iv_tool_title.setText("微博");
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editorH5content = editor.getHtml();
                back();
            }
        });
        et_header.setHint("标题： " + this.manuscriptListInfo.header);
        iv_alter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入设置页面
                //保存标题
                manuscriptListInfo.header = title.equals("") ? originalTitle : title;
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                intent.putExtra("info", manuscriptListInfo);
                intent.putExtra("hasPrivilege", hasPrivilege);
                XLog.i("onClick: " + manuscriptListInfo);
                RedactFragment.this.startActivityForResult(intent, Constant.REQUESTCODE_SETTING);
            }
        });

        dialog = new CreateManuscriptDialog(getActivity(), manuscriptListInfo, hasPrivilege);
        dialog.setCreateListener(new CreateManuscriptDialog.CreateListener() {
            @Override
            public void onCreateManuscript(ManuscriptListInfo info) {
                if (info.columnname.equals(info.columns.get(0))) {
                    Toast.makeText(getActivity(), "所属栏目不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.hide();
            }
        });
        iv_emotion.setVisibility(View.GONE);
        iv_topic.setVisibility(View.GONE);
        iv_select_file_weibo.setVisibility(View.GONE);
        iv_camera_weibo.setVisibility(View.GONE);
        switch (manuscriptListInfo.manuscripttype) {
            case ManuscriptListInfo.MANUSCRIPT_TYPE_CMS:
                ll_tool_set.setVisibility(View.VISIBLE);
                iv_emotion.setVisibility(View.GONE);
                iv_topic.setVisibility(View.GONE);
                iv_select_file.setVisibility(View.VISIBLE);
                iv_input_method.setVisibility(View.VISIBLE);
                iv_record.setVisibility(View.VISIBLE);
                take_video.setVisibility(View.VISIBLE);
                editor.setVisibility(View.VISIBLE);
                ll_editor_weibo.setVisibility(View.GONE);
                break;
            case ManuscriptListInfo.MANUSCRIPT_TYPE_TV:
                ll_tool_set.setVisibility(View.GONE);
                editor.setVisibility(View.VISIBLE);
                ll_editor_weibo.setVisibility(View.GONE);
                break;
            case ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT:
                ll_tool_set.setVisibility(View.VISIBLE);
                iv_emotion.setVisibility(View.GONE);
                iv_topic.setVisibility(View.GONE);
                iv_select_file.setVisibility(View.VISIBLE);
                iv_input_method.setVisibility(View.VISIBLE);
                iv_record.setVisibility(View.GONE);
                take_video.setVisibility(View.GONE);
                take_photo.setVisibility(View.VISIBLE);
                from_album.setVisibility(View.VISIBLE);
                editor.setVisibility(View.VISIBLE);
                ll_editor_weibo.setVisibility(View.GONE);
                break;
            case ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO:
                editor.setVisibility(View.GONE);
                ll_tool_set.setVisibility(View.VISIBLE);
                iv_input_method.setVisibility(View.VISIBLE);
                iv_select_file.setVisibility(View.GONE);
                iv_record.setVisibility(View.GONE);
                iv_emotion.setVisibility(View.VISIBLE);
                iv_topic.setVisibility(View.VISIBLE);
                iv_input_method.setVisibility(View.VISIBLE);
                iv_record.setVisibility(View.GONE);
                take_video.setVisibility(View.GONE);
                ll_editor_weibo.setVisibility(View.VISIBLE);
                iv_select_file_weibo.setVisibility(View.VISIBLE);
                iv_camera_weibo.setVisibility(View.VISIBLE);
                iv_input_method.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                });
                initWeibo();
                return;
        }
        iv_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile(SELECTFILE_WEB_RECORD);
            }
        });
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile(SELECTFILE_NORMAL_TAKE_PHOTO);
            }
        });
        take_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile(SELECTFILE_WEB_TAKE_VIDEO);
            }
        });
        iv_shelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMediaFile();
            }
        });
        iv_shelter.setClickable(false);

        ll_select_image.setVisibility(View.GONE);
        iv_input_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        iv_select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMediaFile();
            }
        });
        from_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manuscriptListInfo.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_CMS) {
                    selectFile(SELECTFILE_WEB_CHOOSE_FILE);
                } else {
                    selectFile(SELECTFILE_FOR_WECHAT_CHOOSE_FILE);
                }
            }
        });
        editor.setHtml(manuscriptListInfo.h5content);
        if (!hasPrivilege) {
            editor.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            ll_tool_set.setVisibility(View.GONE);
        } else {
            editor.focusEditor();
        }


    }

    private void initWeibo() {
        et_weibo.addTextChangedListener(new ListViewCompat(getActivity()) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                int usableLength = 144 - et_weibo.getText().toString().trim().length();
                tv_weibo_text_count.setText("还可以输入" + usableLength + "字");
                if (count == 0) {
                    et_weibo.setHint("分享身边的新鲜事...");
                }
                manuscriptListInfo.textcontent = et_weibo.getText().toString().trim();
            }
        });
        if (!manuscriptListInfo.textcontent.equals("")) {
            et_weibo.setText(manuscriptListInfo.textcontent);
        } else {
            et_weibo.setHint("分享身边的新鲜事...");
        }

        iv_emotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertEmotion();
            }
        });

        iv_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertTopic();
            }
        });

        iv_camera_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile(SELECTFILE_FOR_WEIBO_TAKE_PHOTO);
            }
        });

        iv_select_file_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile(SELECTFILE_FOR_WEIBO_CHOOSE_FILE);
            }
        });

        iv_close_weibo_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWeiboImage();
            }
        });
        redactPresenter.getWeiboImage(manuscriptListInfo);
        if (!hasPrivilege) {
            et_weibo.setKeyListener(null);
            iv_emotion.setOnClickListener(null);
            iv_topic.setOnClickListener(null);
            iv_camera_weibo.setOnClickListener(null);
            iv_select_file_weibo.setOnClickListener(null);
        }
        //init 表情
        WindowManager wm = activity.getWindowManager();
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        width = point.x;
        vp_emoji.setMinimumHeight(width);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) vp_emoji.getLayoutParams();
        layoutParams.height = (width / 7) * 3;
        vp_emoji.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) ll_emoji_point.getLayoutParams();
        layoutParams1.height = (width / 14);
        ll_emoji_point.setLayoutParams(layoutParams1);
        et_weibo.setFocusable(true);
        et_weibo.setFocusableInTouchMode(true);
        et_weibo.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                imm.showSoftInput(et_weibo, 0);
            }
        }, 1000);
        initEmoji();
    }

    private void initEmoji() {
        emojiRecyclerViewList = getEmojiRecyclerViews();
        EmojiPagerAdapter emojiPagerAdapter = new EmojiPagerAdapter(activity, emojiRecyclerViewList, width);
        emojiPagerAdapter.setListenr(new EmojiListAdapter.ClickEmojiListener() {
            @Override
            public void onClick(int resource) {
                int index = et_weibo.getSelectionStart();//获取光标所在位置
                Editable edit = et_weibo.getEditableText();//获取EditText的文字
                String content = et_weibo.getText().toString();  //获取EditText的文字
                if (resource == R.drawable.compose_emotion_delete_highlighted) {//删除
                    String substring1 = content.substring(0, index);
                    if (substring1.endsWith("]")) {
                        int i = substring1.lastIndexOf("[");
                        edit.delete(i, index);
                    } else {
                        edit.delete(index - 1, index);
                    }
                    return;
                }
                String s = PublicResource.getInstance().getDrawableMap().get(resource);
                if (index < 0 || index >= edit.length()) {
                    edit.append(s);
                } else {
                    edit.insert(index, s);//光标所在位置插入文字
                }
                et_weibo.setSelection(index + s.length());
            }
        });
        vp_emoji.setAdapter(emojiPagerAdapter);
        initEmojiPoint(0);
        vp_emoji.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                View childAt = ll_emoji_point.getChildAt(position);
                childAt.setBackgroundResource(R.drawable.shap_view_pager_point_light);
                initEmojiPoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        emojiheight = (width / 7) * 3 + (width / 14);
        ll_emoji.setTranslationY(emojiheight);
        ll_emoji.setVisibility(View.VISIBLE);
        initEmojiLayoutAnim();

    }

    public void initEmojiLayoutAnim() {
        emojiMoveing = false;
        emojiLayoutShowAnim = ValueAnimator.ofFloat(emojiheight, 0);
        emojiLayoutShowAnim.setDuration(150);
        emojiLayoutShowAnim.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                double v = (Math.PI / 2) / 1.0;
                float sin = (float) Math.sin(input * v);
                return sin;
            }
        });
        emojiLayoutShowAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ll_emoji.setTranslationY((Float) animation.getAnimatedValue());
            }
        });

        emojiLayoutCloseAnim = ValueAnimator.ofFloat(0, emojiheight);
        emojiLayoutCloseAnim.setDuration(150);
        emojiLayoutCloseAnim.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                double v = (Math.PI / 2) / 1.0;
                float sin = (float) Math.sin(input * v);
                return sin;
            }
        });
        emojiLayoutCloseAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ll_emoji.setTranslationY((Float) animation.getAnimatedValue());
            }
        });
        emojiLayoutCloseAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                emojiMoveing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                emojiMoveing = false;
                imm.showSoftInput(et_weibo, InputMethodManager.SHOW_FORCED);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        emojiLayoutShowAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                emojiMoveing = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                emojiMoveing = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void showEmoji() {
        if (emojiMoveing) {
            return;
        }
        et_weibo.setFocusable(true);
        et_weibo.setFocusableInTouchMode(true);
        et_weibo.requestFocus();
        float translationY = ll_emoji.getTranslationY();
        if (translationY == 0) {
            emojiLayoutCloseAnim.start();
            iv_shelter.setVisibility(View.GONE);
            iv_shelter.setClickable(false);
            iv_shelter.setOnClickListener(null);
        } else {
            if (immOpen) {
                imm.hideSoftInputFromWindow(et_weibo.getWindowToken(), 0);
                handler.sendEmptyMessageDelayed(OPEN_EMOJI, 250);
            } else {
                emojiLayoutShowAnim.start();
                iv_shelter.setVisibility(View.VISIBLE);
                iv_shelter.setClickable(true);
                iv_shelter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEmoji();
                    }
                });
            }
        }
    }

    private void initEmojiPoint(int position) {
        ll_emoji_point.removeAllViews();
        for (int i = 0; i < emojiRecyclerViewList.size(); i++) {
            ImageView imageView = new ImageView(activity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 20, 20, 20);
            imageView.setLayoutParams(params);
            if (i == position) {
                imageView.setBackgroundResource(R.drawable.shap_view_pager_point_dark);
            } else {
                imageView.setBackgroundResource(R.drawable.shap_view_pager_point_light);
            }
            ll_emoji_point.addView(imageView);
        }
    }

    private List<View> getEmojiRecyclerViews() {
        List<View> list = new ArrayList<>();
        List<Integer> drawableList = PublicResource.getInstance().getDrawableList();
        int pageCount = drawableList.size() % 20 == 0 ? drawableList.size() / 20 : drawableList.size() / 20 + 1;
        for (int i = 0; i < pageCount; i++) {
            View view = LayoutInflater.from(activity).inflate(R.layout.view_pager_item_emoji, vp_emoji, false);
            list.add(view);
        }
        return list;
    }

    private void closeWeiboImage() {
        rl_weibo_image.setVisibility(View.GONE);
        ImageLoader.getInstance().displayImage("", iv_weibo_image);
        redactPresenter.delWeiboImage(manuscriptListInfo);
    }

    private void insertTopic() {
        int index = et_weibo.getSelectionStart();//获取光标所在位置
        String text = "#在这里输入你想要说的话题#";
        Spanned spanned = colorText(text);
        Editable edit = et_weibo.getEditableText();//获取EditText的文字
        if (index < 0 || index >= edit.length()) {
            edit.append(spanned);
            Selection.setSelection(edit, edit.length() - 13, edit.length() - 1);
        } else {
            edit.insert(index, spanned);//光标所在位置插入文字
            Selection.setSelection(edit, 0, 0);
            Selection.setSelection(edit, index + 1, index + 14);
        }

    }


    private Spanned colorText(String text) {
        return Html.fromHtml(String.format("<font color='#3399FF'>%1$s</font>", text));
    }

    private void insertEmotion() {
        et_weibo.setFocusable(true);
        et_weibo.setFocusableInTouchMode(true);
        et_weibo.requestFocus();//获取焦点 光标出现  
        showEmoji();
    }

    public void addMediaFile() {
        int visibility = ll_select_image.getVisibility();
        if (visibility == View.VISIBLE) {
            ll_select_image.setVisibility(View.GONE);
            iv_shelter.setClickable(false);
        } else {
            ll_select_image.setVisibility(View.VISIBLE);
            iv_shelter.setClickable(true);
        }
    }

    public void selectFile(final int requestCode) {

        if (requestCode == SELECTFILE_WEB_CHOOSE_FILE || requestCode == SELECTFILE_FOR_WECHAT_THUMBNAIL_CHOOSE_FILE || requestCode == SELECTFILE_FOR_WECHAT_CHOOSE_FILE || requestCode == SELECTFILE_FOR_WEIBO_CHOOSE_FILE) {
            permissionUtil.checkPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionUtil.PermissionListener() {
                @Override
                public void permissionAllowed() {
                    PickFileManager.getInstance().pickFile(RedactFragment.this, 1, requestCode);
                }

                @Override
                public void permissionRefused() {
                    showTextDialog("提示", "没有足够的权限，请进入权限设置更改权限");
                }
            });
        } else if (requestCode == SELECTFILE_NORMAL_TAKE_PHOTO || requestCode == SELECTFILE_FOR_WECHAT_THUMBNAIL_TAKE_PHOTO || requestCode == SELECTFILE_FOR_WEIBO_TAKE_PHOTO) {
            permissionUtil.checkPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, new PermissionUtil.PermissionListener() {
                @Override
                public void permissionAllowed() {
                    takePhoto(requestCode);
                }

                @Override
                public void permissionRefused() {
                    showTextDialog("提示", "没有足够的权限，请进入权限设置更改权限");
                }
            });
        } else if (requestCode == SELECTFILE_WEB_TAKE_VIDEO) {
            permissionUtil.checkPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, new PermissionUtil.PermissionListener() {
                @Override
                public void permissionAllowed() {
                    takeVideo(requestCode);
                }

                @Override
                public void permissionRefused() {
                    showTextDialog("提示", "没有足够的权限，请进入权限设置更改权限");
                }
            });
        } else if (requestCode == SELECTFILE_WEB_RECORD) {

            permissionUtil.checkPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, new PermissionUtil.PermissionListener() {
                @Override
                public void permissionAllowed() {
                    takeRecord(requestCode);
                }

                @Override
                public void permissionRefused() {
                    showTextDialog("提示", "没有足够的权限，请进入权限设置更改权限");
                }
            });
        }
    }

    private void takePhoto(int code) {
        imageFile = this.getFileByDate("images", "jpg");
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        this.startActivityForResult(cameraIntent, code);
    }

    private void takeVideo(int code) {
        videoFile = this.getFileByDate("videos", "mp4");
        Intent cameraVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);// 构造intent
        File out = getFileByDate("videos", "mp4");
        Uri uri = Uri.fromFile(out);
        cameraVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        this.startActivityForResult(cameraVideoIntent, code);
    }

    private void takeRecord(int code) {
        Toast.makeText(activity, "暂时没有实现", 0).show();
    }

    private void back() {
        boolean hasPrivilege = PrivilegeUtil.hasPrivilege(PrivilegeUtil.PRIVILEGE_WRITE, manuscriptListInfo);
        if (!title.equals("") && !title.equals(manuscriptListInfo.header)) {
            saveChangeDialog.show();
            return;
        }
        switch (manuscriptListInfo.manuscripttype) {
            case ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO:
                if (textContent.equals(manuscriptListInfo.textcontent) || !hasPrivilege) {
                    activity.back(manuscriptListInfo, this, hasChange);
                } else {
                    saveChangeDialog.show();
                }
                return;
            case ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT:
                if (comparisonString(editorH5content, manuscriptListInfo.h5content)|| !hasPrivilege) {
                    activity.back(manuscriptListInfo, this, hasChange);
                } else {
                    saveChangeDialog.show();
                }
                return;
        }
        if (comparisonString(editorH5content, manuscriptListInfo.h5content)) {
            activity.back(manuscriptListInfo, this, hasChange);
        } else {
            saveChangeDialog.show();
        }

    }

    public boolean comparisonString(String string1, String string2) {
        if (string1.equals(string2)) {
            return true;
        }
        if (string1.endsWith("<p><br/></p>")) {
            string1 = string1.substring(0,string1.length() - 12);
        }
        return string1.equals(string2);
    }

    public void upLoadFile(String path, int requestCode) {
        File file = new File(path);
        boolean exists = file.exists();
        long length = file.length();
        if (!exists || length < 1) {
            return;
        }
        String taskId = manuscriptListInfo.manuscriptid;
        if (requestCode == UPLOAD_REQUESTCODE_NORMAL) {
            switch (manuscriptListInfo.manuscripttype) {
                case ManuscriptListInfo.MANUSCRIPT_TYPE_CMS:
                    taskId = taskId + "";
                    break;
                case ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT:
                    taskId = taskId + "";
                    break;
                case ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO:
                    hasChange = true;
                    taskId = taskId + "_weiboLowImage";
                    break;
                case ManuscriptListInfo.MANUSCRIPT_TYPE_TV:
                    taskId = taskId + "";
                    break;
            }
        } else if (requestCode == UPLOAD_REQUESTCODE_WEIBO) {
            taskId = taskId + "_weiboLowImage";
        } else if (requestCode == UPLOAD_REQUESTCODE_WECHAT_THUMBNAIL) {
            taskId = taskId + "_weixinLowImage";
        }
        redactPresenter.uploadFile(path, taskId, requestCode);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.redact_menu, menu);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getItemId() == R.id.action_preview) {
                if (manuscriptListInfo.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_TV || manuscriptListInfo.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO) {
                    menu.getItem(i).setVisible(false);
                    menu.getItem(i).setEnabled(false);
                }
            }
            if (menu.getItem(i).getItemId() == R.id.action_submit
                    && ((manuscriptListInfo.status == ManuscriptListInfo.MANUSCRIPT_STATUS_PASS
                    || manuscriptListInfo.status == ManuscriptListInfo.MANUSCRIPT_STATUS_WAITING_PENDING)
                    || (manuscriptListInfo.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT
                    && manuscriptListInfo.arrayindex != 0))) {
                menu.getItem(i).setVisible(false);
                menu.getItem(i).setEnabled(false);
            }
            if (menu.getItem(i).getItemId() == R.id.action_audit_history && (manuscriptListInfo.status == ManuscriptListInfo.MANUSCRIPT_STATUS_WAITING_SUBMIT)) {
                menu.getItem(i).setVisible(false);
                menu.getItem(i).setEnabled(false);
            }
            if (menu.getItem(i).getItemId() == R.id.action_audit) {
                boolean b = manuscriptListInfo.status != ManuscriptListInfo.MANUSCRIPT_STATUS_WAITING_PENDING;
                boolean hasPrivilege = PrivilegeUtil.hasPrivilege(PrivilegeUtil.PRIVILEGE_AUDIT, manuscriptListInfo);
                boolean b1 = manuscriptListInfo.manuscripttype == ManuscriptListInfo.MANUSCRIPT_TYPE_WECHAT && manuscriptListInfo.arrayindex != 0;
                if (b || !hasPrivilege || b1) {
                    menu.getItem(i).setVisible(false);
                    menu.getItem(i).setEnabled(false);
                }
            }
            if (menu.getItem(i).getItemId() == R.id.action_save) {
                boolean hasPrivilege = PrivilegeUtil.hasPrivilege(PrivilegeUtil.PRIVILEGE_WRITE, manuscriptListInfo);
                if (!hasPrivilege) {
                    menu.getItem(i).setVisible(false);
                    menu.getItem(i).setEnabled(false);
                }
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_save:
                save(false);
                break;
            case R.id.action_submit:
                submit();
                break;
            case R.id.action_preview:

                actionPreviewManuscript();
                break;
            case R.id.action_audit:
                actionAudit();
                break;
            case R.id.action_audit_history:
                showAuditHistory();
                break;
        }
        return true;
    }

    private void showThumbnailDialog() {
        new MaterialDialog.Builder(activity)
                .title("缩略图")
                .items("预览缩略图", "上传缩略图")
                .itemsColor(activity.getResources().getColor(R.color.textDark))
                .titleGravity(GravityEnum.START)
                .itemsGravity(GravityEnum.CENTER)
                .itemsCallback(this)
                .build()
                .show();
    }

    private void showAuditHistory() {
        if (historyDialog == null) {
            redactPresenter.getHistoryData(manuscriptListInfo);
        } else {
            historyDialog.show();
        }
    }

    public void setHistoryMessage(List<CensorRecordInfo> list) {
        censorRecordInfos = list;
        historyDialog = new AuditHistoryDialog(activity, censorRecordInfos).build();
        historyDialog.show();
    }


    public void showTextDialog(String title, final String message) {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .content(message)
                .positiveText("确定")
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (message.equals("审核成功") || message.equals("提交稿件成功")) {
                            activity.back(manuscriptListInfo, RedactFragment.this, true);
                        }
                    }
                })
                .show();

    }

    @Override
    public void showSubmitDialog(final UserListAndTargetSystem system, final ManuscriptListInfo manuscriptListInfo) {
        SubmitDialog submitDialog = new SubmitDialog(activity, system, manuscriptListInfo);
        final MaterialDialog build = submitDialog.build();
        submitDialog.setSubmitListener(new SubmitDialog.SubmitListener() {
            @Override
            public void onSubmit(final RequestSubmitManuscript submitManuscript) {
                if (system.showChooseAuditor) {
                    String censorAuditor = submitManuscript.getCensorAuditor();
                    if (censorAuditor == null || censorAuditor.equals("") || censorAuditor.equals("请指定审核人")) {
                        new MaterialDialog.Builder(activity)
                                .title("提示")
                                .content("您还未选择审核人")
                                .positiveColor(getResources().getColor(R.color.colorDYBlue))
                                .positiveText("确定")
                                .build().show();
                        return;
                    }
                }
                if (submitManuscript.getTargetSystemIds() == null || submitManuscript.getTargetSystemIds().equals("")) {
                    new MaterialDialog.Builder(activity)
                            .title("提示")
                            .content("您还未选择分发目标")
                            .positiveText("继续提交")
                            .positiveColor(getResources().getColor(R.color.colorDYBlue))
                            .negativeText("返回修改")
                            .negativeColor(getResources().getColor(R.color.colorDYBlue))
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    redactPresenter.submitManuscript(submitManuscript, manuscriptListInfo);
                                    build.dismiss();
                                }
                            })
                            .build().show();
                    return;
                }
                redactPresenter.submitManuscript(submitManuscript, manuscriptListInfo);
                build.dismiss();
            }
        });
        build.show();
    }

    @Override
    public void showTextDialog(String message) {
        showTextDialog("提示", message);
    }

    @Override
    public void showAuditDialog(UserListAndTargetSystem system, ManuscriptListInfo manuscriptListInfo) {
        AuditDialog auditDialog = new AuditDialog(activity, manuscriptListInfo, system);
        final MaterialDialog build = auditDialog.build();
        auditDialog.setListener(new AuditDialog.AuditListener() {
            @Override
            public void onAudit(ManuscriptListInfo info, RequestAuditManuscript auditManuscript) {
                build.dismiss();
                redactPresenter.auditManuscript(info, auditManuscript);
            }
        });
        build.show();
    }

    private void actionAudit() {
        String html = editor.getHtml();
        String textContent = editor.getTextContent();
        if (html == null) {
            html = "";
        }
        if (textContent == null) {
            textContent = "";
        }
        if (manuscriptListInfo.manuscripttype != ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO) {
            manuscriptListInfo.h5content = html;
            manuscriptListInfo.textcontent = textContent;
        } else {
            manuscriptListInfo.h5content = "";
        }
        manuscriptListInfo.header = title.equals("") ? originalTitle : title;
        redactPresenter.getAuditMessage(manuscriptListInfo);
    }

    private void actionPreviewThumbnail() {
        //预览缩略图
        String path = manuscriptListInfo.weixinlowimage;
        String streamPath = PublicResource.getInstance().getStreamPath();
        if (ManuscriptListInfo.MANUSCRIPT_TYPE_CMS == manuscriptListInfo.manuscripttype) {
            path = streamPath + "/" + path;
        }
        MaterialDialog dialog = new ImageDialog(activity, path).build();
        dialog.show();
    }

    private void actionPreviewManuscript() {
        String html = editor.getHtml();
        manuscriptListInfo.h5content = html == null ? "" : html;
        manuscriptListInfo.header = title.equals("") ? originalTitle : title;
        Intent intent = new Intent(getActivity(), PreviewActivity.class);
        intent.putExtra("info", manuscriptListInfo);
        startActivity(intent);
    }

    private void submit() {
        String html = editor.getHtml();
        String textContent = editor.getTextContent();
        if (html == null) {
            html = "";
        }
        if (textContent == null) {
            textContent = "";
        }
        if (manuscriptListInfo.manuscripttype != ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO) {
            manuscriptListInfo.h5content = html;
            manuscriptListInfo.textcontent = textContent;
        } else {
            manuscriptListInfo.h5content = "";
        }
        manuscriptListInfo.header = title.equals("") ? originalTitle : title;
        redactPresenter.getSubmitMessage(manuscriptListInfo);
    }

    private void uploadThumbnail() {
        uploadThumbnailDialog.show();
    }

    private void save(final boolean isBack) {
        String html = editor.getHtml();
        String textContent = editor.getTextContent();
        if (html == null) {
            html = "";
        }
        if (textContent == null) {
            textContent = "";
        }
        if (manuscriptListInfo.manuscripttype != ManuscriptListInfo.MANUSCRIPT_TYPE_WEIBO) {
            manuscriptListInfo.h5content = html;
            manuscriptListInfo.textcontent = textContent;
        } else {
            manuscriptListInfo.h5content = "";
        }
        manuscriptListInfo.header = title.equals("") ? originalTitle : title;
        redactPresenter.save(manuscriptListInfo, isBack);
    }


    @Override
    public Activity getViewActivity() {
        return getActivity();
    }


    @Override
    public void makeToast(String text) {
        Toast.makeText(getActivity(), text, 0).show();
    }


    @Override
    public void upLoadFileComplete(String path, int requestCode) {
        String streamPath = PublicResource.getInstance().getStreamPath();
        streamPath = streamPath.endsWith("/") ? streamPath : streamPath + "/";
        File file = new File(path);
        String name = file.getName();
        String url = "";
        if (requestCode == UPLOAD_REQUESTCODE_NORMAL) {
            url = streamPath + "phone//" + manuscriptListInfo.manuscriptid + "//" + name;
            boolean audioFileType = MediaFile.isAudioFileType(path);
            boolean videoFileType = MediaFile.isVideoFileType(path);
            boolean imageFileType = MediaFile.isImageFileType(path);
            if (audioFileType) {
                editor.insertAudio(url);
                editor.focusEditor();
            }
            if (videoFileType) {
                editor.insertVideo(url);
                editor.focusEditor();
            }
            if (imageFileType) {
                editor.insertImage(url);
                editor.focusEditor();
            }
        } else if (requestCode == UPLOAD_REQUESTCODE_WEIBO) {
            url = streamPath + "phone/" + manuscriptListInfo.manuscriptid + "_weiboLowImage/" + name;
            rl_weibo_image.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(url, iv_weibo_image);
        } else if (requestCode == UPLOAD_REQUESTCODE_WECHAT_THUMBNAIL) {
            if (ManuscriptListInfo.MANUSCRIPT_TYPE_CMS == manuscriptListInfo.manuscripttype) {
                manuscriptListInfo.weixinlowimage = "phone/" + manuscriptListInfo.manuscriptid + "_weixinLowImage/" + name;
            } else {
                manuscriptListInfo.weixinlowimage = streamPath + "/phone/" + manuscriptListInfo.manuscriptid + "_weixinLowImage/" + name;
            }
        }
    }

    @Override
    public void insertImageForWeibo(String url) {
        rl_weibo_image.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(url, iv_weibo_image);
    }


    @Override
    public void setTextContent(String text) {
        this.textContent = text;
    }

    @Override
    public void setHasChange(boolean hasChange) {
        this.hasChange = hasChange;
    }

    @Override
    public void setWeixinImage(String weixinImage) {
        this.weixinImageUrl = weixinImage;
        manuscriptListInfo.weixinlowimage = weixinImage;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        permissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void goBack() {
        activity.back(manuscriptListInfo, this, hasChange);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            immOpen = true;
            iv_input_method.setImageResource(R.drawable.ic_keyboard_hide);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            immOpen = false;
            iv_input_method.setImageResource(R.drawable.ic_live_speaker_keyboard);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUESTCODE_SETTING) {
            if (data != null) {
                XLog.i("onActivityResult: " + data.getSerializableExtra("info"));
                ManuscriptListInfo info = (ManuscriptListInfo) data.getSerializableExtra("info");
                this.manuscriptListInfo.copy(info);
                et_header.setHint("标题： " + this.manuscriptListInfo.header);
                setWeixinImage(manuscriptListInfo.weixinlowimage);
                originalTitle = info.header;
                hasChange = true;
            }
            return;
        }
        String path = "";
        if (requestCode != SELECTFILE_FOR_WECHAT_THUMBNAIL_CHOOSE_FILE && requestCode != SELECTFILE_FOR_WECHAT_THUMBNAIL_TAKE_PHOTO && requestCode != SELECTFILE_FOR_WEIBO_CHOOSE_FILE && requestCode != SELECTFILE_FOR_WEIBO_TAKE_PHOTO) {
            addMediaFile();
        }
        if (requestCode == SELECTFILE_WEB_CHOOSE_FILE || requestCode == SELECTFILE_FOR_WECHAT_THUMBNAIL_CHOOSE_FILE || requestCode == SELECTFILE_FOR_WECHAT_CHOOSE_FILE || requestCode == SELECTFILE_FOR_WEIBO_CHOOSE_FILE) {
            if (data == null) {
                return;
            }
            ArrayList<String> files = data.getStringArrayListExtra("files");
            path = files.get(0);
        }
        switch (requestCode) {
            case SELECTFILE_FOR_WECHAT_CHOOSE_FILE:
                upLoadFile(path, UPLOAD_REQUESTCODE_NORMAL);
                break;
            case SELECTFILE_WEB_CHOOSE_FILE:
                upLoadFile(path, UPLOAD_REQUESTCODE_NORMAL);
                break;
            case SELECTFILE_FOR_WECHAT_THUMBNAIL_CHOOSE_FILE:
                upLoadFile(path, UPLOAD_REQUESTCODE_WECHAT_THUMBNAIL);
                break;
            case SELECTFILE_FOR_WEIBO_CHOOSE_FILE:
                upLoadFile(path, UPLOAD_REQUESTCODE_WEIBO);
                break;
            case SELECTFILE_NORMAL_TAKE_PHOTO:
                upLoadFile(imageFile.getPath(), UPLOAD_REQUESTCODE_NORMAL);
                break;
            case SELECTFILE_FOR_WEIBO_TAKE_PHOTO:
                upLoadFile(imageFile.getPath(), UPLOAD_REQUESTCODE_WEIBO);
                break;
            case SELECTFILE_FOR_WECHAT_THUMBNAIL_TAKE_PHOTO:
                upLoadFile(imageFile.getPath(), UPLOAD_REQUESTCODE_WECHAT_THUMBNAIL);
                break;
            case SELECTFILE_WEB_TAKE_VIDEO:
                upLoadFile(videoFile.getPath(), UPLOAD_REQUESTCODE_NORMAL);
                break;
            case SELECTFILE_WEB_RECORD:
                upLoadFile(path, UPLOAD_REQUESTCODE_NORMAL);
                break;

        }
    }

    @Override
    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
        if (text.equals("本地")) {
            selectFile(SELECTFILE_FOR_WECHAT_THUMBNAIL_CHOOSE_FILE);
        } else if (text.equals("拍照")) {
            selectFile(SELECTFILE_FOR_WECHAT_THUMBNAIL_TAKE_PHOTO);
        } else if (text.equals("保存")) {
            save(true);
        } else if (text.equals("不保存")) {
            goBack();
        } else if (text.equals("预览缩略图")) {
            actionPreviewThumbnail();
        } else if (text.equals("上传缩略图")) {
            uploadThumbnail();
        } else if (text.equals("取消")) {

        }
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
}
