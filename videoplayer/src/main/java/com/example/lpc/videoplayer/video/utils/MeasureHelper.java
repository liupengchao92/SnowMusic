package com.example.lpc.videoplayer.video.utils;

import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Author: liupengchao
 * Date: 2021/7/15
 * ClassName :MeasureHelper
 * Desc:
 */
public class MeasureHelper {

    private WeakReference<View> mWeakView;
    private int mVideoWidth;
    private int mVideoHeight;
    private int mVideoSarNum;
    private int mVideoSarDen;
    private int mVideoRotationDegree;
    private int mMeasuredWidth;
    private int mMeasuredHeight;
    private int mCurrentAspectRatio = 0;
    private final MeasureHelper.MeasureFormVideoParamsListener mParamsListener;

    public MeasureHelper(View view, MeasureHelper.MeasureFormVideoParamsListener listener) {
        this.mParamsListener = listener;
        this.mWeakView = new WeakReference(view);
    }

    public View getView() {
        return this.mWeakView == null ? null : (View) this.mWeakView.get();
    }

    public void setVideoSize(int videoWidth, int videoHeight) {
        this.mVideoWidth = videoWidth;
        this.mVideoHeight = videoHeight;
    }

    public void setVideoSampleAspectRatio(int videoSarNum, int videoSarDen) {
        this.mVideoSarNum = videoSarNum;
        this.mVideoSarDen = videoSarDen;
    }

    public void setVideoRotation(int videoRotationDegree) {
        this.mVideoRotationDegree = videoRotationDegree;
    }

    public int getCurrentAspectRatio() {
        return mCurrentAspectRatio;
    }

    public void setCurrentAspectRatio(int mCurrentAspectRatio) {
        this.mCurrentAspectRatio = mCurrentAspectRatio;
    }

    public void doMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        if (this.mVideoRotationDegree == 90 || this.mVideoRotationDegree == 270) {
            width = widthMeasureSpec;
            widthMeasureSpec = heightMeasureSpec;
            heightMeasureSpec = width;
        }

        width = View.getDefaultSize(this.mVideoWidth, widthMeasureSpec);
        int height = View.getDefaultSize(this.mVideoHeight, heightMeasureSpec);
        if (this.mCurrentAspectRatio == -4) {
            width = widthMeasureSpec;
            height = heightMeasureSpec;
        } else if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
            int widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
            if (widthSpecMode == -2147483648 && heightSpecMode == -2147483648) {
                float specAspectRatio = (float) widthSpecSize / (float) heightSpecSize;
                float displayAspectRatio;
                switch (this.mCurrentAspectRatio) {
                    case 0:
                    case 3:
                    case 4:
                    default:
                        displayAspectRatio = (float) this.mVideoWidth / (float) this.mVideoHeight;
                        if (this.mVideoSarNum > 0 && this.mVideoSarDen > 0) {
                            displayAspectRatio = displayAspectRatio * (float) this.mVideoSarNum / (float) this.mVideoSarDen;
                        }
                        break;
                    case 1:
                        displayAspectRatio = 1.7777778F;
                        if (this.mVideoRotationDegree == 90 || this.mVideoRotationDegree == 270) {
                            displayAspectRatio = 1.0F / displayAspectRatio;
                        }
                        break;
                    case 2:
                        displayAspectRatio = 1.3333334F;
                        if (this.mVideoRotationDegree == 90 || this.mVideoRotationDegree == 270) {
                            displayAspectRatio = 1.0F / displayAspectRatio;
                        }
                }

                boolean shouldBeWider = displayAspectRatio > specAspectRatio;
                switch (this.mCurrentAspectRatio) {
                    case 0:
                    case 1:
                    case 2:
                        if (shouldBeWider) {
                            width = widthSpecSize;
                            height = (int) ((float) widthSpecSize / displayAspectRatio);
                        } else {
                            height = heightSpecSize;
                            width = (int) ((float) heightSpecSize * displayAspectRatio);
                        }
                        break;
                    case 3:
                    default:
                        if (shouldBeWider) {
                            width = Math.min(this.mVideoWidth, widthSpecSize);
                            height = (int) ((float) width / displayAspectRatio);
                        } else {
                            height = Math.min(this.mVideoHeight, heightSpecSize);
                            width = (int) ((float) height * displayAspectRatio);
                        }
                        break;
                    case 4:
                        if (shouldBeWider) {
                            height = heightSpecSize;
                            width = (int) ((float) heightSpecSize * displayAspectRatio);
                        } else {
                            width = widthSpecSize;
                            height = (int) ((float) widthSpecSize / displayAspectRatio);
                        }
                }
            } else if (widthSpecMode == 1073741824 && heightSpecMode == 1073741824) {
                width = widthSpecSize;
                height = heightSpecSize;
                if (this.mVideoWidth * heightSpecSize < widthSpecSize * this.mVideoHeight) {
                    width = heightSpecSize * this.mVideoWidth / this.mVideoHeight;
                } else if (this.mVideoWidth * heightSpecSize > widthSpecSize * this.mVideoHeight) {
                    height = widthSpecSize * this.mVideoHeight / this.mVideoWidth;
                }
            } else if (widthSpecMode == 1073741824) {
                width = widthSpecSize;
                height = widthSpecSize * this.mVideoHeight / this.mVideoWidth;
                if (heightSpecMode == -2147483648 && height > heightSpecSize) {
                    height = heightSpecSize;
                }
            } else if (heightSpecMode == 1073741824) {
                height = heightSpecSize;
                width = heightSpecSize * this.mVideoWidth / this.mVideoHeight;
                if (widthSpecMode == -2147483648 && width > widthSpecSize) {
                    width = widthSpecSize;
                }
            } else {
                width = this.mVideoWidth;
                height = this.mVideoHeight;
                if (heightSpecMode == -2147483648 && height > heightSpecSize) {
                    height = heightSpecSize;
                    width = heightSpecSize * this.mVideoWidth / this.mVideoHeight;
                }

                if (widthSpecMode == -2147483648 && width > widthSpecSize) {
                    width = widthSpecSize;
                    height = widthSpecSize * this.mVideoHeight / this.mVideoWidth;
                }
            }
        }

        this.mMeasuredWidth = width;
        this.mMeasuredHeight = height;
    }

    public void prepareMeasure(int widthMeasureSpec, int heightMeasureSpec, int rotate) {
        if (this.mParamsListener != null) {
            try {
                int videoWidth = this.mParamsListener.getCurrentVideoWidth();
                int videoHeight = this.mParamsListener.getCurrentVideoHeight();
                LogUtils.d("videoWidth: " + videoWidth + " videoHeight: " + videoHeight);
                int videoSarNum = this.mParamsListener.getVideoSarNum();
                int videoSarDen = this.mParamsListener.getVideoSarDen();
                if (videoWidth > 0 && videoHeight > 0) {
                    this.setVideoSampleAspectRatio(videoSarNum, videoSarDen);
                    this.setVideoSize(videoWidth, videoHeight);
                }

                this.setVideoRotation(rotate);
                this.doMeasure(widthMeasureSpec, heightMeasureSpec);
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }

    }

    public int getMeasuredWidth() {
        return this.mMeasuredWidth;
    }

    public int getMeasuredHeight() {
        return this.mMeasuredHeight;
    }

    public void setAspectRatio(int aspectRatio) {
        this.mCurrentAspectRatio = aspectRatio;
    }

    public interface MeasureFormVideoParamsListener {
        int getCurrentVideoWidth();

        int getCurrentVideoHeight();

        int getVideoSarNum();

        int getVideoSarDen();
    }
}
