package models;

import java.util.Date;

// ── Review ────────────────────────────────────────────────────────────────────
public class Review {
    private String reviewId;
    private Customer customer;
    private Restaurant restaurant;
    private int rating;        // 1–5
    private String comment;
    private Date date;

    public Review(String reviewId, Customer customer, Restaurant restaurant,
                  int rating, String comment) {
        this.reviewId   = reviewId;
        this.customer   = customer;
        this.restaurant = restaurant;
        this.rating     = Math.min(5, Math.max(1, rating)); // clamp 1–5
        this.comment    = comment;
        this.date       = new Date();
    }

    public void submitReview()  { System.out.println("[Review] Submitted."); }
    public void editReview(String newComment, int newRating) {
        this.comment = newComment;
        this.rating  = newRating;
        System.out.println("[Review] Updated.");
    }
    public void deleteReview()  { System.out.println("[Review] Deleted."); }

    public String getReviewId()      { return reviewId; }
    public int    getRating()        { return rating; }
    public String getComment()       { return comment; }
    public Date   getDate()          { return date; }
    public String getCustomerName()  { return customer.getName(); }

    @Override
    public String toString() {
        return String.format("⭐ %d/5 by %s: \"%s\"", rating, customer.getName(), comment);
    }
}
