export function fetchWithCredentials(url, options = {}) {
    // Ensure options is an object and apply default settings
    const defaultOptions = {
        credentials: "include",
    };

    // Get auth from localStorage
    const auth = localStorage.getItem('auth');

    const mergedOptions = {
        ...defaultOptions,
        ...options,
        headers: {
            'Content-Type': 'application/json',
            ...options.headers, // Merge custom headers
            ...(auth ? { 'Authorization': `Bearer ${auth}` } : {}), // Add Authorization header if auth exists
        },
    };

    // Return fetch call with then/catch for promise handling
    return fetch(url, mergedOptions)
        .then((response) => {
            if (!response.ok) {
                // Throw error for non-2xx status codes
                return Promise.reject(new Error(`HTTP error! Status: ${response.status}`));
            }
            // Parse JSON response if applicable
            return response;
        })
        .catch((error) => {
            // Catch and handle any network or other errors
            console.error("Fetch error:", error);
            throw error;
        });
}
