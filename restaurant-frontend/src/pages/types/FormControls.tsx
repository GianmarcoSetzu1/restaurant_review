import React, {InputHTMLAttributes, TextareaHTMLAttributes} from "react";

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
    label: string;
    name: string;
}

interface TextareaProps extends TextareaHTMLAttributes<HTMLTextAreaElement> {
    label: string;
    name: string;
}

export const Input: React.FC<InputProps> = ({label, name, ...props}) => (
    <div className="flex flex-col">
        <label htmlFor={name} className="mb-1 text-sm font-medium text-gray-700">{label}</label>
        <input
            id={name}
            name={name}
            {...props}
            className="border border-gray-300 rounded-md px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
    </div>
);

export const Textarea: React.FC<TextareaProps> = ({label, name, ...props}) => (
    <div className="flex flex-col">
        <label htmlFor={name} className="mb-1 text-sm font-medium text-gray-700">{label}</label>
        <textarea
            id={name}
            name={name}
            {...props}
            className="border border-gray-300 rounded-md px-3 py-2 resize-none focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
    </div>
);
