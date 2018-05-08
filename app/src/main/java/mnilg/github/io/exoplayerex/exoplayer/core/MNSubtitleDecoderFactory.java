package mnilg.github.io.exoplayerex.exoplayer.core;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.text.SubtitleDecoder;
import com.google.android.exoplayer2.text.SubtitleDecoderFactory;
import com.google.android.exoplayer2.util.MimeTypes;

import mnilg.github.io.exoplayerex.exoplayer.core.text.MNSubripDecoder;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/7 18:28
 */
public class MNSubtitleDecoderFactory implements SubtitleDecoderFactory{
    @Override
    public boolean supportsFormat(Format format) {
        return SubtitleDecoderFactory.DEFAULT.supportsFormat(format);
    }

    /**
     * 字幕解析使用自定义的{@link MNSubripDecoder}，否则使用{@link SubtitleDecoderFactory#DEFAULT#createDecoder(Format)}
     * @param format
     * @return
     */
    @Override
    public SubtitleDecoder createDecoder(Format format) {
        if(MimeTypes.APPLICATION_SUBRIP.equals(format.sampleMimeType)) {
            return new MNSubripDecoder();
        }
        return SubtitleDecoderFactory.DEFAULT.createDecoder(format);
    }
}
