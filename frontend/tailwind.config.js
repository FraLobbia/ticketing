/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        'custom-primary':' #ffe8cc',
        'custom-secondary': '#acacac3e;',
        'custom-danger': 'rgb(111,2,2)',
      },
      borderRadius: {
        'custom': '20px', // Definisci il raggio standard personalizzato
      },
    },
  },
  plugins: [],
}

