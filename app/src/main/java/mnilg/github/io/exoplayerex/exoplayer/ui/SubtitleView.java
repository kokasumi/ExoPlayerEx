package mnilg.github.io.exoplayerex.exoplayer.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/8 17:48
 */
public class SubtitleView extends AppCompatTextView implements TextOutput {
    private List<Cue> cues;
    private SubtitleClickableSpan.SpannableClickCallback clickCallback;

    public SubtitleView(Context context) {
        this(context,null);
    }

    public SubtitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SubtitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void setClickCallback(SubtitleClickableSpan.SpannableClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    @Override
    public void onCues(List<Cue> cues) {
        setCues(cues);
    }

    public void setCues(List<Cue> cues) {
        if (this.cues == cues) {
            return;
        }
        this.cues = cues;
        setSubtitle(cues != null && cues.size() > 0 ? cues.get(0).text : "");
    }

    private void setSubtitle(CharSequence text) {
        setText(text, BufferType.SPANNABLE);
        //Click on Each Word response
        addClickEventOnEachWord();
    }

    private void addClickEventOnEachWord() {
        Spannable spans = (Spannable) getText();
        Integer[] indices = getIndices(
                getText().toString().trim(), ' ');
        int start = 0;
        int end = 0;
        // to cater last/only word loop will run equal to the length of indices.length
        for (int i = 0; i <= indices.length; i++) {
            ClickableSpan clickSpan = new SubtitleClickableSpan().setCallback(clickCallback);
            // to cater last/only word
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
        //Change the highlight color for selected text
        setHighlightColor(Color.BLUE);
    }

    private Integer[] getIndices(String s, char c) {
        int pos = s.indexOf(c, 0);
        List<Integer> indices = new ArrayList<Integer>();
        while (pos != -1) {
            indices.add(pos);
            pos = s.indexOf(c, pos + 1);
        }
        return indices.toArray(new Integer[0]);
    }
}
