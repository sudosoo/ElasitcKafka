package com.sudosoo.takeItEasy.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private boolean isDeleted = false;

    public Category(String title) {
        this.title = title;
    }

    protected Category() {
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Category)) return false;
        final Category other = (Category) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$categoryName = this.getTitle();
        final Object other$categoryName = other.getTitle();
        if (this$categoryName == null ? other$categoryName != null : !this$categoryName.equals(other$categoryName))
            return false;
        if (this.isDeleted() != other.isDeleted()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Category;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $categoryName = this.getTitle();
        result = result * PRIME + ($categoryName == null ? 43 : $categoryName.hashCode());
        result = result * PRIME + (this.isDeleted() ? 79 : 97);
        return result;
    }

    public void updateTitle(String title) {
        this.title = title;
    }
}
