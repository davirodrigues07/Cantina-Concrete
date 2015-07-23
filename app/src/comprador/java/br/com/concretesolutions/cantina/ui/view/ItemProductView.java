package br.com.concretesolutions.cantina.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.ui.utils.RoundedTransformation;

@EViewGroup(R.layout.item_product_layout)
public class ItemProductView extends FrameLayout {

    private static ItemProductView mSingleton;
    private static Context mContext;
    @ViewById(R.id.image_product_list_item)
    ImageView imageProduct;
    @ViewById(R.id.name_product_list_item)
    TextView nameProduct;
    @ViewById(R.id.amount_product_list_item)
    TextView amountProduct;
    @ViewById(R.id.buy)
    Button buy;
    private OnClickItemButtonListener mButtonListener;

    public ItemProductView(Context context) {
        super(context);
        mContext = context;
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
     * Method for implements singleton
     *
     * @param context
     * @return ItemProductView
     */
    public static ItemProductView getDefault(Context context) {
        // Instantiate singleton if context different of older context
        if (mSingleton == null && mContext == context) {
            mSingleton = new ItemProductView(context);
        }
        return mSingleton;
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
