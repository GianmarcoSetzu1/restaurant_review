import {Star, StarHalf, Star as StarOutline} from "lucide-react";
import {FC, useEffect, useState} from "react";
import axios from "axios";

export const restaurantIcons: { [key: string]: string } = {
    ristorante: "🍝",
    osteria: "🍷",
    trattoria: "🥖",
    sushi: "🍣",
    pizza: "🍕",
    trapizzino: "🥪",
    bowl: "🥗",
    orientale: "🍜",
    default: "🍽️"
};

const renderStars = (rating: number) => {
    const full = Math.floor(rating);
    const half = rating % 1 >= 0.5 ? 1 : 0;
    const empty = 10 - full - half;

    return (
        <div className="flex items-center gap-1 mt-1">
            {[...Array(full)].map((_, i) => (
                <Star key={`f-${i}`} size={18} className="text-yellow-500 fill-yellow-500"/>
            ))}
            {half === 1 && <StarHalf size={18} className="text-yellow-500 fill-yellow-500"/>}
            {[...Array(empty)].map((_, i) => (
                <StarOutline key={`e-${i}`} size={18} className="text-gray-300"/>
            ))}
            <span className="ml-2 text-sm text-gray-600">({rating}/10)</span>
        </div>
    );
};

type Review = {
    id: string;
    userId: number;
    restaurantId: number;
    rating: number;
    comment: string;
    createdAt: string;
};

type Restaurant = {
    name: string;
    type: string;
};

type User = {
    username: string;
};

type ReviewListProps = {
    loading: boolean;
    reviews: Review[];
    jwtToken: string;
};

const ReviewList: FC<ReviewListProps> = ({reviews, jwtToken}) => {
    const [restaurantData, setRestaurantData] = useState<Record<number, Restaurant>>({});
    const [userData, setUserData] = useState<Record<number, User>>({});

    useEffect(() => {
        const fetchRestaurantData = async () => {
            const uniqueIds = [...new Set(reviews.map((r) => r.restaurantId))];
            const results: Record<number, Restaurant> = {};

            await Promise.all(
                uniqueIds.map(async (id) => {
                    try {
                        const res = await axios.get(`http://localhost:8082/restaurant/restaurant/${id}`, {
                            headers: {
                                Authorization: `Bearer ${jwtToken}`,
                            },
                        });
                        results[id] = {
                            name: res.data.name,
                            type: res.data.type.toLowerCase(),
                        };
                    } catch (e) {
                        results[id] = {
                            name: `Ristorante #${id}`,
                            type: "default",
                        };
                    }
                })
            );

            setRestaurantData(results);
        };

        const fetchUserData = async () => {
            const uniqueIds = [...new Set(reviews.map((r) => r.userId))];
            const results: Record<number, User> = {};

            await Promise.all(
                uniqueIds.map(async (id) => {
                    try {
                        const res = await axios.get(`http://localhost:8080/users/user/${id}`, {
                            headers: {
                                Authorization: `Bearer ${jwtToken}`,
                            },
                        });
                        results[id] = {
                            username: res.data.username,
                        };
                    } catch (e) {
                        results[id] = {
                            username: `User #${id}`,
                        };
                    }
                })
            );

            setUserData(results);
        };

        //TODO: Improve rendering these data
        fetchRestaurantData();
        fetchUserData();
    }, [reviews, jwtToken]);


    return (
        <ul className="space-y-6 mx-auto px-4">
            {reviews.map((r) => {
                const restaurant = restaurantData[r.restaurantId];
                const icon = restaurant ? restaurantIcons[restaurant.type] || restaurantIcons.default : "🍽️";
                const restaurantName = restaurant ? restaurant.name : `Ristorante #${r.restaurantId}`;

                const user = userData[r.userId];
                const userName = user ? user.username : `User #${r.userId}`;

                return (
                    <li
                        key={r.id}
                        className="p-6 bg-white rounded-2xl shadow-md border border-gray-100 overflow-hidden"
                    >
                        <div className="flex items-center gap-2 mb-2">
                            <div className="text-xl">{icon}</div>
                            <div className="text-lg font-semibold text-gray-900">{restaurantName}</div>
                        </div>

                        <div className="text-sm text-gray-700 italic mb-3">
                            <span className="font-bold">{userName}</span>: “{r.comment}”
                        </div>

                        {renderStars(r.rating)}

                        <div className="mt-2 text-xs text-gray-500">
                            Pubblicato il: {new Date(r.createdAt).toLocaleString()}
                        </div>
                    </li>
                );
            })}
        </ul>
    );
};

export default ReviewList;
