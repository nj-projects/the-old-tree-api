package me.nolanjames.theoldtreeapi.category;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.nolanjames.theoldtreeapi.product.Product;
import me.nolanjames.theoldtreeapi.shared.BaseEntity;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {

    @Column(unique = true)
    private String name;
    private String description;
    @UuidGenerator
    private String publicId;

    @ManyToMany(mappedBy = "categories", cascade = {CascadeType.MERGE})
    private List<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name) && Objects.equals(description, category.description) && Objects.equals(publicId, category.publicId) && Objects.equals(products, category.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, publicId, products);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", publicId='" + publicId + '\'' +
                ", products=" + products +
                '}';
    }
}
