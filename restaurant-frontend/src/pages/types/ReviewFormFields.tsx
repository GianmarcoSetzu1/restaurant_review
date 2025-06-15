import {Input, Textarea} from "./FormControls.tsx";
import AutoCompleteInput from "../review/AutoCompleteInput.tsx";
import * as React from "react";

export interface Review {
    restaurantId: string;
    rating: string;
    comment: string;
}

export interface Restaurant {
    id: number;
    name: string;
}

export interface ReviewFormFieldsProps {
    query: string;
    setQuery: React.Dispatch<React.SetStateAction<string>>;
    newReview: Review;
    setNewReview: React.Dispatch<React.SetStateAction<Review>>;
    suggestions: Restaurant[];
    showDropdown: boolean;
    setShowDropdown: React.Dispatch<React.SetStateAction<boolean>>;
    onCreateNew: () => void;
}

const ReviewFormFields: React.FC<ReviewFormFieldsProps> = ({
                                                               query,
                                                               setQuery,
                                                               newReview,
                                                               setNewReview,
                                                               suggestions,
                                                               showDropdown,
                                                               setShowDropdown,
                                                               onCreateNew,
                                                           }) => (
    <>
        <AutoCompleteInput
            value={query}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                setQuery(e.target.value);
                setNewReview((prev) => ({...prev, restaurantId: ""}));
            }}
            onSelect={(restaurant: Restaurant) => {
                setQuery(restaurant.name);
                setNewReview((prev) => ({...prev, restaurantId: restaurant.id.toString()}));
            }}
            onCreateNew={onCreateNew}
            suggestions={suggestions}
            showDropdown={showDropdown}
            setShowDropdown={setShowDropdown}
        />
        <Input
            type="number"
            step="0.1"
            min="0"
            max="10"
            name="rating"
            label="Rating"
            placeholder="Valutazione da 0 a 10"
            value={newReview.rating}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => setNewReview({...newReview, rating: e.target.value})}
            required
        />
        <Textarea
            name="comment"
            label="Commento"
            placeholder="Scrivi il tuo commento..."
            value={newReview.comment}
            onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) => setNewReview({
                ...newReview,
                comment: e.target.value
            })}
            required
            rows={4}
        />
    </>
);
export default ReviewFormFields;
