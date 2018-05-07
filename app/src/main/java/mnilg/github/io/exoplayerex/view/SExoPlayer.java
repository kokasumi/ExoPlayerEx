package mnilg.github.io.exoplayerex.view;

import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.util.Clock;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/7 16:23
 */
public class SExoPlayer extends SimpleExoPlayer {
    protected SExoPlayer(RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl) {
        super(renderersFactory, trackSelector, loadControl);
    }

    protected SExoPlayer(RenderersFactory renderersFactory, TrackSelector trackSelector, LoadControl loadControl, Clock clock) {
        super(renderersFactory, trackSelector, loadControl, clock);
    }
}
