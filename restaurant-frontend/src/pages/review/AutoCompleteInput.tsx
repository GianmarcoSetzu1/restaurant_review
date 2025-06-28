import * as React from "react";
import {Restaurant} from "../types/ReviewFormFields.tsx";

interface AutoCompleteInputProps {
    value: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    onSelect: (restaurant: Restaurant) => void;
    onCreateNew: () => void;
    fetchSuggestions: (reset: boolean) => Promise<void>;
    suggestions: Restaurant[];
    hasMore: boolean;
    showDropdown: boolean;
    setShowDropdown: (show: boolean) => void;
}

const AutoCompleteInput: React.FC<AutoCompleteInputProps> = ({
                                                                 value,
                                                                 onChange,
                                                                 onSelect,
                                                                 onCreateNew,
                                                                 fetchSuggestions,
                                                                 suggestions,
                                                                 hasMore,
                                                                 showDropdown,
                                                                 setShowDropdown,
                                                             }) => (
    <div className="relative">
        <label htmlFor="restaurantName" className="block text-sm font-medium text-gray-700 mb-1">
            Nome Ristorante
        </label>
        <input
            type="text"
            id="restaurantName"
            name="restaurantName"
            value={value}
            onChange={onChange}
            placeholder="Inserisci il nome del ristorante"
            className="w-full border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
            autoComplete="off"
            onFocus={() => setShowDropdown(true)}
            onBlur={() => setTimeout(() => setShowDropdown(false), 200)}
        />
        {showDropdown && (
            <ul className="absolute z-10 w-full bg-white border border-gray-300 rounded-md mt-1 max-h-48 overflow-auto shadow-md">
                {suggestions.length > 0 ? (
                    <>
                        {suggestions.map((r) => (
                            <li
                                key={r.id}
                                onClick={() => {
                                    onSelect(r);
                                    setShowDropdown(false);
                                }}
                                className="px-4 py-2 cursor-pointer hover:bg-blue-100"
                            >
                                {r.name}
                            </li>
                        ))}
                        {hasMore && (
                            <li
                                onClick={() => fetchSuggestions(false)}
                                className="px-4 py-2 text-center text-gray-900 cursor-pointer hover:bg-blue-50 font-medium"
                            >
                                Carica altri...
                            </li>
                        )}
                    </>
                ) : (
                    <li
                        onClick={onCreateNew}
                        className="px-4 py-2 text-blue-600 cursor-pointer hover:underline"
                    >
                        Nessun risultato. Clicca per aggiungere un nuovo ristorante.
                    </li>
                )}
            </ul>
        )}
    </div>
);
export default AutoCompleteInput;
