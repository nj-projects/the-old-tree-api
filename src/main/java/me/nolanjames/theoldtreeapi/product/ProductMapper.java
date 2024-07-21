package me.nolanjames.theoldtreeapi.product;

import lombok.RequiredArgsConstructor;
import me.nolanjames.theoldtreeapi.category.CategoryMapper;
import me.nolanjames.theoldtreeapi.product.dto.ImageRequest;
import me.nolanjames.theoldtreeapi.product.dto.ImageResponse;
import me.nolanjames.theoldtreeapi.product.dto.ProductRequest;
import me.nolanjames.theoldtreeapi.product.dto.ProductResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    public Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .stock(productRequest.stock())
                .isActive(productRequest.isActive())
                .heroImage(toImage(productRequest.heroImage()))
                .images(
                        productRequest.images().stream()
                                .map(this::toImage)
                                .toList()
                )
                .categories(
                        productRequest.categories().stream()
                                .map(categoryMapper::toCategory)
                                .toList()
                )
                .build();
    }

    public ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.isActive(),
                toImageResponse(product.getHeroImage()),
                product.getImages().stream()
                        .map(this::toImageResponse)
                        .toList(),
                product.getCategories().stream()
                        .map(categoryMapper::toCategoryResponse)
                        .toList(),
                product.isOnSale(),
                product.getSalePrice(),
                product.getDiscountPercentage(),
                product.getPublicId()
        );
    }

    public Image toImage(ImageRequest imageRequest) {
        return Image.builder()
                .link(imageRequest.link())
                .build();
    }

    public ImageResponse toImageResponse(Image image) {
        return new ImageResponse(
                image.getLink()
        );
    }

}
