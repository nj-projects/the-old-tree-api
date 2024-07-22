package me.nolanjames.theoldtreeapi.product.service;

import lombok.RequiredArgsConstructor;
import me.nolanjames.theoldtreeapi.category.service.CategoryMapper;
import me.nolanjames.theoldtreeapi.product.Image;
import me.nolanjames.theoldtreeapi.product.Product;
import me.nolanjames.theoldtreeapi.product.dto.ImageRequest;
import me.nolanjames.theoldtreeapi.product.dto.ImageResponse;
import me.nolanjames.theoldtreeapi.product.dto.ProductRequest;
import me.nolanjames.theoldtreeapi.product.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;

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
                        productRequest.images() != null
                                ? productRequest.images().stream()
                                .map(this::toImage)
                                .toList()
                                : Collections.emptyList()
                )
//                .categories(
//                        productRequest.categories() != null
//                                ? productRequest.categories().stream()
//                                .peek(categoryRequest -> System.out.println(categoryRequest.name()))
//                                .map(categoryMapper::toCategory)
//                                .collect(Collectors.toSet())
//                                : Collections.emptySet()
//                )
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
