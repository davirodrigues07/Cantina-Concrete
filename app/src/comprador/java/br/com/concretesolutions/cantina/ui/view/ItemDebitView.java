package br.com.concretesolutions.cantina.ui.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Sale;

@EViewGroup(R.layout.item_debit_layout)
public class ItemDebitView extends LinearLayout {

    @ViewById(R.id.month)
    TextView month;
    @ViewById(R.id.day)
    TextView day;
    @ViewById(R.id.state_icon)
    ImageView stateIcon;
    @ViewById(R.id.hour)
    TextView hour;
    @ViewById(R.id.detail)
    TextView detail;
    @ViewById(R.id.value)
    TextView value;
    Context mContext;

    public ItemDebitView(Context context) {
        super(context);
        mContext = context;
    }

    public void bind(Sale sale) {
        month.setText("mars");
        day.setText("22");
        Picasso.with(mContext)
                .load(sale.getPaid() ? R.drawable.paid : R.drawable.no_paid)
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

