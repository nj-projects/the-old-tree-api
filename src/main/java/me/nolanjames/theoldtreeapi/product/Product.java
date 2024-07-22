package me.nolanjames.theoldtreeapi.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.nolanjames.theoldtreeapi.category.Category;
import me.nolanjames.theoldtreeapi.shared.BaseEntity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    @Column(unique = true)
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private boolean isActive;
    private boolean isOnSale;
    private BigDecimal salePrice;
    private int discountPercentage;
    private String publicId;

    @OneToOne(cascade = ALL)
    private Image heroImage;

    @OneToMany(cascade = ALL)
    private List<Image> images;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Category> categories = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return stock == product.stock && isActive == product.isActive && isOnSale == product.isOnSale && discountPercentage == product.discountPercentage && Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(price, product.price) && Objects.equals(salePrice, product.salePrice) && Objects.equals(publicId, product.publicId) && Objects.equals(heroImage, product.heroImage) && Objects.equals(images, product.images) && Objects.equals(categories, product.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, stock, isActive, isOnSale, salePrice, discountPercentage, publicId, heroImage, images, categories);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", isActive=" + isActive +
                ", isOnSale=" + isOnSale +
                ", salePrice=" + salePrice +
                ", discountPercentage=" + discountPercentage +
                ", publicId='" + publicId + '\'' +
                ", heroImage=" + heroImage +
                ", images=" + images +
                ", categories=" + categories +
                '}';
    }
}
