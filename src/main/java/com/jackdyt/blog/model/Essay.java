package com.jackdyt.blog.model;

import java.io.Serializable;

public class Essay implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column essay.id
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column essay.title
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column essay.gmt_create
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    private Long gmtCreate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column essay.gmt_modified
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    private Long gmtModified;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column essay.creator
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    private Long creator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column essay.comment_count
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    private Integer commentCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column essay.view_count
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    private Integer viewCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column essay.like_count
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    private Integer likeCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column essay.tag
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    private String tag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column essay.description
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    private String description;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column essay.id
     *
     * @return the value of essay.id
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column essay.id
     *
     * @param id the value for essay.id
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column essay.title
     *
     * @return the value of essay.title
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column essay.title
     *
     * @param title the value for essay.title
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column essay.gmt_create
     *
     * @return the value of essay.gmt_create
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public Long getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column essay.gmt_create
     *
     * @param gmtCreate the value for essay.gmt_create
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column essay.gmt_modified
     *
     * @return the value of essay.gmt_modified
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public Long getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column essay.gmt_modified
     *
     * @param gmtModified the value for essay.gmt_modified
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column essay.creator
     *
     * @return the value of essay.creator
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public Long getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column essay.creator
     *
     * @param creator the value for essay.creator
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public void setCreator(Long creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column essay.comment_count
     *
     * @return the value of essay.comment_count
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column essay.comment_count
     *
     * @param commentCount the value for essay.comment_count
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column essay.view_count
     *
     * @return the value of essay.view_count
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column essay.view_count
     *
     * @param viewCount the value for essay.view_count
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column essay.like_count
     *
     * @return the value of essay.like_count
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column essay.like_count
     *
     * @param likeCount the value for essay.like_count
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column essay.tag
     *
     * @return the value of essay.tag
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public String getTag() {
        return tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column essay.tag
     *
     * @param tag the value for essay.tag
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column essay.description
     *
     * @return the value of essay.description
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column essay.description
     *
     * @param description the value for essay.description
     *
     * @mbg.generated Tue Jul 14 20:25:23 EDT 2020
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}