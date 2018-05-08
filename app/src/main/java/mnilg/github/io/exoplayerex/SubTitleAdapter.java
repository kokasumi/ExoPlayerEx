package mnilg.github.io.exoplayerex;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mnilg.github.io.exoplayerex.exoplayer.model.SrtSubtitle;

/**
 * @author : 李罡
 * @description:
 * @company(开发公司) : 小鹏科技
 * @copyright(版权) : 本文件归属小鹏科技公司所有
 * @date : 2018/5/3 17:49
 */
public class SubTitleAdapter extends RecyclerView.Adapter<SubTitleAdapter.SubTitleViewHolder> {
    private OnClickListener onClickListener;
    private Context context;
    private List<SrtSubtitle> srtSubtitleList;

    public SubTitleAdapter(Context context,OnClickListener onClickListener) {
        this.context = context;
        this.onClickListener = onClickListener;
    }

    public void setSrtSubtitleList(List<SrtSubtitle> srtSubtitleList) {
        this.srtSubtitleList = srtSubtitleList;
    }

    @NonNull
    @Override
    public SubTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        return new SubTitleViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTitleViewHolder holder, int position) {
        final SrtSubtitle srtSubtitle = srtSubtitleList.get(position);
        if(srtSubtitle != null && srtSubtitle.getCue() != null) {
            holder.textView.setText(srtSubtitle.getCue().text);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener != null) {
                        onClickListener.clickItem(srtSubtitle);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return srtSubtitleList != null ? srtSubtitleList.size() : 0;
    }

    public static class SubTitleViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public SubTitleViewHolder(TextView itemView) {
            super(itemView);
            this.textView = itemView;
        }
    }

    public interface OnClickListener{
        void clickItem(SrtSubtitle srtSubtitle);
    }
}
