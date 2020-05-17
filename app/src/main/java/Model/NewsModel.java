package Model;

public class NewsModel  {

    private String news;
    private String img_url;

    public NewsModel() {
    }

    public NewsModel(String news, String img_url) {
        this.news = news;
        this.img_url = img_url;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
