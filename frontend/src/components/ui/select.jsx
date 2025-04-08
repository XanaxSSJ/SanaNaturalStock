// src/components/ui/select.jsx
import React from "react";

export function Select({ value, onChange, children, ...props }) {
    return (
        <select
            value={value}
            onChange={(e) => onChange(e.target.value)}
            className="border rounded-md p-2 w-full"
            {...props}
        >
            {children}
        </select>
    );
}

export function SelectItem({ value, children }) {
    return <option value={value}>{children}</option>;
}
