package me.nolanjames.theoldtreeapi.product;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import me.nolanjames.theoldtreeapi.category.Category;
import me.nolanjames.theoldtreeapi.shared.BaseEntity;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private boolean isActive;
    private boolean isOnSale;
    private BigDecimal salePrice;
    private int discountPercentage;
    private String publicId;

    @OneToOne
    private Image heroImage;

    @OneToMany()
    private List<Image> images;

    @OneToMany()
    private List<Category> categories;
}
