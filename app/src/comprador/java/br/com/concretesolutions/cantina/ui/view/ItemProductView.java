package br.com.concretesolutions.cantina.ui.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import br.com.concretesolutions.cantina.R;
import br.com.concretesolutions.cantina.data.type.parse.Product;
import br.com.concretesolutions.cantina.presenter.ShoppingCartRepository;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemProductView extends FrameLayout {

    private static Context mContext;
    @Bind(R.id.name_product_list_item)
    TextView nameProduct;
    @Bind(R.id.price_product_list_item)
    TextView priceProduct;
    @Bind(R.id.buy)
    Button buy;
    @Bind(R.id.minus)
    TextView minus;
    @Bind(R.id.plus)
    TextView plus;
    @Bind(R.id.total)
    TextView textTotal;
    private int total;

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
        priceProduct.setText(product.getPrice());

        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (total > 0) {
                    total--;
                    ShoppingCartRepository.remove(product);
                }
                textTotal.setText(String.valueOf(total));
                 Log.d("app", "size: "+ShoppingCartRepository.size());
            }
        });

        plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                total++;
                ShoppingCartRepository.add(product);
                textTotal.setText(String.valueOf(total));
                Log.d("app", "size: " + ShoppingCartRepository.size());
            }
        });

       /* if (mButtonListener != null) {
            buy.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mButtonListener.onClickedItemButton(listProduct);
                    listProduct.clear();
                }
            });


        }*/
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

    public TextView getNameProductTextView() {
        return nameProduct;
    }

    public TextView getPriceProductTextView() {
        return priceProduct;
    }

    public Button getGetBuyButton() {
        return buy;
    }

    /**
     * Listener for click button of each item
     */
    public interface OnClickItemButtonListener {
        void onClickedItemButton(List<Product> product);
    }
}
