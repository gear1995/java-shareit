package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOwner(long userId);

    @Query(value = "select item " +
                   "from Item as item " +
                   "where (item.name ilike ?1 " +
                   "or item.description ilike ?2) " +
                   "and item.available = true")
    List<Item> findAllByNameOrDescriptionContainsIgnoreCaseAndAvailableTrue(String name, String description);
}
