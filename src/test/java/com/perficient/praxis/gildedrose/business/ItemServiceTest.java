package com.perficient.praxis.gildedrose.business;

import com.perficient.praxis.gildedrose.error.ResourceNotFoundException;
import com.perficient.praxis.gildedrose.model.Item;
import com.perficient.praxis.gildedrose.repository.ItemRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ItemServiceTest {

    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;


    @Test
    public void testGetItemByIdWhenItemWasNotFound(){

        when(itemRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                itemService.findById(0));
    }

    @Test
    public void testGetItemByIdSuccess(){

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));

        Item itemFound = itemService.findById(0);
        assertEquals(item, itemFound);
    }

    @Test
    /**
     * GIVEN a valid normal type item in the database
     * WHEN updateQuality method is called
     * THEN the service should update the quality and sellIn values,
     * both will be decreased by 1
     */
    public void testUpdateQualityOfNormalTypeItem(){

        var item = new Item(0, "Oreo", 10, 30, Item.Type.NORMAL);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Oreo", itemsUpdated.get(0).name);
        assertEquals(9, itemsUpdated.get(0).sellIn);
        assertEquals(29, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.NORMAL, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testUpdateItemWhenItemWasNotFound(){
        var item = new Item(6, "Ice Cream", 10, 30, Item.Type.AGED);
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(item));

        assertThrows(ResourceNotFoundException.class, () ->
                itemService.updateItem(23, item));
    }

    @Test
    public void testUpdateQualityOfAgedTypeItem(){

        var item = new Item(0, "Oreo2", 10, 30, Item.Type.AGED);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Oreo2", itemsUpdated.get(0).name);
        assertEquals(9, itemsUpdated.get(0).sellIn);
        assertEquals(31, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.AGED, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }

    @Test
    public void testUpdateQualityOfTicketTypeItem(){

        var item = new Item(0, "Other cookie", 5, 30, Item.Type.TICKETS);
        when(itemRepository.findAll()).thenReturn(List.of(item));

        List<Item> itemsUpdated = itemService.updateQuality();

        assertEquals(0, itemsUpdated.get(0).getId());
        assertEquals("Other cookie", itemsUpdated.get(0).name);
        assertEquals(4, itemsUpdated.get(0).sellIn);
        assertEquals(33, itemsUpdated.get(0).quality);
        assertEquals(Item.Type.TICKETS, itemsUpdated.get(0).type);
        verify(itemRepository,times(1)).save(any());
    }







    }
