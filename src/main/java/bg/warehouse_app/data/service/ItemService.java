package bg.warehouse_app.data.service;

import bg.warehouse_app.common.exception.ItemCreationException;
import bg.warehouse_app.common.exception.ItemUpdateException;
import bg.warehouse_app.common.util.StringUtil;
import bg.warehouse_app.data.dto.CreateItemDTO;
import bg.warehouse_app.data.dto.ItemDTO;
import bg.warehouse_app.data.entity.Item;
import bg.warehouse_app.data.mapper.ItemMapper;
import bg.warehouse_app.data.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    public List<ItemDTO> search(String name, String code, String category) {
        List<Item> searchResults = itemRepository.search(name, code, StringUtil.matchCategoryByLabel(category));
        return searchResults.stream().map(itemMapper::toDTO).toList();
    }

    public List<ItemDTO> findAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream().map(itemMapper::toDTO).toList();
    }

    public void create(CreateItemDTO createItemDTO) {
        if (createItemDTO == null) {
            throw new ItemCreationException("Failed to create item, because the item parameters are null.");
        }

        Item itemToCreate = itemMapper.toEntity(createItemDTO);
        itemRepository.save(itemToCreate);
    }

    public void update(ItemDTO itemDTO) {
        if (Objects.isNull(itemDTO)) {
            throw new ItemUpdateException("Cannot update the item, because it is null.");
        }

        Optional<Item> item = itemRepository.getByCode(itemDTO.getCode());
        if (item.isPresent()) {
            Item foundItem = item.get();

            if (!foundItem.getId().equals(itemDTO.getId())) {
                throw new ItemUpdateException(
                        String.format("Another item with code %s already exists in our database.", itemDTO.getCode()));
            }

            itemMapper.intoEntity(itemDTO, foundItem);
            itemRepository.save(foundItem);
        } else {
            Item itemToUpdate = itemRepository.findById(itemDTO.getId()).orElse(null);
            if (Objects.isNull(itemToUpdate)) {
                throw new ItemUpdateException("This item does not exist in our database.");
            }
            itemMapper.intoEntity(itemDTO, itemToUpdate);
            itemRepository.save(itemToUpdate);
        }
    }

    public void delete(ItemDTO itemDTO) {
        itemRepository.deleteById(itemDTO.getId());
    }

    public ItemDTO getItemByCode(ItemDTO itemDTO) {
        if (Objects.isNull(itemDTO)) {
            return null;
        }
        Optional<Item> item = itemRepository.getByCode(itemDTO.getCode());
        return item.map(itemMapper::toDTO).orElse(null);
    }
}
