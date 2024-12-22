package com.tmall.domain.service.item;

import com.tmall.domain.entity.cart.Cart;
import com.tmall.domain.entity.cart.item.Item;
import com.tmall.domain.entity.cart.item.VasItem;
import com.tmall.domain.entity.product.Product;
import com.tmall.domain.exception.item.ItemNotFoundException;
import com.tmall.domain.repository.item.ItemRepository;
import com.tmall.domain.repository.item.VasItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final VasItemRepository vasItemRepository;

    public Item findById(Long id) {
        return itemRepository.findItemByIdAndDeletedFalse(id).orElseThrow(() -> new ItemNotFoundException("Item not found with Id:" + id));
    }

    public Item createItem(Cart cart, Product product, int quantity) {
        Item item = new Item();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);
        return itemRepository.save(item);
    }

    public void createVasItem(Item item, Product product, int quantity) {
        VasItem vasItem = new VasItem();
        vasItem.setItem(item);
        vasItem.setProduct(product);
        vasItem.setQuantity(quantity);
        vasItemRepository.save(vasItem);
    }

    public void updateItemQuantity(Item item, int quantity) {
        item.setQuantity(item.getQuantity() + quantity);
        itemRepository.save(item);
    }

    public void updateVasItemQuantity(VasItem vasItem, int quantity) {
        vasItem.setQuantity(vasItem.getQuantity() + quantity);
        vasItemRepository.save(vasItem);
    }

    public void deleteItem(Item item) {
        itemRepository.delete(item.getId());
    }
}