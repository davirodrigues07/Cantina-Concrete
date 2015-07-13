package br.com.concretesolutions.cantina.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Sale;

public class DebitListAdapter extends RecyclerView.Adapter<DebitListAdapter.DebitViewHolder> {

    private List<Sale> mListSales;
    private int mIntemLayout;
    private Context mContext;

    public DebitListAdapter(Context context, List<Sale> listSales, int itemLayout) {
        mListSales = listSales;
        mIntemLayout = itemLayout;
        mContext = context;
    }

    @Override
    public DebitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mIntemLayout, parent);
        return new DebitViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DebitViewHolder holder, int position) {
        Sale current = mListSales.get(position);
        holder.month.setText(current.getMonth());
        holder.day.setText(current.getDay());
        Picasso.with(mContext)
                .load(current.getPaid() ? R.drawable.paid : R.drawable.no_paid)
                .into(holder.stateIcon);
        holder.hour.setText(current.getHour());
        holder.detail.setText(current.getProduct().getName());
        holder.value.setText(current.getProduct().getAmount());
    }

    @Override
    public int getItemCount() {
        return mListSales.size();
    }

    public static class DebitViewHolder extends RecyclerView.ViewHolder {

        TextView month;
        TextView day;
        ImageView stateIcon;
        TextView hour;
        TextView detail;
        TextView value;

        public DebitViewHolder(View itemView) {
            super(itemView);
            month = (TextView) itemView.findViewById(R.id.month);
            day = (TextView) itemView.findViewById(R.id.day);
            stateIcon = (ImageView) itemView.findViewById(R.id.state_icon);
            hour = (TextView) itemView.findViewById(R.id.hour);
            detail = (TextView) itemView.findViewById(R.id.detail);
            value = (TextView) itemView.findViewById(R.id.value);
        }
    }
}
