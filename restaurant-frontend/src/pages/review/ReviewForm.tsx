import {FC, useState, useEffect} from "react";
import ReviewFormFields from "../types/ReviewFormFields.tsx";
import NewRestaurantForm from "../restaurant/NewRestaurantForm.tsx";
import {Button} from "@material-tailwind/react";
import * as React from "react";

interface ReviewFormProps {
    onSuccess: (newReview: any) => void;
}

const ReviewForm: FC<ReviewFormProps> = ({onSuccess}) => {
    const [mode, setMode] = useState<"review" | "newRestaurant">("review");
    const [submitting, setSubmitting] = useState(false);

    const [query, setQuery] = useState("");
    const [suggestions, setSuggestions] = useState([]);
    const [showDropdown, setShowDropdown] = useState(false);

    const [newReview, setNewReview] = useState({
        restaurantId: "",
        rating: "",
        comment: "",
    });

    const [newRestaurant, setNewRestaurant] = useState({
        name: "",
        url: "",
        type: "RISTORANTE",
    });

    useEffect(() => {
        const delay = setTimeout(() => {
            if (query.length > 1) {
                fetch(`http://localhost:8082/restaurant/search?query=${query}`)
                    .then((res) => res.json())
                    .then(setSuggestions)
                    .catch(console.error);
            } else {
                setSuggestions([]);
            }
        }, 300);
        return () => clearTimeout(delay);
    }, [query]);

    const handleSubmitReview = async (e: React.FormEvent) => {
        e.preventDefault();
        setSubmitting(true);
        try {
            const res = await fetch("http://localhost:8081/reviews/create", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    restaurantId: Number(newReview.restaurantId),
                    rating: Number(newReview.rating),
                    comment: newReview.comment,
                }),
            });
            if (!res.ok) throw new Error("Errore nell'invio della recensione");
            const data = await res.json();
            onSuccess(data);
            setNewReview({restaurantId: "", rating: "", comment: ""});
        } catch (err: any) {
            alert(err.message);
        } finally {
            setSubmitting(false);
        }
    };

    const handleCreateRestaurant = async (e: React.FormEvent) => {
        e.preventDefault();
        setSubmitting(true);
        try {
            const res = await fetch("http://localhost:8082/restaurant/create", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(newRestaurant),
            });
            if (!res.ok) throw new Error("Errore nella creazione del ristorante");
            const restaurant = await res.json();
            setMode("review");
            setQuery(restaurant.name);
            setNewReview((prev) => ({...prev, restaurantId: restaurant.id.toString()}));
        } catch (err: any) {
            alert(err.message);
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <form
            onSubmit={mode === "review" ? handleSubmitReview : handleCreateRestaurant}
            className="space-y-6 max-w-md mx-auto"
        >
            <h2 className="text-xl font-semibold text-gray-800 mb-12">
                {mode === "review" ? "Lascia una recensione" : "Nuovo ristorante"}
            </h2>

            {mode === "review" ? (
                <ReviewFormFields
                    query={query}
                    setQuery={setQuery}
                    newReview={newReview}
                    setNewReview={setNewReview}
                    suggestions={suggestions}
                    showDropdown={showDropdown}
                    setShowDropdown={setShowDropdown}
                    onCreateNew={() => setMode("newRestaurant")}
                />
            ) : (
                <NewRestaurantForm
                    newRestaurant={newRestaurant}
                    setNewRestaurant={setNewRestaurant}
                />
            )}

            <div className="flex justify-between">
                {mode === "newRestaurant" && (
                    <Button variant="text" color="gray"
                            onClick={() => setMode("review")} type="button" children="Annulla" className="p-0"
                            placeholder={undefined} onPointerEnterCapture={undefined} onPointerLeaveCapture={undefined}>
                    </Button>
                )}
                <Button type="submit" disabled={submitting}
                        children={submitting
                            ? "Invio in corso..."
                            : mode === "review"
                                ? "Invia recensione"
                                : "Crea ristorante"}
                        placeholder={undefined} onPointerEnterCapture={undefined} onPointerLeaveCapture={undefined}>
                </Button>
            </div>
        </form>
    );
};

export default ReviewForm;
