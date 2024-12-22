package com.tmall.domain.service.item;

import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.entity.cart.item.VasItem;
import com.tmall.domain.entity.product.Product;
import com.tmall.domain.exception.item.ItemNotFoundException;
import com.tmall.domain.repository.item.ItemRepository;
import com.tmall.domain.repository.item.VasItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private VasItemRepository vasItemRepository;

    private Item item;
    private VasItem vasItem;
    private Product product;
    private Cart cart;

    @Before
    public void setUp() {
        cart = new Cart();
        product = new Product();
        item = new Item();
        item.setId(1L);
        item.setProduct(product);
        item.setQuantity(2);

        vasItem = new VasItem();
        vasItem.setId(1L);
        vasItem.setProduct(product);
        vasItem.setQuantity(1);
    }

    @Test
    public void shouldFindById() {
        Long itemId = 1L;

        when(itemRepository.findItemByIdAndDeletedFalse(itemId)).thenReturn(Optional.of(item));

        Item foundItem = itemService.findById(itemId);

        assertEquals(item, foundItem);
    }

    @Test
    public void shouldThrowExceptionWhenItemNotFound() {
        Long itemId = 1L;

        when(itemRepository.findItemByIdAndDeletedFalse(itemId)).thenReturn(Optional.empty());

        ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, () -> itemService.findById(itemId));

        assertEquals("Item not found with Id:1", exception.getMessage());
    }

    @Test
    public void shouldCreateItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        Item result = itemService.createItem(cart, product, 5);

        assertNotNull(result);
        assertEquals(item.getId(), result.getId());

        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    public void shouldCreateVasItem() {
        when(vasItemRepository.save(any(VasItem.class))).thenReturn(vasItem);

        itemService.createVasItem(item, product, 3);

        verify(vasItemRepository, times(1)).save(any(VasItem.class));
    }

    @Test
    public void shouldUpdateItemQuantity() {
        int additionalQuantity = 3;

        when(itemRepository.save(item)).thenReturn(item);

        itemService.updateItemQuantity(item, additionalQuantity);

        assertEquals(5, item.getQuantity());
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    public void shouldUpdateVasItemQuantity() {
        int additionalQuantity = 2;

        when(vasItemRepository.save(vasItem)).thenReturn(vasItem);

        itemService.updateVasItemQuantity(vasItem, additionalQuantity);

        assertEquals(3, vasItem.getQuantity());
        verify(vasItemRepository, times(1)).save(vasItem);
    }

    @Test
    public void shouldDeleteItem() {
        Long itemId = 1L;
        item.setId(itemId);

        itemService.deleteItem(item);

        verify(itemRepository, times(1)).delete(itemId);
    }
}