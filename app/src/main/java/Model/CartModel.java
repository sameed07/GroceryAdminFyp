package Model;

public class CartModel {

    private String title, product_id, price, img_url;
    private int id;

    public CartModel(String product_id, String title, String price, String img_url) {
        this.title = title;
        this.product_id = product_id;
        this.price = price;
        this.img_url = img_url;
        this.id = id;
    }

    public CartModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CartModel(String string) {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
