package com.fsf.habitup.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dailyThought")
public class DailyThought {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thoughtId", nullable = false, unique = true)
    private Long thoughtId;

    @Column(name = "content", nullable = false)
    private String contentURL;

    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;

    @Column(name = "author", nullable = false)
    private String authorName;

    /**
     * @return Long return the thoughtId
     */
    public Long getThoughtId() {
        return thoughtId;
    }

    /**
     * @param thoughtId the thoughtId to set
     */
    public void setThoughtId(Long thoughtId) {
        this.thoughtId = thoughtId;
    }

    /**
     * @return String return the contentURL
     */
    public String getContentURL() {
        return contentURL;
    }

    /**
     * @param contentURL the contentURL to set
     */
    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    /**
     * @return Date return the updatedAt
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return String return the authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * @param authorName the authorName to set
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

}
