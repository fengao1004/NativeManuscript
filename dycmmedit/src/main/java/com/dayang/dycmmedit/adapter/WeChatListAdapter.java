package com.dayang.dycmmedit.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dayang.dycmmedit.R;
import com.dayang.dycmmedit.base.BaseActivity;
import com.dayang.dycmmedit.base.BaseFragment;
import com.dayang.dycmmedit.holder.WechatListHolder;
import com.dayang.dycmmedit.info.ManuscriptListInfo;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 冯傲 on 2017/5/12.
 * e-mail 897840134@qq.com
 */

public class WeChatListAdapter extends RecyclerView.Adapter<WechatListHolder> {
    Context context;
    RecyclerView recyclerView;
    List<ManuscriptListInfo> list;
    AddListener addListener;
    DeleteListener deleteListener;
    GoToRedactListener goToRedactListener;

    public void setGoToRedactListener(GoToRedactListener goToRedactListener) {
        this.goToRedactListener = goToRedactListener;
    }

    View animationView;
    View animationDelView;
    BaseFragment fragment;
    private ValueAnimator animator;

    public void setDeleteListener(DeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setAddListener(AddListener addListener) {
        this.addListener = addListener;
    }

    public WeChatListAdapter(RecyclerView recyclerView, Context context, List<ManuscriptListInfo> list, BaseFragment fragment) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.list = list;
        this.fragment = fragment;
    }

    @Override
    public WechatListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_wechat_list, parent, false);
        return new WechatListHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final WechatListHolder holder, final int position) {
        if (position == list.size()) {
            holder.iv_wechat_add.setVisibility(View.VISIBLE);
            holder.rl_father.setVisibility(View.GONE);
            holder.ll_child.setVisibility(View.GONE);
            holder.iv_wechat_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (animationDelView != null) {
                        closeAnimation();
                    } else {
                        if (addListener != null) {
                            addListener.add();
                        }
                    }
                }
            });
            holder.iv_line_wechat.setVisibility(View.GONE);
        } else {

            holder.iv_line_wechat.setVisibility(View.VISIBLE);
            final ManuscriptListInfo info = list.get(position);
            holder.iv_delete_wechat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeAnimation();
                    if (deleteListener != null) {
                        deleteListener.delete(info);
                    }
                }
            });
            if (position == 0) {
                holder.iv_wechat_add.setVisibility(View.GONE);
                holder.rl_father.setVisibility(View.VISIBLE);
                holder.ll_child.setVisibility(View.GONE);
                ImageLoader.getInstance().displayImage(info.weixinlowimage, holder.iv_father_image);
                holder.tv_father_header.setText(info.header);
            } else {
                holder.iv_wechat_add.setVisibility(View.GONE);
                holder.rl_father.setVisibility(View.GONE);
                holder.ll_child.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(info.weixinlowimage, holder.iv_child_image);
                holder.tv_child_header.setText(info.header);
                holder.tv_child_time.setText(info.createtime);
                holder.tv_child_username.setText(info.username);
            }
            holder.ll_wechat_list_root.setLongClickable(true);
            holder.ll_wechat_list_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (animationDelView != null) {
                        closeAnimation();
                    } else {
                        if (goToRedactListener != null) {
                            goToRedactListener.goToRedact(info);
                        }
                    }
                }
            });
            holder.ll_wechat_list_root.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (animationDelView != null) {
                        closeAnimation();
                    }
                    animationView = holder.ll_wechat_list_root;
                    animationDelView = holder.iv_delete_wechat;
                    holder.ll_wechat_list_root.setScaleX(0.85f);
                    holder.ll_wechat_list_root.setScaleY(0.85f);
                    holder.iv_delete_wechat.setVisibility(View.VISIBLE);
                    openAnimation();
                    return true;
                }
            });

        }
    }

    public void openAnimation() {
        if (animator == null) {
            animator = ValueAnimator.ofInt(-2, 2);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    animationView.setRotation((animatedValue));
                }
            });
            animator.setDuration(70);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.REVERSE);
        }
        fragment.setBackKeyListener(new BaseActivity.BackKeyListener() {
            @Override
            public boolean onBackKeyDown() {
                closeAnimation();
                return true;
            }
        });
        animator.start();
    }

    public void closeAnimation() {
        if (animationView != null && animationDelView != null) {
            animator.end();
            animationView.setScaleX(1.0f);
            animationView.setScaleY(1.0f);
            animationView.setRotation(0.0f);
            animationDelView.setVisibility(View.GONE);
            animationDelView = null;
            animationView = null;
            fragment.setBackKeyListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public interface AddListener {
        void add();
    }

    public interface DeleteListener {
        void delete(ManuscriptListInfo info);
    }

    public interface GoToRedactListener {
        void goToRedact(ManuscriptListInfo manuscriptListInfo);
    }

}
