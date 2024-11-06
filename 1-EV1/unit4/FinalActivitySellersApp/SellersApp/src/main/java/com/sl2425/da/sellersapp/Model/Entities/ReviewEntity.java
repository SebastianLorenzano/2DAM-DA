package com.sl2425.da.sellersapp.Model.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reviews")
public class ReviewEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_id_gen")
    @SequenceGenerator(name = "reviews_id_gen", sequenceName = "reviews_review_id_seq", allocationSize = 1)
    @Column(name = "review_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_detail_id")
    private com.sl2425.da.sellersapp.Model.Entities.OrderDetailEntity orderDetail;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Lob
    @Column(name = "comment")
    private String comment;

    @Column(name = "review_date")
    private LocalDate reviewDate;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public com.sl2425.da.sellersapp.Model.Entities.OrderDetailEntity getOrderDetail()
    {
        return orderDetail;
    }

    public void setOrderDetail(com.sl2425.da.sellersapp.Model.Entities.OrderDetailEntity orderDetail)
    {
        this.orderDetail = orderDetail;
    }

    public Integer getRating()
    {
        return rating;
    }

    public void setRating(Integer rating)
    {
        this.rating = rating;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public LocalDate getReviewDate()
    {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate)
    {
        this.reviewDate = reviewDate;
    }

}