package mnilg.github.io.exoplayerex.exoplayer.core.helper;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.exoplayer2.text.Cue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mnilg.github.io.exoplayerex.exoplayer.model.SrtSubtitle;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/8 19:32
 */
public class SubtitleHelper {
    private static final String TAG = "SubtitleHelper";
    private static final String SUBRIP_TIMECODE = "(?:(\\d+):)?(\\d+):(\\d+),(\\d+)";
    private static final Pattern SUBRIP_TIMING_LINE =
            Pattern.compile("\\s*(" + SUBRIP_TIMECODE + ")\\s*-->\\s*(" + SUBRIP_TIMECODE + ")?\\s*");
    private final StringBuilder textBuilder;
    private static SubtitleHelper mInstance;

    private SubtitleHelper() {
        textBuilder = new StringBuilder();
    }

    public static SubtitleHelper getInstance() {
        if(mInstance == null) {
            synchronized (SubtitleHelper.class) {
                if(mInstance == null) {
                    mInstance = new SubtitleHelper();
                }
            }
        }
        return mInstance;
    }

    public List<SrtSubtitle> parse(InputStream is) {
        List<SrtSubtitle> srtSubtitleList = new ArrayList<>();
        InputStreamReader in= new InputStreamReader(is);
        BufferedReader br = new BufferedReader(in);
        String currentLine;
        try {
            while((currentLine = br.readLine()) != null) {
                SrtSubtitle srtSubtitle = new SrtSubtitle();
                if(currentLine.length() == 0) {
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
                currentLine = br.readLine();
                if (currentLine == null) {
                    Log.w(TAG, "Unexpected end");
                    break;
                }
                Matcher matcher = SUBRIP_TIMING_LINE.matcher(currentLine);
                if (matcher.matches()) {
                    srtSubtitle.setBeginTime(parseTimecode(matcher, 1));
                    if (!TextUtils.isEmpty(matcher.group(6))) {
                        haveEndTimecode = true;
                        srtSubtitle.setEndTime(parseTimecode(matcher, 6));
                    }
                } else {
                    Log.w(TAG, "Skipping invalid timing: " + currentLine);
                    continue;
                }
                // Read and parse the text.
                textBuilder.setLength(0);
                while (!TextUtils.isEmpty(currentLine = br.readLine())) {
                    if (textBuilder.length() > 0) {
                        textBuilder.append("<br>");
                    }
                    textBuilder.append(currentLine.trim());
                }
                Spanned text = Html.fromHtml(textBuilder.toString());
                srtSubtitle.setCue(new Cue(text));
                srtSubtitleList.add(srtSubtitle);
                if (haveEndTimecode) {
                    srtSubtitleList.add(null);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return srtSubtitleList;
    }

    private static long parseTimecode(Matcher matcher, int groupOffset) {
        long timestampMs = Long.parseLong(matcher.group(groupOffset + 1)) * 60 * 60 * 1000;
        timestampMs += Long.parseLong(matcher.group(groupOffset + 2)) * 60 * 1000;
        timestampMs += Long.parseLong(matcher.group(groupOffset + 3)) * 1000;
        timestampMs += Long.parseLong(matcher.group(groupOffset + 4));
        return timestampMs;
    }
}
