import { FC, useState } from "react";
import { Input, Textarea, Button } from "@material-tailwind/react";
import * as React from "react";

interface ReviewFormProps {
  onSuccess: (newReview: any) => void;
}

const ReviewForm: FC<ReviewFormProps> = ({ onSuccess }) => {
  const [newReview, setNewReview] = useState({
    userId: "",
    restaurantId: "",
    rating: "",
    comment: "",
  });
  const [submitting, setSubmitting] = useState(false);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setNewReview({ ...newReview, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setSubmitting(true);

    fetch("http:///localhost:8081/reviews/create", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        userId: Number(newReview.userId),
        restaurantId: Number(newReview.restaurantId),
        rating: Number(newReview.rating),
        comment: newReview.comment,
      }),
    })
      .then((res) => {
        if (!res.ok) throw new Error("Failed to submit review");
        return res.json();
      })
      .then((createdReview) => {
        onSuccess(createdReview);
        setNewReview({ userId: "", restaurantId: "", rating: "", comment: "" });
      })
      .catch((err) => alert(err.message))
      .finally(() => setSubmitting(false));
  };

  return (
    <form onSubmit={handleSubmit} className="mb-6 space-y-4">
      <Input
        type="number"
        name="userId"
        label="User ID"
        value={newReview.userId}
        onChange={handleChange}
        required
      />
      <Input
        type="number"
        name="restaurantId"
        label="Restaurant ID"
        value={newReview.restaurantId}
        onChange={handleChange}
        required
      />
      <Input
        type="number"
        step="0.1"
        min="0"
        max="10"
        name="rating"
        label="Rating"
        value={newReview.rating}
        onChange={handleChange}
        required
      />
      <Textarea
        name="comment"
        label="Commento"
        value={newReview.comment}
        onChange={handleChange}
        required
        rows={4}
      />
      <Button type="submit" disabled={submitting}>
        {submitting ? "Invio in corso..." : "Invia recensione"}
      </Button>
    </form>
  );
};

export default ReviewForm;
