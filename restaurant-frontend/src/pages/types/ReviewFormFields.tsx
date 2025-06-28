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
    partialName: string;
    setPartialName: React.Dispatch<React.SetStateAction<string>>;
    newReview: Review;
    setNewReview: React.Dispatch<React.SetStateAction<Review>>;
    fetchSuggestions: (reset: boolean) => Promise<void>;
    suggestions: Restaurant[];
    hasMore: boolean;
    showDropdown: boolean;
    setShowDropdown: React.Dispatch<React.SetStateAction<boolean>>;
    onCreateNew: () => void;
}

const ReviewFormFields: React.FC<ReviewFormFieldsProps> = ({
                                                               partialName,
                                                               setPartialName,
                                                               newReview,
                                                               setNewReview,
                                                               fetchSuggestions,
                                                               suggestions,
                                                               hasMore,
                                                               showDropdown,
                                                               setShowDropdown,
                                                               onCreateNew,
                                                           }) => (
    <>
        <AutoCompleteInput
            value={partialName}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
                setPartialName(e.target.value);
                setNewReview((prev) => ({...prev, restaurantId: ""}));
            }}
            onSelect={(restaurant: Restaurant) => {
                setPartialName(restaurant.name);
                setNewReview((prev) => ({...prev, restaurantId: restaurant.id.toString()}));
            }}
            onCreateNew={onCreateNew}
            fetchSuggestions={fetchSuggestions}
            suggestions={suggestions}
            hasMore={hasMore}
            showDropdown={showDropdown}
            setShowDropdown={setShowDropdown}
        />
        <Input
            type="number"
            step="0.5"
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
