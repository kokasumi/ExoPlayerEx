package mnilg.github.io.exoplayerex;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.decoder.SimpleDecoder;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MergingMediaSource;
import com.google.android.exoplayer2.source.SingleSampleMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SubtitleDecoderFactory;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.text.subrip.SubripDecoder;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.AssetDataSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import mnilg.github.io.exoplayerex.exoplayer.MNDefaultRenderersFactory;
import mnilg.github.io.exoplayerex.utils.Caption;
import mnilg.github.io.exoplayerex.utils.FormatSRT;
import mnilg.github.io.exoplayerex.utils.TimedTextObject;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/3 14:10
 */
public class ExoPlayerActivity extends Activity {
    private PlayerView playerView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);
        playerView = findViewById(R.id.player_view);
        recyclerView = findViewById(R.id.rv_subtitle);
        MNDefaultRenderersFactory renderersFactory = new MNDefaultRenderersFactory(this) {
            @Override
            protected void clickSubtitleText(String text) {
                super.clickSubtitleText(text);
                Toast.makeText(ExoPlayerActivity.this,"点击content:" + text,Toast.LENGTH_LONG).show();
            }
        };
        final SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(renderersFactory,new DefaultTrackSelector());
        playerView.setPlayer(player);
        DataSource.Factory factory = new DataSource.Factory() {
            @Override
            public DataSource createDataSource() {
                return new AssetDataSource(ExoPlayerActivity.this);
            }
        };
        playerView.setUseController(false);
        player.addTextOutput(new TextOutput() {
            @Override
            public void onCues(List<Cue> cues) {
                Log.e("TAG","cues相应Size：" + cues.size());
                for(Cue cue : cues) {
                    Log.e("TAG","position:" + cue.position + ",positionAnchor:" + cue.positionAnchor + ",text" + cue.text);
                }
            }
        });
        player.addListener(new Player.DefaultEventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
                super.onTimelineChanged(timeline, manifest, reason);
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                super.onPlaybackParametersChanged(playbackParameters);
            }
        });
        MediaSource source = new ExtractorMediaSource.Factory(factory)
                .createMediaSource(Uri.parse("assets:///you2go.me .mp4"));
        Format subtitleFormat = Format.createTextSampleFormat("11", MimeTypes.APPLICATION_SUBRIP,Format.NO_VALUE, Util.normalizeLanguageCode("en"));
        Format chSubtitleFormat = Format.createTextSampleFormat("22",MimeTypes.APPLICATION_SUBRIP,Format.NO_VALUE, Util.normalizeLanguageCode("zh"));
        MediaSource srtSource = new SingleSampleMediaSource.Factory(factory)
                .createMediaSource(Uri.parse("assets:///you2go.me.srt")
                        ,subtitleFormat, C.TIME_UNSET);
        MediaSource zhSource = new SingleSampleMediaSource.Factory(factory)
                .createMediaSource(Uri.parse("assets:///you2go.me .zh.srt")
                        ,chSubtitleFormat, C.TIME_UNSET);
        MergingMediaSource mergingMediaSource = new MergingMediaSource(source,srtSource);
        player.prepare(mergingMediaSource);
        player.setPlayWhenReady(true);
        FormatSRT srtFormat = new FormatSRT();
        try {
            TimedTextObject textObject = srtFormat.parseFile("",getAssets().open("you2go.me.srt"));
            TreeMap<Integer,Caption> captionTreeMap = textObject.captions;
            Iterator<Integer> integers = captionTreeMap.keySet().iterator();
            List<Caption> captions = new ArrayList<>();
            while (integers.hasNext()) {
                Caption caption = captionTreeMap.get(integers.next());
                Log.e("TAG",caption.content);
                captions.add(caption);
            }
            SubTitleAdapter mAdapter = new SubTitleAdapter(ExoPlayerActivity.this, new SubTitleAdapter.OnClickListener() {
                @Override
                public void clickItem(Caption caption) {
                    player.seekTo(caption.start.mseconds);
                }
            });
            recyclerView.setAdapter(mAdapter);
            mAdapter.setCaptions(captions);
            recyclerView.setLayoutManager(new LinearLayoutManager(ExoPlayerActivity.this,LinearLayoutManager.VERTICAL,false));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if(playerView.getPlayer() != null) {
            playerView.getPlayer().release();
        }
        super.onDestroy();
    }
}
