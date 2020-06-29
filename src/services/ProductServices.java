package services;

import dao.ProductDao;
import model.OrderItem;
import model.Product;

import java.util.List;

public class ProductServices {

    public void getASoledItemBack(OrderItem orderItem) {
        ProductDao productDao = new ProductDao();
        Product product = orderItem.getProduct();

        product.setStock(orderItem.getCount() + product.getStock());
        productDao.updateProduct(product);
    }

    public void getAllSoledItemsBack(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            getASoledItemBack(orderItem);
        }
    }
}