package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ExtendedItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public ItemDto create(ItemDto itemDto, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
        return ItemMapper.toItemDto(itemRepository.save(ItemMapper.toItemWithOwner(itemDto, userId)));
    }

    @Override
    @Transactional
    public ItemDto update(Long itemId, ItemDto itemDto, Long userId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
        if (!Objects.equals(item.getOwner(), userId)) {
            throw new NotFoundException("You do not own this item");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        if (itemDto.getRequestId() != null) {
            item.setRequestId(itemDto.getRequestId());
        }

        return ItemMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ExtendedItemDto getItemById(Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));

        List<Booking> startBookings = bookingRepository.findAllByItemIdOrderByStart(item.getId());
        startBookings.stream()
                .filter(booking -> booking.getStart().isAfter(LocalDateTime.now()))
                .findFirst().ifPresent(booking -> item.setNextBooking(booking.getStart()));

        List<Booking> endBooking = bookingRepository.findAllByItemIdOrderByEnd(item.getId());
        endBooking.stream()
                .filter(booking -> !booking.getEnd().isBefore(LocalDateTime.now()))
                .findFirst().ifPresent(booking -> item.setLastBooking(booking.getEnd()));

        List<Comment> comments = commentRepository.findAllByItemId(itemId);
        item.setComments(comments);

        return ItemMapper.toExtendedItemDto(item);
    }

    @Override
    public List<ExtendedItemDto> getUserItems(Long userId) {
        List<Item> items = itemRepository.findAllByOwner(userId);
        items.forEach(item -> {
            List<Booking> startBookings = bookingRepository.findAllByItemIdOrderByEnd(item.getId());
            startBookings.stream()
                    .filter(booking -> !booking.getStart().isAfter(LocalDateTime.now()))
                    .findFirst().ifPresent(booking -> item.setNextBooking(booking.getStart()));

            List<Booking> endBooking = bookingRepository.findAllByItemIdOrderByEnd(item.getId());
            endBooking.stream()
                    .filter(booking -> !booking.getEnd().isBefore(LocalDateTime.now()))
                    .findFirst().ifPresent(booking -> item.setLastBooking(booking.getEnd()));
        });
        return ItemMapper.toExtendedItemDtoList(items);
    }

    @Override
    public List<ItemDto> getItemsBySearchParam(String query) {
        return ItemMapper.toItemDtoList(itemRepository.findAllByNameOrDescriptionContainsIgnoreCaseAndAvailableTrue(query, query));
    }

    @Override
    @Transactional
    public CommentDto addComment(Long itemId, CommentDto commentDto, Long userId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
        User author = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
        List<Booking> bookings = bookingRepository.findAllByItemIdAndBookerIdAndEndIsBefore(itemId, userId, LocalDateTime.now());

        if (!bookings.isEmpty()) {
            Comment comment = Comment.builder()
                    .created(LocalDateTime.now())
                    .author(author)
                    .item(item)
                    .text(commentDto.getText())
                    .build();
            return CommentMapper.toCommentDto(commentRepository.save(comment));
        } else {
            throw new DataIntegrityViolationException("You do not booking this item");
        }
    }
}
