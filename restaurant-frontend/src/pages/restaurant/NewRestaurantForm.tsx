import {Input} from "../types/FormControls.tsx";
import * as React from "react";
import {restaurantIcons} from "../ReviewList.tsx";


interface NewRestaurant {
    name: string;
    url: string;
    type: string;
}

interface NewRestaurantFormProps {
    newRestaurant: NewRestaurant;
    setNewRestaurant: React.Dispatch<React.SetStateAction<NewRestaurant>>;
}

const restaurantTypes: string[] = Object.keys(restaurantIcons)
    .filter((key) => key !== "default")
    .map((key) => key.toUpperCase());

const NewRestaurantForm: React.FC<NewRestaurantFormProps> = ({newRestaurant, setNewRestaurant}) => (
    <>
        <Input
            type="text"
            name="name"
            label="Nome"
            placeholder="Nome del ristorante"
            value={newRestaurant.name}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => setNewRestaurant({
                ...newRestaurant,
                name: e.target.value
            })}
            required
        />
        <Input
            type="url"
            name="url"
            label="URL"
            placeholder="https://..."
            value={newRestaurant.url}
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => setNewRestaurant({
                ...newRestaurant,
                url: e.target.value
            })}
            required
        />
        <div className="flex flex-col">
            <label htmlFor="type" className="mb-1 text-sm font-medium text-gray-700">Tipo di ristorante</label>
            <select
                name="type"
                id="type"
                value={newRestaurant.type}
                onChange={(e) => setNewRestaurant({...newRestaurant, type: e.target.value})}
                className="border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                required
            >
                <option value="">Seleziona un tipo</option>
                {restaurantTypes.map((type) => (
                    <option key={type} value={type}>
                        {type}
                    </option>
                ))}
            </select>
        </div>
    </>
);

export default NewRestaurantForm;
