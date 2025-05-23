package com.vladmihalcea.hpjp.hibernate.query.hierarchical;

import com.vladmihalcea.hpjp.spring.data.recursive.domain.PostCommentDTO;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vlad Mihalcea
 */
@Entity(name = "PostComment")
public class PostComment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private PostComment parent;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Transient
    private List<PostComment> children = new ArrayList<>();

    public PostComment() {
    }

    public PostComment(String value, Status status) {
        this.description = value;
        this.status = status;
    }

    public PostComment getParent() {
        return parent;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public List<PostComment> getChildren() {
        return children;
    }

    public void addChild(PostComment child) {
        children.add(child);
        child.parent = this;
    }

    public PostComment getRoot() {
        if(parent != null) {
            return parent.getRoot();
        }
        return this;
    }
}
