package com.dayang.dycmmedit.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.dayang.dycmmedit.BuildConfig;
import com.dayang.dycmmedit.R;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.Printer;
import com.elvishew.xlog.printer.file.FilePrinter;
import com.elvishew.xlog.printer.file.backup.NeverBackupStrategy;
import com.elvishew.xlog.printer.file.naming.DateFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by 冯傲 on 2017/4/26.
 * e-mail 897840134@qq.com
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        ImageLoaderConfiguration aDefault = ImageLoaderConfiguration.createDefault(this);
//        ImageLoader.getInstance().init(aDefault);
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL             // 指定日志级别，低于该级别的日志将不会被打印，默认为 LogLevel.ALL
                        : LogLevel.NONE)
                .tag("fengao")                                         // 指定 TAG，默认为 "X-LOG"
//                .t()                                                   // 允许打印线程信息，默认禁止
//                .st(1)                                                 // 允许打印深度为2的调用栈信息，默认禁止
//                .b()                                                   // 允许打印日志边框，默认禁止l
                .build();

        Printer androidPrinter = new AndroidPrinter();             // 通过 android.util.Log 打印日志的打印器
//        Printer consolePrinter = new ConsolePrinter();             // 通过 System.out 打印日志到控制台的打印器
        Printer filePrinter = new FilePrinter                      // 打印日志到文件的打印器
                .Builder("/sdcard/xlog/")                              // 指定保存日志文件的路径
                .fileNameGenerator(new DateFileNameGenerator())        // 指定日志文件名生成器，默认为 ChangelessFileNameGenerator("log")
                .backupStrategy(new NeverBackupStrategy())           // 指定日志文件备份策略，默认为 FileSizeBackupStrategy(1024 * 1024)
                .build();

        XLog.init(                                                 // 初始化 XLog
                config,                                                // 指定日志配置，如果不指定，会默认使用 new LogConfiguration.Builder().build()
                androidPrinter,                                        // 添加任意多的打印器。如果没有添加任何打印器，会默认使用 AndroidPrinter(Android)/ConsolePrinter(java)
//                consolePrinter,
                filePrinter);
        Log.i("fengao", "onCreate: ");

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
//                .displayer(new RoundedBitmapDisplayer(20))
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnLoading(R.drawable.zan)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.drawable.zan)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.drawable.zan)
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSizePercentage(20)//设置占用内存的百分比
                .diskCacheFileCount(100)//设置最大下载图片数
                .diskCacheSize(5 * 1024 * 1024)
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(configuration);
//        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
//        ImageLoader.getInstance().init(configuration);
    }
}
