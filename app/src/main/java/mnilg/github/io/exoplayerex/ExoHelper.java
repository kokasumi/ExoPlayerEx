package mnilg.github.io.exoplayerex;

import android.content.Context;

import com.google.android.exoplayer2.util.Util;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/3 14:34
 */
public class ExoHelper {
    private Context mContext;
    private String userAgent;
    private static ExoHelper mInstance;

    private ExoHelper(Context mContext) {
        this.mContext = mContext;
        userAgent = Util.getUserAgent(mContext,mContext.getPackageName());
    }

    public static void init(Context mContext) {
        if(mInstance == null) {
            synchronized (ExoHelper.class) {
                if(mInstance == null) {
                    mInstance = new ExoHelper(mContext);
                }
            }
        }
    }

    public static ExoHelper getInstance() {
        return mInstance;
    }
}
