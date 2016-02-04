package br.com.concretesolutions.cantina.presenter;

import java.util.LinkedList;
import java.util.List;

import br.com.concretesolutions.cantina.data.type.parse.Product;


public class ShoppingCartRepository {
    private static List<Product> cart = new LinkedList<>();

    private ShoppingCartRepository() {
    }

    public static boolean add(Product product) {
        return cart.add(product);
    }

    public static boolean remove(Product product) {
        return cart.remove(product);
    }

    public static Product get(int id) {
        return cart.remove(id);
    }

    public static List<Product> all() {
        return cart;
    }

    public static int size() {
        return cart.size();
    }

    public static void clear() {
        cart.clear();
    }

}

