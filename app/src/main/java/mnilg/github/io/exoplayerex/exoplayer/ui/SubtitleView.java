package mnilg.github.io.exoplayerex.exoplayer.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;

import java.util.List;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/8 17:48
 */
public class SubtitleView extends AppCompatTextView implements TextOutput {
    public SubtitleView(Context context) {
        super(context);
    }

    public SubtitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SubtitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onCues(List<Cue> cues) {

    }
}
