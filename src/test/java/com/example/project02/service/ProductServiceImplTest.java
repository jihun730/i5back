package com.example.project02.service;

import com.example.project02.DTO.ProductDTO;
import com.example.project02.entity.Product;
import com.example.project02.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        // 모의 데이터 설정
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(Product.builder()
                .id(1L)
                .name("Product 1")
                .description("Description 1")
                .price(10.0)
                .code("Code1")
                .quantity(5)
                .fquantity(3)
                .build());
        mockProducts.add(Product.builder()
                .id(2L)
                .name("Product 2")
                .description("Description 2")
                .price(15.0)
                .code("Code2")
                .quantity(8)
                .fquantity(4)
                .build());

        when(productRepository.findAll()).thenReturn(mockProducts);

        // 테스트 수행
        List<ProductDTO> result = productService.getAllProducts();

        // 검증
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("Product 2", result.get(1).getName());
    }

    @Test
    public void testGetProductById() {
        // 모의 데이터 설정
        Long productId = 1L;
        Product mockProduct = Product.builder()
                .id(productId)
                .name("Product 1")
                .description("Description 1")
                .price(10.0)
                .code("Code1")
                .quantity(5)
                .fquantity(3)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // 테스트 수행
        ProductDTO result = productService.getProductById(productId);

        // 검증
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Product 1", result.getName());
    }

    @Test
    public void testGetProductByName() {
        // 모의 데이터 설정
        String productName = "Product 2";
        Product mockProduct = Product.builder()
                .id(2L)
                .name(productName)
                .description("Description 2")
                .price(15.0)
                .code("Code2")
                .quantity(8)
                .fquantity(4)
                .build();

        when(productRepository.findByName(productName)).thenReturn(Optional.of(mockProduct));

        // 테스트 수행
        ProductDTO result = productService.getProductByName(productName);

        // 검증
        assertNotNull(result);
        assertEquals(productName, result.getName());
    }

    @Test
    public void testCreateProduct() {
        // 모의 데이터 설정
        ProductDTO requestProductDTO = ProductDTO.builder()
                .name("New Product")
                .description("New Description")
                .price(20.0)
                .code("NewCode")
                .quantity(10)
                .fquantity(6)
                .build();

        Product createdProduct = Product.builder()
                .id(3L)
                .name(requestProductDTO.getName())
                .description(requestProductDTO.getDescription())
                .price(requestProductDTO.getPrice())
                .code(requestProductDTO.getCode())
                .quantity(requestProductDTO.getQuantity())
                .fquantity(requestProductDTO.getFquantity())
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(createdProduct);

        // 테스트 수행
        ProductDTO result = productService.createProduct(requestProductDTO);

        // 검증
        assertNotNull(result);
        assertEquals(requestProductDTO.getName(), result.getName());
    }

    @Test
    public void testUpdateProduct() {
        // 모의 데이터 설정
        Long productId = 1L;
        ProductDTO requestProductDTO = ProductDTO.builder()
                .name("Updated Product")
                .description("Updated Description")
                .price(25.0)
                .code("UpdatedCode")
                .quantity(15)
                .fquantity(8)
                .build();

        Product existingProduct = Product.builder()
                .id(productId)
                .name("Product 1")
                .description("Description 1")
                .price(10.0)
                .code("Code1")
                .quantity(5)
                .fquantity(3)
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // 테스트 수행
        ProductDTO result = productService.updateProduct(productId, requestProductDTO);

        // 검증
        assertNotNull(result);
        assertEquals(requestProductDTO.getName(), result.getName());
    }

    @Test
    public void testDeleteProduct() {
        // 모의 데이터 설정
        Long productId = 1L;
        when(productRepository.existsById(productId)).thenReturn(true);

        // 테스트 수행
        productService.deleteProduct(productId);

        // 검증: 특별한 검증 없음
        verify(productRepository, times(1)).deleteById(productId);
    }
    @Test
    public void testProductNotFound() {
        // 존재하지 않는 상품 조회를 테스트
        Long nonExistentProductId = 100L;
        when(productRepository.findById(nonExistentProductId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.findById(nonExistentProductId));
    }
}
