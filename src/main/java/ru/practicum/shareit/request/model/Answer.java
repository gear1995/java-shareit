package ru.practicum.shareit.request.model;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.connector.Request;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "answers", schema = "public")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @JoinColumn(name = "request_id", nullable = false)
    private Request request;
}