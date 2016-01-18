package br.com.concretesolutions.cantina.ui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.ui.utils.RoundedTransformation;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemProductView extends FrameLayout {

    private static Context mContext;
    @Bind(R.id.image_product_list_item)
    ImageView imageProduct;
    @Bind(R.id.name_product_list_item)
    TextView nameProduct;
    @Bind(R.id.amount_product_list_item)
    TextView amountProduct;
    @Bind(R.id.buy)

    Button buy;
    private OnClickItemButtonListener mButtonListener;

    public ItemProductView(Context context) {
        super(context);
        mContext = context;
        View root = LayoutInflater.from(context).inflate(R.layout.item_product_layout, this);
        ButterKnife.bind(root);
    }

    /**
     * Prepare each list item
     *
     * @param product
     */
    public void bind(final Product product) {
        nameProduct.setText(product.getName());
        amountProduct.setText(product.getAmount());
        Picasso.with(mContext).load(product.getImage().getUrl())
                .centerCrop()
                .transform(new RoundedTransformation(100, 20))
                .resize(160, 160)
                .into(imageProduct);

        if (mButtonListener != null) {
            buy.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mButtonListener.onClickedItemButton(product);
                }
            });
        }
    }

    /**
     * Set listener of the click button of each item
     *
     * @param listener
     * @return
     */
    public ItemProductView setClickItemButtonListener(OnClickItemButtonListener listener) {
        mButtonListener = listener;
        return this;
    }

    public ImageView getImageProductTextView() {
        return imageProduct;
    }

    public TextView getNameProductTextView() {
        return nameProduct;
    }

    public TextView getAmountProductTextView() {
        return amountProduct;
    }

    public Button getGetBuyButton() {
        return buy;
    }

    /**
     * Listener for click button of each item
     */
    public interface OnClickItemButtonListener {
        void onClickedItemButton(Product product);
    }
}
