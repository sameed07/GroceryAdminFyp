package Model;

public class OrderModel {

    private String address;
    private String phone;
    private String total_item;
    private String total_price;
    private String user_id;
    private String order_id;
    private String order_status;

    public OrderModel() {
    }

    public OrderModel(String address, String phone, String total_item, String total_price,
                      String user_id, String order_id, String order_status) {
        this.address = address;
        this.phone = phone;
        this.total_item = total_item;
        this.total_price = total_price;
        this.user_id = user_id;
        this.order_id = order_id;
        this.order_status = order_status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTotal_item() {
        return total_item;
    }

    public void setTotal_item(String total_item) {
        this.total_item = total_item;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }
}
