import axios from 'axios';

const http = axios.create({
  baseURL: "http://localhost:8080/"
});


export default {

    getCuisines() {
        return http.get('preferences');
    },

    getAllFavorites(userId) {
        return http.get(`users/${userId}/favorites`);
    },

    searchRandomZip(zip) {
        return http.get(`/pull/${zip}`);
    },

    saveFavorite(userId, restaurant) {
        return http.post(`users/${userId}/favorites`, {
            // left side is java -- right side is vue restaurant object
            restaurantName: restaurant.name,
            restaurantId: restaurant.id,
            cuisine: restaurant.cuisines,
            phoneNumber: restaurant.phone_numbers,
            rating: restaurant.user_rating.aggregate_rating,
            featuredImage: restaurant.featured_image,
            currency: restaurant.currency,
            menuUrl: restaurant.menu_url,
            url: restaurant.url

        });
    },

    deleteFavorite(userId, restaurantId) {
        return http.delete(`users/${userId}/favorites/${restaurantId}`);
    }
}