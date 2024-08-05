package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "items", schema = "public")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner", nullable = false)
    private Long owner;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @ElementCollection
    private List<Comment> comments;

    private LocalDateTime lastBooking;
    private LocalDateTime nextBooking;
}
