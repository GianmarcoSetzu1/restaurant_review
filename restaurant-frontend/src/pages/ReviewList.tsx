import { Star, StarHalf, Star as StarOutline } from "lucide-react";
import { FC } from "react";

//todo: manage icons dynamically
const restaurantIcons: { [key: string]: string } = {
  pizzeria: "🍕",
  sushi: "🍣",
  ristorante: "🍝",
  hamburger: "🍔",
  default: "🍽️",
};

const renderStars = (rating: number) => {
  const full = Math.floor(rating);
  const half = rating % 1 >= 0.5 ? 1 : 0;
  const empty = 10 - full - half;

  return (
      <div className="flex items-center gap-1 mt-1">
        {[...Array(full)].map((_, i) => (
            <Star key={`f-${i}`} size={18} className="text-yellow-500 fill-yellow-500" />
        ))}
        {half === 1 && <StarHalf size={18} className="text-yellow-500 fill-yellow-500" />}
        {[...Array(empty)].map((_, i) => (
            <StarOutline key={`e-${i}`} size={18} className="text-gray-300" />
        ))}
        <span className="ml-2 text-sm text-gray-600">({rating}/10)</span>
      </div>
  );
};


type Review = {
  id: string;
  userId: number;
  restaurantId: number;
  restaurantType?: string; // todo: category?
  rating: number;
  comment: string;
  createdAt: string;
};

type ReviewListProps = {
  reviews: Review[];
};

const ReviewList: FC<ReviewListProps> = ({ reviews }) => {
  return (
      <ul className="space-y-6 mx-auto px-4">
        {reviews.map((r) => (
            <li
                key={r.id}
                className="p-6 bg-white rounded-2xl shadow-md border border-gray-100 overflow-hidden"
            >
              <div className="flex items-center gap-2 mb-2">
                <div className="text-xl">🍕</div> {/* It depends on the category */}
                <div className="text-lg font-semibold text-gray-900">
                  Ristorante #{r.restaurantId}
                </div>
              </div>

              <div className="text-sm text-gray-700 italic mb-3">
                Utente {r.userId}: “{r.comment}”
              </div>

              {renderStars(r.rating)}

              <div className="mt-2 text-xs text-gray-500">
                Pubblicato il: {new Date(r.createdAt).toLocaleString()}
              </div>
            </li>
        ))}
      </ul>
  );
};

export default ReviewList;
