package com.example.project02.service;

import com.example.project02.DTO.ProductInBoxDTO;
import com.example.project02.entity.Box;
import com.example.project02.entity.Product;
import com.example.project02.entity.ProductInBox;
import com.example.project02.repository.BoxRepository;
import com.example.project02.repository.ProductInBoxRepository;
import com.example.project02.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

class ProductInBoxServiceImplTest {
    @InjectMocks
    private ProductInBoxServiceImpl productInBoxService;

    @Mock
    private ProductInBoxRepository productInBoxRepository;

    @Mock
    private BoxRepository boxRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllProductInBoxes() {
        // 테스트용 데이터 생성
        List<ProductInBox> productInBoxList = new ArrayList<>();
        // productInBoxList에 ProductInBox 엔티티 추가

        when(productInBoxRepository.findAll()).thenReturn(productInBoxList);

        List<ProductInBoxDTO> productInBoxDTOList = productInBoxService.getAllProductInBoxes();

        assertNotNull(productInBoxDTOList);
        assertEquals(0, productInBoxDTOList.size());
    }

    @Test
    public void testGetProductInBoxById() {
        Long id = 1L;
        ProductInBox productInBox = new ProductInBox();
        // productInBox를 생성하고 id 설정

        when(productInBoxRepository.findById(id)).thenReturn(Optional.of(productInBox));

        ProductInBoxDTO productInBoxDTO = productInBoxService.getProductInBoxById(id);

        assertNotNull(productInBoxDTO);
        assertEquals(id, productInBoxDTO.getId());
    }

    @Test
    public void testCreateProductInBox() {
        ProductInBoxDTO productInBoxDTO = ProductInBoxDTO.builder()
                .build();
        // productInBoxDTO를 생성하고 필요한 필드 설정

        ProductInBox productInBox = new ProductInBox();
        // productInBox를 생성하고 필요한 필드 설정

        Box box = new Box();
        // box를 생성하고 필요한 필드 설정

        Product product = new Product();
        // product를 생성하고 필요한 필드 설정

        when(productInBoxRepository.save(any(ProductInBox.class))).thenReturn(productInBox);
        when(boxRepository.findById(anyLong())).thenReturn(Optional.of(box));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        ProductInBoxDTO createdProductInBoxDTO = productInBoxService.createProductInBox(productInBoxDTO);

        assertNotNull(createdProductInBoxDTO);
        // 필요한 검증 추가
    }

    @Test
    public void testUpdateProductInBox() {
        Long id = 1L;

        ProductInBox existingProductInBox = ProductInBox.builder()
                .id(id)
                .box(Box.builder().id(id).build())
                .product(Product.builder().id(id).build())
                .build();

        when(productInBoxRepository.findById(id)).thenReturn(Optional.of(existingProductInBox));
        when(boxRepository.findById(anyLong())).thenReturn(Optional.of(Box.builder().id(id).build()));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(Product.builder().id(id).build()));
        when(productInBoxRepository.save(any(ProductInBox.class))).thenReturn(existingProductInBox);


        ProductInBoxDTO updatedProductInBoxDTO =
                productInBoxService.updateProductInBox(id,
                        ProductInBoxDTO.builder()
                                .id(id)
                                .boxId(id)
                                .productId(id)
                                .build());

        assertNotNull(updatedProductInBoxDTO);
    }

    @Test
    public void testDeleteProductInBox() {
        Long id = 1L;
        ProductInBox existingProductInBox = new ProductInBox();
        // existingProductInBox를 생성하고 필요한 필드 설정

        Box box = new Box();
        // box를 생성하고 필요한 필드 설정

        Product product = new Product();
        // product를 생성하고 필요한 필드 설정

        when(productInBoxRepository.findById(id)).thenReturn(Optional.of(existingProductInBox));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        boolean result = productInBoxService.deleteProductInBox(id);

        assertTrue(result);
    }
}