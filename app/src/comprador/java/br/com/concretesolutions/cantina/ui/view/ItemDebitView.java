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

public class ItemDebitView extends LinearLayout {


    LinearLayout cell;
    TextView month;
    TextView day;
    ImageView stateIcon;
    TextView hour;
    TextView detail;
    TextView value;
    Context mContext;

    public ItemDebitView(Context context) {
        super(context);
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.item_debit_layout, this);
        cell = (LinearLayout) view.findViewById(R.id.cell);
        month = (TextView) view.findViewById(R.id.month);
        day = (TextView) view.findViewById(R.id.day);
        stateIcon = (ImageView) view.findViewById(R.id.state_icon);
        hour = (TextView) view.findViewById(R.id.hour);
        detail = (TextView) view.findViewById(R.id.detail);
        value = (TextView) view.findViewById(R.id.value);
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

