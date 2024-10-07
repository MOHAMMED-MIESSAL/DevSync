/** @type {import('tailwindcss').Config} */
const defaultTheme = require('tailwindcss/defaultTheme')

module.exports = {
    content: [
        './src/main/webapp/**/*.{html,jsp,js}', // Adjusted to include JSP files
    ],

    "tailwindCSS.includeLanguages": {
        plaintext: "php",
    },

    plugins: [],
    darkMode: "class",
    theme: {
        extend: {
            fontFamily: {
                poppins: ["Poppins", "sans-serif"],
            },
            colors: {
                'primary': {
                    100: '#0D4B33',
                    200: '#052519'
                },
                'orange': '#FB6109',

            }
        },
    },
};