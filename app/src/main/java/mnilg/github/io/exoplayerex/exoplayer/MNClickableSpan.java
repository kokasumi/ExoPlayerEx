package mnilg.github.io.exoplayerex.exoplayer;

import android.text.style.ClickableSpan;
import android.view.View;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/7 17:54
 */
public class MNClickableSpan extends ClickableSpan {
    private String text;
    private SpannableClickCallback callback;

    public MNClickableSpan(String text) {
        this.text = text;
    }

    public MNClickableSpan setCallback(SpannableClickCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void onClick(View widget) {
        if(callback != null) {
            callback.clickText(text);
        }
    }

    public interface SpannableClickCallback{
        void clickText(String text);
    }
}
