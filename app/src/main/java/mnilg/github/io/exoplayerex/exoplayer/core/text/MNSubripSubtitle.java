package mnilg.github.io.exoplayerex.exoplayer.core.text;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.util.Collections;
import java.util.List;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/7 18:10
 */
public class MNSubripSubtitle implements Subtitle {
    private final Cue[] cues;
    private final long[] cueTimesUs;

    /**
     * @param cues       The cues in the subtitle. Null entries may be used to represent empty cues.
     * @param cueTimesUs The cue times, in microseconds.
     */
    public MNSubripSubtitle(Cue[] cues, long[] cueTimesUs) {
        this.cues = cues;
        this.cueTimesUs = cueTimesUs;
    }

    @Override
    public int getNextEventTimeIndex(long timeUs) {
        int index = Util.binarySearchCeil(cueTimesUs, timeUs, false, false);
        return index < cueTimesUs.length ? index : C.INDEX_UNSET;
    }

    @Override
    public int getEventTimeCount() {
        return cueTimesUs.length;
    }

    @Override
    public long getEventTime(int index) {
        Assertions.checkArgument(index >= 0);
        Assertions.checkArgument(index < cueTimesUs.length);
        return cueTimesUs[index];
    }

    @Override
    public List<Cue> getCues(long timeUs) {
        int index = Util.binarySearchFloor(cueTimesUs, timeUs, true, false);
        if (index == -1 || cues[index] == null) {
            // timeUs is earlier than the start of the first cue, or we have an empty cue.
            return Collections.emptyList();
        } else {
            return Collections.singletonList(cues[index]);
        }
    }
}
