package mnilg.github.io.exoplayerex.exoplayer.model;

import com.google.android.exoplayer2.text.Cue;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/8 20:02
 */
public class SrtSubtitle{
    private long beginTime;
    private long endTime;
    private Cue cue;

    public SrtSubtitle() {

    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Cue getCue() {
        return cue;
    }

    public void setCue(Cue cue) {
        this.cue = cue;
    }

    @Override
    public String toString() {
        return "SrtSubtitle{" +
                "beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", cue=" + cue +
                '}';
    }
}
