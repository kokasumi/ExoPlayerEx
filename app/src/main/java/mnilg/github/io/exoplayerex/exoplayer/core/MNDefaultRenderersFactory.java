package mnilg.github.io.exoplayerex.exoplayer.core;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.Renderer;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.text.TextRenderer;

import java.util.ArrayList;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/7 18:40
 */
public class MNDefaultRenderersFactory extends DefaultRenderersFactory {
    public MNDefaultRenderersFactory(Context context) {
        super(context);
    }

    public MNDefaultRenderersFactory(Context context, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager) {
        super(context, drmSessionManager);
    }

    public MNDefaultRenderersFactory(Context context, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, int extensionRendererMode) {
        super(context, drmSessionManager, extensionRendererMode);
    }

    public MNDefaultRenderersFactory(Context context, @Nullable DrmSessionManager<FrameworkMediaCrypto> drmSessionManager, int extensionRendererMode, long allowedVideoJoiningTimeMs) {
        super(context, drmSessionManager, extensionRendererMode, allowedVideoJoiningTimeMs);
    }

    /**
     * 使用自定义{@link MNSubtitleDecoderFactory}替换{@link com.google.android.exoplayer2.text.SubtitleDecoderFactory#DEFAULT}
     * @param context
     * @param output
     * @param outputLooper
     * @param extensionRendererMode
     * @param out
     */
    @Override
    protected void buildTextRenderers(Context context, TextOutput output, Looper outputLooper, int extensionRendererMode, ArrayList<Renderer> out) {
        out.add(new TextRenderer(output,outputLooper,new MNSubtitleDecoderFactory()));
    }
}
