package br.com.concretesolutions.cantina.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Sale;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemDebitView extends LinearLayout {

    @Bind(R.id.cell)
    LinearLayout cell;
    @Bind(R.id.month)
    TextView month;
    @Bind(R.id.day)
    TextView day;
    @Bind(R.id.state_icon)
    ImageView stateIcon;
    @Bind(R.id.hour)
    TextView hour;
    @Bind(R.id.detail)
    TextView detail;
    @Bind(R.id.value)
    TextView value;
    Context mContext;

    public ItemDebitView(Context context) {
        super(context);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.item_debit_layout, this);
        ButterKnife.bind(view);

    }

    public void bind(Sale sale) {
        month.setText("fev");
        day.setText("07");
        Picasso.with(mContext)
                .load(R.drawable.paid)
                .placeholder(R.drawable.no_paid)
                .into(stateIcon);
        hour.setText("4:20h");
        detail.setText(sale.getProduct().getName());
        value.setText(sale.getProduct().getPrice());
    }

    public TextView getMonth() {
        return month;
    }

    public TextView getDay() {
        return day;
    }

    public ImageView getStateIcon() {
        return stateIcon;
    }

    public TextView getHour() {
        return hour;
    }

    public TextView getDetail() {
        return detail;
    }

    public TextView getValue() {
        return value;
    }
}

