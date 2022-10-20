package com.example.boardservice.module.category.domain;

import com.example.boardservice.module.base.TimeEntity;
import com.example.boardservice.module.post.domain.Post;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = "category-entity-graph-with-post",
        attributeNodes = {
                @NamedAttributeNode("parent"),
                @NamedAttributeNode(value = "posts", subgraph = "post-entity-graph")}
        , subgraphs = {
                @NamedSubgraph(
                        name = "post-entity-graph",
                        attributeNodes = {
                                @NamedAttributeNode("category")
                        }
                )})
@Entity
@Getter
@AllArgsConstructor
@Table(name = "CATEGORIES")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends TimeEntity {

    @Id
    @Column(name = "CATEGORIES_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CATEGORIES_NAME", nullable = false, unique = true)
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_CATEGORIES_ID")
    private Category parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Category> childCategories = new ArrayList<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Category(String categoryName) {
        this.categoryName = categoryName;
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
