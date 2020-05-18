package Model;

public class CategoryModel  {

    private String id;
    private String category_img;
    private String category_title;
    private String category_desc;

    public CategoryModel() {
    }

    public CategoryModel(String id, String category_img, String category_title, String category_desc) {
        this.id = id;
        this.category_img = category_img;
        this.category_title = category_title;
        this.category_desc = category_desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_img() {
        return category_img;
    }

    public void setCategory_img(String category_img) {
        this.category_img = category_img;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public String getCategory_desc() {
        return category_desc;
    }

    public void setCategory_desc(String category_desc) {
        this.category_desc = category_desc;
    }
}
