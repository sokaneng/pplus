package io.fireant.pplus.dto;

/**
 * Created by engsokan on 8/9/18.
 */

public class ProductDTO {
    private String id;
    private String name;
    private String categoryType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
}
