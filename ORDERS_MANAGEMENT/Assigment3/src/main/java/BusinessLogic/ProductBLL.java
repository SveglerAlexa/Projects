package BusinessLogic;

import DataAccess.ProductDAO;
import Model.Product;

import java.util.List;

/**
 * Business logic layer for managing products.
 * Provides methods to insert, update, delete, and retrieve client data.
 */
public class ProductBLL {

    private ProductDAO productDAO;

    public ProductBLL() {
        this.productDAO = new ProductDAO();
    }

    public void insert(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }

        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price must be non-negative.");
        }

        if (product.getQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity must be non-negative.");
        }

        productDAO.insert(product);
    }

    public void update(Product product) {
        productDAO.update(product);
    }

    public void delete(Product product) {
        productDAO.delete(product);
    }

    public Product findById(int id) {
        return productDAO.findById(id);
    }

    public List<Product> findAll() {
        return productDAO.findAll();
    }


}
