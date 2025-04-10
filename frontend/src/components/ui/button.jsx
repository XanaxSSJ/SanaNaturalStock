export function Button({ children, ...props }) {
    return (
        <button
            className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors"
            {...props}
        >
            {children}
        </button>
    )
}