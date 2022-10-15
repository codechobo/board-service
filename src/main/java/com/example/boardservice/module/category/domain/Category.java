package com.example.boardservice.module.category.domain;

import com.example.boardservice.module.post.domain.Post;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(of = "id")
@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "CATEGORIES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @Column(name = "CATEGORIES_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CATEGORIES_NAME", nullable = false, unique = true)
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Category> childCategories = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public void addPost(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post Entity is Null!!");
        }
        this.post = post;
        post.getCategories().add(this);
    }
    public void addParentCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category Entity is Null!!");
        }
        this.parent = category;
    }

    public void addChildCategories(Category category) {
        this.childCategories.add(category);
        category.addParentCategory(this);
    }

}
