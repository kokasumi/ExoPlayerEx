package mnilg.github.io.exoplayerex.exoplayer.ui;

import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/7 17:54
 */
public class SubtitleClickableSpan extends ClickableSpan {
    private SpannableClickCallback callback;

    public SubtitleClickableSpan() {
    }

    public SubtitleClickableSpan setCallback(SpannableClickCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void onClick(View widget) {
        String text = "";
        if(callback != null) {
            if(widget instanceof TextView) {
                TextView textView = (TextView) widget;
                text = textView.getText().subSequence(textView.getSelectionStart(),textView.getSelectionEnd()).toString();
            }
            callback.clickText(text);
        }
    }

    public interface SpannableClickCallback{
        void clickText(String text);
    }
}
