package br.com.concretesolutions.cantina.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.ui.utils.RoundedTransformation;

public class ListProductAdapter extends ArrayAdapter<Product> {

    private Context mContext;
    private List<Product> mProducts;

    public ListProductAdapter(Context context, List<Product> products) {
        super(context, R.layout.list_product_adapter, products);
        mContext = context;
        mProducts = products;
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Product getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.item_product_layout, parent, false);

        ImageView imageProductListItem = (ImageView) convertView.findViewById(R.id.image_product_list_item);
        TextView amountProductListItem = (TextView) convertView.findViewById(R.id.amount_product_list_item);
        TextView nameProductListItem = (TextView) convertView.findViewById(R.id.name_product_list_item);

        amountProductListItem.setText(product.getAmount());
        nameProductListItem.setText(product.getName());

        Picasso.with(convertView.getContext())
                .load(product.getImage().getUrl())
                .transform(new RoundedTransformation(100, 20))
                .resize(160, 160)
                .centerCrop()
                .into(imageProductListItem);

        return convertView;
    }
}