import React from "react";

export function Table({ children }) {
    return (
        <div className="w-full overflow-x-auto rounded-2xl shadow">
            <table className="min-w-full divide-y divide-gray-200 text-sm text-left">
                {children}
            </table>
        </div>
    );
}

export function TableHeader({ children }) {
    return <thead className="bg-gray-100 text-gray-700 uppercase">{children}</thead>;
}

export function TableRow({ children }) {
    return <tr className="hover:bg-gray-50 transition-colors">{children}</tr>;
}

export function TableHead({ children }) {
    return <th className="px-4 py-3 font-semibold tracking-wider">{children}</th>;
}

export function TableBody({ children }) {
    return <tbody className="divide-y divide-gray-200">{children}</tbody>;
}

export function TableCell({ children }) {
    return <td className="px-4 py-2 whitespace-nowrap">{children}</td>;
}
