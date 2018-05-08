package mnilg.github.io.exoplayerex.exoplayer.core.text;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.ParsableByteArray;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : 李罡
 * @description: 自定义字幕解析器
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/7 16:38
 */
public class MNSubripDecoder extends SimpleSubtitleDecoder {
    private static final String TAG = "MNSubripDecoder";
    private static final String SUBRIP_TIMECODE = "(?:(\\d+):)?(\\d+):(\\d+),(\\d+)";
    private static final Pattern SUBRIP_TIMING_LINE =
            Pattern.compile("\\s*(" + SUBRIP_TIMECODE + ")\\s*-->\\s*(" + SUBRIP_TIMECODE + ")?\\s*");
    private final StringBuilder textBuilder;

    public MNSubripDecoder() {
        super("MNSubripDecoder");
        textBuilder = new StringBuilder();
    }

    @Override
    protected Subtitle decode(byte[] data, int size, boolean reset) throws SubtitleDecoderException {
        ArrayList<Cue> cues = new ArrayList<>();
        LongArray cueTimesUs = new LongArray();
        ParsableByteArray subripData = new ParsableByteArray(data, size);
        String currentLine;
        while ((currentLine = subripData.readLine()) != null) {
            if (currentLine.length() == 0) {
                // Skip blank lines.
                continue;
            }
            // Parse the index line as a sanity check.
            try {
                Integer.parseInt(currentLine);
            } catch (NumberFormatException e) {
                Log.w(TAG, "Skipping invalid index: " + currentLine);
                continue;
            }
            // Read and parse the timing line.
            boolean haveEndTimecode = false;
            currentLine = subripData.readLine();
            if (currentLine == null) {
                Log.w(TAG, "Unexpected end");
                break;
            }
            Matcher matcher = SUBRIP_TIMING_LINE.matcher(currentLine);
            if (matcher.matches()) {
                cueTimesUs.add(parseTimecode(matcher, 1));
                if (!TextUtils.isEmpty(matcher.group(6))) {
                    haveEndTimecode = true;
                    cueTimesUs.add(parseTimecode(matcher, 6));
                }
            } else {
                Log.w(TAG, "Skipping invalid timing: " + currentLine);
                continue;
            }
            // Read and parse the text.
            textBuilder.setLength(0);
            while (!TextUtils.isEmpty(currentLine = subripData.readLine())) {
                if (textBuilder.length() > 0) {
                    textBuilder.append("<br>");
                }
                textBuilder.append(currentLine.trim());
            }
            Spanned text = Html.fromHtml(textBuilder.toString());
            cues.add(new Cue(text));
            if (haveEndTimecode) {
                cues.add(null);
            }
        }
        Cue[] cuesArray = new Cue[cues.size()];
        cues.toArray(cuesArray);
        long[] cueTimesUsArray = cueTimesUs.toArray();
        return new MNSubripSubtitle(cuesArray, cueTimesUsArray);
    }

    private static long parseTimecode(Matcher matcher, int groupOffset) {
        long timestampMs = Long.parseLong(matcher.group(groupOffset + 1)) * 60 * 60 * 1000;
        timestampMs += Long.parseLong(matcher.group(groupOffset + 2)) * 60 * 1000;
        timestampMs += Long.parseLong(matcher.group(groupOffset + 3)) * 1000;
        timestampMs += Long.parseLong(matcher.group(groupOffset + 4));
        return timestampMs * 1000;
    }
}
