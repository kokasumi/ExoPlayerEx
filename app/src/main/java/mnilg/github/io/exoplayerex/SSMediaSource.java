package mnilg.github.io.exoplayerex;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.upstream.Allocator;

import java.io.IOException;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/5 11:40
 */
public class SSMediaSource implements MediaSource {
    @Override
    public void prepareSource(ExoPlayer player, boolean isTopLevelSource, Listener listener) {

    }

    @Override
    public void maybeThrowSourceInfoRefreshError() throws IOException {

    }

    @Override
    public MediaPeriod createPeriod(MediaPeriodId id, Allocator allocator) {
        return null;
    }

    @Override
    public void releasePeriod(MediaPeriod mediaPeriod) {

    }

    @Override
    public void releaseSource() {

    }
}
